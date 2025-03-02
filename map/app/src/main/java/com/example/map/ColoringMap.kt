package com.example.map

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.ui.focus.focusModifier


@Composable
fun ColoringScreen() {
    var selectedColor by remember { mutableStateOf(Color.Yellow) }
    var time by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            time++
        }
    }

    PageTemplate(
        header = "Hard",
        pageDescription = "4 colors",
        buttonDescription = "Pause",
        buttonColor = commonGreenColor,
        buttonAction = { /* TODO:*/ },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
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
                    buttonColor = Color.LightGray,
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

    Canvas(
        modifier = modifier
            .width(350.dp)
            .height(350.dp)
            .background(Color.White)
            .clickable {
                regionColors = regionColors.map { selectedColor }
            }
    ) {
        val regions = listOf(
            listOf(Offset(30f, 30f), Offset(90f, 30f), Offset(60f, 90f)),
            listOf(Offset(120f, 30f), Offset(180f, 30f), Offset(150f, 90f)),
            listOf(Offset(60f, 90f), Offset(120f, 90f), Offset(90f, 150f))
        )

        regions.forEachIndexed { index, points ->
            val path = Path().apply {
                moveTo(points.first().x, points.first().y)
                points.drop(1).forEach { lineTo(it.x, it.y) }
                close()
            }
            drawPath(
                path = path,
                color = regionColors[index],
                style = Fill
            )
            drawPath(
                path = path,
                color = Color.Black,
                style = Stroke(width = 3f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
    }
}
