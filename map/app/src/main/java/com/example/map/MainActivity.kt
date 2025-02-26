package com.example.map

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val JuraFontFamily = FontFamily(
    Font(R.font.jura_regular, FontWeight.Normal),
    Font(R.font.jura_bold, FontWeight.Bold)
    // Add more font styles here if you have them
)

val commonOrangeColor = Color(0xFFF49B57)
val commonGreenColor = Color(0xFF87BA5D)
val commonGrayColor = Color(0xFFADADAD)
val commonBackgroundColor = Color(0xFFD9D9D9)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapColoringScreen()
        }
    }
}

@Composable
fun MapColoringScreen() {
    Box(
        modifier = Modifier
            .background(commonBackgroundColor)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (90).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                text = "Map Coloring",
                fontSize = 50.sp,
                style = TextStyle(
                    fontFamily = JuraFontFamily,
                    fontWeight = FontWeight.Bold,
                )
            )
            Spacer(modifier = Modifier.height(70.dp))

            Text(
                text = "Choose level difficulty:",
                fontSize = 30.sp,
                style = TextStyle(
                    fontFamily = JuraFontFamily,
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            CommonButton("Easy", commonOrangeColor, onClick = { /* TODO:*/ })
            CommonButton("Medium", commonOrangeColor, onClick = { /* TODO:*/ })
            CommonButton("Hard", commonOrangeColor, onClick = { /* TODO:*/ })

            Spacer(modifier = Modifier.height(120.dp))

            CommonButton("Custom", commonGreenColor, onClick = { /* TODO:*/ })
            CommonButton("Statistics", commonGrayColor, onClick = { /* TODO:*/ })

        }
    }
}

@Composable
fun CommonButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(color),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .padding(vertical = 5.dp)
            .height(80.dp)
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