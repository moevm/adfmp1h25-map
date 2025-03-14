package game.example.map

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.map.R
import game.example.map.SettingsDataStore.getSelectedPolygon
import kotlinx.coroutines.delay
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.random.Random


@Composable
fun ColoringScreen(navController: NavHostController, difficulty: String, context: Context) {
    var polygonCount: Int
    var mapPolygons: MapPolygons = remember { MapPolygons() }
    LaunchedEffect(Unit) {
        polygonCount = getSelectedPolygon(context)
        mapPolygons.createMap(polygonCount, getColorCount(difficulty))
    }

    var time by remember { mutableIntStateOf(0) }
    var isPaused by remember { mutableStateOf(false) }

    val painter by remember { mutableStateOf(Painter()) }
    var updated by remember { mutableStateOf(false) }

    val borderMap = mapOf(
        Pair(true, 10.dp),
        Pair(false, 1.dp)
    )

    LaunchedEffect(isPaused, updated) {
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
        if (isPaused) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(commonBackgroundColor)
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
        } else {
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
                        modifier = Modifier
                            .align(Alignment.Center),
                        mapPolygons,
                        painter,
                        toogle = {
                            updated = !updated
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    RoundButton(
                        imageResId = R.drawable.bulb_icon,
                        buttonColor = Color.Yellow,
                        borderStroke = BorderStroke(1.dp, Color.Black),
                        onClick = {
                            mapPolygons.hintColoring()
                            time += 10
                        }
                    )
                    RoundButton(
                        imageResId = R.drawable.brush_icon,
                        buttonColor = gameColorsMap.getValue(painter.currentColor),
                        borderStroke = BorderStroke(
                            borderMap.getValue(painter.isGettingColor),
                            color = Color.Black
                        ),
                        onClick = {
                            painter.isGettingColor = !painter.isGettingColor
                            updated = !updated
                        }
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
fun ColoringMap(selectedColor: Color, modifier: Modifier = Modifier, mapPolygons: MapPolygons) {
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

        val mapSizePx = with(density) { 350.dp.toPx() }
        val squareLen = mapSizePx/10

        for (polygon in mapPolygons.getPolygons()) {
            for (square in polygon.squares) {
                drawRect(
                    color = gameColorsMap[polygon.color] ?: commonWhite,
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


fun getColorCount(difficulty: String): Int {
    return mapOf(
        "Hard" to 4,
        "Medium" to 5,
        "Easy" to 6
    ).getValue(difficulty)
}
