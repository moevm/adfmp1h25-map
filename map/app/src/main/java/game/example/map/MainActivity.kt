package game.example.map

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

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
        buttonDescription = "About",
        buttonColor = commonGrayColor,
        buttonAction = { navController.navigate("about") },
    ) {
        Column(){
            CommonButton("Easy", commonOrangeColor, onClick = {
                navController.navigate("coloring/Easy")
            })
            CommonButton("Medium", commonOrangeColor, onClick = {
                navController.navigate("coloring/Medium")
            })
            CommonButton("Hard", commonOrangeColor, onClick = {
                navController.navigate("coloring/Hard")
            })

            Spacer(modifier = Modifier.height(90.dp))

            CommonButton("Custom", commonGreenColor, onClick = {
                navController.navigate("custom")
            })
            CommonButton("Statistics", commonGrayColor, onClick = {
                navController.navigate("statistics")
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
        composable(
            route = "coloring/{difficulty}",
            arguments = listOf(
                navArgument("difficulty") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "Unknown"
            ColoringScreen(navController, difficulty, context)
        }
        composable("custom") { CustomScreen(navController, context) }
        composable("statistics") { StatisticsScreen(navController, context) }
        composable("about") { AboutScreen(navController, context) }
    }
}