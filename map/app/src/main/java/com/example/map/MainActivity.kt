package com.example.map

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
        buttonAction = { navController.navigate("statistics") },
    ) {
        Column(){
            CommonButton("Easy", commonOrangeColor, onClick = {
                navController.navigate("coloring")
            })
            CommonButton("Medium", commonOrangeColor, onClick = { /* TODO:*/ })
            CommonButton("Hard", commonOrangeColor, onClick = { /* TODO:*/ })

            Spacer(modifier = Modifier.height(180.dp))

            CommonButton("Custom", commonGreenColor, onClick = {
                navController.navigate("custom")
            })
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") { MapColoringScreen(navController) }
        composable("coloring") { ColoringScreen(navController) }
        composable("custom") { CustomScreen(navController, context) }
        composable("statistics") { StatisticsScreen(navController, context) }
    }
}