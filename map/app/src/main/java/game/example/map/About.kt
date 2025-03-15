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
                "Students of ETU \"LETI\"\nGroup 1303:\n\nAndreeva Elizaveta:\nelizavetaa40@gmail.com\n\nButylo Egor:\negorbutylo@mail.ru\n\nDepreys Alexander:\nikighgg@gmail.com ",
                fontSize = 20.sp,
                modifier = Modifier.width(300.dp),
                style = TextStyle(
                    fontFamily = JuraFontFamily,
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}


