package com.example.map

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun CustomScreen(navController: NavHostController, context: Context) {
    val polygonsList = listOf("10", "15", "25", "35", "50", "75")
    val count = polygonsList.size

    var currentConfigVal by remember { mutableStateOf("10") }

    LaunchedEffect(Unit) {
        currentConfigVal = SettingsDataStore.getSelectedPolygon(context)
    }

    PageTemplate(
        header = "Custom",
        pageDescription = "Choose number of polygons:",
        buttonDescription = "Menu",
        buttonColor = commonGrayColor,
        buttonAction = { navController.navigate("menu") },
    ) {
        Column {
            for (i in 0 until (count / 2)) {
                Row {
                    val value1 = polygonsList[i * 2]
                    val value2 = polygonsList[i * 2 + 1]
                    val isChosen1 = (value1 == currentConfigVal)
                    val isChosen2 = (value2 == currentConfigVal)

                    PolygonCountButton(value1, commonOrangeColor, isChosen1) {
                        saveToConf(navController, context, value1) { newValue ->
                            currentConfigVal = newValue
                        }
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    PolygonCountButton(value2, commonOrangeColor, isChosen2) {
                        saveToConf(navController, context, value2) { newValue ->
                            currentConfigVal = newValue
                        }
                    }
                }
            }
        }
    }
}

fun saveToConf(navController: NavHostController, context: Context, value: String, onSaved: (String) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        SettingsDataStore.saveSelectedPolygon(context, value)
        withContext(Dispatchers.Main) {
            onSaved(value)
            navController.navigate("custom")
        }
    }
}

@Composable
fun PolygonCountButton(text: String, color: Color, isChosen: Boolean, onClick: () -> Unit) {
    var border: BorderStroke? = null
    if (isChosen) {
        border = BorderStroke(2.dp, Color.Black)
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(color),
        border = border,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(vertical = 5.dp)
            .height(80.dp)
            .width(150.dp)
    ) {
        Text(
            text = text,
            fontSize = 30.sp,
            color = Color.Black,
            style = TextStyle(
                fontFamily = JuraFontFamily,
                fontWeight = FontWeight.Bold,
            )

        )
    }
}
