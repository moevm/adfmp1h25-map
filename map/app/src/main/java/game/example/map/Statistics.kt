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
fun StatisticsScreen(navController: NavHostController, context: Context) {
    var statistics by remember { mutableStateOf<Map<String, Pair<Int, String>>>(emptyMap()) }

    LaunchedEffect(Unit) {
        statistics = StatisticsDataStore.getStatistics(context)
    }

    PageTemplate(
        header = "Statistics",
        pageDescription = "",
        buttonDescription = "Menu",
        buttonColor = commonGrayColor,
        buttonAction = { navController.navigate("menu") },
    ) {
        StatisticsTable(statistics)
    }
}

@Composable
fun StatisticsTable(statistics: Map<String, Pair<Int, String>>) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Difficulty",
                fontSize = 20.sp,
                modifier = Modifier.width(100.dp),
                style = TextStyle(
                    fontFamily = JuraFontFamily,
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center
            )
            Text(
                "Solved",
                fontSize = 20.sp,
                modifier = Modifier.width(90.dp),
                style = TextStyle(
                    fontFamily = JuraFontFamily,
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center
            )
            Text(
                "Best Time",
                fontSize = 20.sp,
                modifier = Modifier.width(110.dp),
                style = TextStyle(
                    fontFamily = JuraFontFamily,
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        statistics.forEach { (difficulty, data) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    difficulty,
                    fontSize = 20.sp,
                    modifier = Modifier.width(100.dp),
                    style = TextStyle(
                        fontFamily = JuraFontFamily,
                        fontWeight = FontWeight.Bold,
                        ),
                    textAlign = TextAlign.Center
                )
                Text(
                    data.first.toString(),
                    fontSize = 20.sp,
                    modifier = Modifier.width(90.dp),
                    style = TextStyle(
                        fontFamily = JuraFontFamily,
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    data.second,
                    fontSize = 20.sp,
                    modifier = Modifier.width(110.dp),
                    style = TextStyle(
                        fontFamily = JuraFontFamily,
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}