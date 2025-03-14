package game.example.map

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import kotlin.collections.Map

@Composable
fun AboutScreen(navController: NavHostController, context: Context) {
    PageTemplate(
        header = "Authors",
        pageDescription = "",
        buttonDescription = "Menu",
        buttonColor = commonGrayColor,
        buttonAction = { navController.navigate("menu") },
    ) {
        Box(
        ){
            Text(
                "Andreeva Elizaveta\nEgor Butylo\nAlexander Depreys",
                fontSize = 20.sp,
                modifier = Modifier.width(200.dp),
                style = TextStyle(
                    fontFamily = JuraFontFamily,
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}


