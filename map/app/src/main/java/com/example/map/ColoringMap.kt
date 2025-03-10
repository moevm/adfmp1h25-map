package com.example.map

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlin.random.Random


@Composable
fun ColoringScreen(navController: NavHostController, difficulty: String, context: Context) {
    var selectedColor by remember { mutableStateOf(gameGreenColor) }
    var time by remember { mutableIntStateOf(0) }
    var isPaused by remember { mutableStateOf(false) }

    LaunchedEffect(isPaused) {
        while (!isPaused) {
            delay(1000L)
            time++
        }
    }

    PageTemplate(
        header = difficulty,
        pageDescription = "${getColorCount(difficulty)} colors",
        buttonDescription = "Pause",
        buttonColor = commonGreenColor,
        visibleButton = !isPaused,
        buttonAction = {
            isPaused = true
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .border(BorderStroke(1.dp, Color.Black))
            ) {
                ColoringMap(
                    selectedColor,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RoundButton(
                    imageResId = R.drawable.bulb_icon,
                    buttonColor = Color.Yellow,
                    onClick = { }
                )
                RoundButton(
                    imageResId = R.drawable.brush_icon,
                    buttonColor = selectedColor,
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "%02d:%02d".format(time / 60, time % 60),
                fontSize = 30.sp,
                style = TextStyle(
                    fontFamily = JuraFontFamily,
                    fontWeight = FontWeight.Bold,
                )
            )
        }

        if (isPaused) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(commonBackgroundColor)
                    .clickable { }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CommonButton("Resume", commonGrayColor, onClick = { isPaused = false })
                    CommonButton("Menu", commonGrayColor, onClick = { navController.navigate("menu") })
                }
            }
        }
    }
}

@Composable
fun RoundButton(imageResId: Int, buttonColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        modifier = Modifier
            .size(80.dp)
            .border(BorderStroke(1.dp, Color.Black), CircleShape)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Button Image",
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
fun ColoringMap(selectedColor: Color, modifier: Modifier = Modifier) {
    var regionColors by remember { mutableStateOf(List(10) { Color.White }) }

    val density = LocalDensity.current

    Canvas(
        modifier = modifier
            .width(350.dp)
            .height(350.dp)
            .background(Color.White)
            .clickable {
                regionColors = regionColors.map { selectedColor }
            }
    ) {

        var mapPolygons = MapPolygons(10)

        val mapSizePx = with(density) { 350.dp.toPx() } // Переводим 350dp в пиксели
        val squareLen = mapSizePx/10

        for (polygon in mapPolygons.getPolygons()) {
            val polygonColor = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            for (square in polygon.squares) {
                drawRect(
                    color = polygonColor,
                    size = Size( width = squareLen, height = squareLen),
                    topLeft = Offset(
                        squareLen * square.xRelative,
                        squareLen * square.yRelative
                    )
                )
            }

            for (line in polygon.border) {
                drawLine(
                    color = Color.Black,
                    start = Offset(
                        squareLen * line[0].first,
                        squareLen * line[0].second
                    ),
                    end = Offset(
                        squareLen * line[1].first,
                        squareLen * line[1].second
                    ),
                    strokeWidth = 5f
                )
            }
        }
    }
}


fun getColorCount(difficulty: String): Int? {
    return mapOf(
        "Hard" to 4,
        "Medium" to 5,
        "Easy" to 6
    )[difficulty]
}
