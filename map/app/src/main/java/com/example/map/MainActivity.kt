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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun MapColoringScreen(navController: NavHostController) {
    PageTemplate(
        header = "Map Coloring",
        pageDescription = "Choose level difficulty:",
        buttonDescription = "Statistics",
        buttonColor = commonGrayColor,
        buttonAction = { /* TODO:*/ },
    ) {
        Column(){
            CommonButton("Easy", commonOrangeColor, onClick = {
                navController.navigate("coloring")
            })
            CommonButton("Medium", commonOrangeColor, onClick = { /* TODO:*/ })
            CommonButton("Hard", commonOrangeColor, onClick = { /* TODO:*/ })

            Spacer(modifier = Modifier.height(180.dp))

            CommonButton("Custom", commonGreenColor, onClick = { /* TODO:*/ })
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") { MapColoringScreen(navController) }
        composable("coloring") { ColoringScreen() }
    }
}