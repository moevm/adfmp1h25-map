package com.example.map

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
import kotlin.random.Random


@Composable
fun CustomScreen(navController: NavHostController) {
    val polygonsList = listOf<String>(
        "10", "15",
        "25", "35",
        "50", "75"
    )
    val count: Int = polygonsList.size
    val randomIndex = Random.nextInt(count)
    val currentConfigVal = polygonsList[randomIndex]

    PageTemplate(
        header = "Custom",
        pageDescription = "Choose number of polygons:",
        buttonDescription = "Menu",
        buttonColor = commonGrayColor,
        buttonAction = {
            navController.navigate("menu")
        },
    ) {
        Column {
            for (i in 0..<(count / 2)) {
                Row {
                    val value1 = polygonsList[i * 2]
                    val value2 = polygonsList[i * 2 + 1]
                    val isChosen1 = (value1 == currentConfigVal)
                    val isChosen2 = (value2 == currentConfigVal)
                    PolygonCountButton(value1, commonOrangeColor, isChosen1, { saveToConf(navController, value1) })
                    Spacer(modifier = Modifier.width(30.dp))
                    PolygonCountButton(value2, commonOrangeColor, isChosen2, { saveToConf(navController, value1) })
                }
            }
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

fun saveToConf(navController: NavHostController, value: String) {
    navController.navigate("custom")
}
