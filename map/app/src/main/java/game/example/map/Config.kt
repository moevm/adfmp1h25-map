package game.example.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.map.R


val JuraFontFamily = FontFamily(
    Font(R.font.jura_regular, FontWeight.Normal),
    Font(R.font.jura_bold, FontWeight.Bold)
)

val commonOrangeColor = Color(0xFFF49B57)
val commonGreenColor = Color(0xFF87BA5D)
val commonGrayColor = Color(0xFFADADAD)
val commonBackgroundColor = Color(0xFFD9D9D9)
val commonWhite = Color(0xFFFFFFFF)


val gameGreenColor = Color(0xFF4CAF50)
val gameBlueColor = Color(0xFF2196F3)
val gameRedColor = Color(0xFFF44336)
val gameYellowColor = Color(0xFFFFC107)
val gameTurquoiseColor = Color(0xFF009688)
val gamePinkColor = Color(0xFFDA36B5)

val gameColorsMap = mapOf(
    Pair(0, commonWhite),
    Pair(1, gameBlueColor),
    Pair(2, gameRedColor),
    Pair(3, gameYellowColor),
    Pair(4, gameTurquoiseColor),
    Pair(5, gamePinkColor),
    Pair(6, gameGreenColor),
)


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

@Composable
fun PageTemplate(
    header: String,
    pageDescription: String,
    buttonDescription: String,
    buttonColor: Color,
    visibleButton: Boolean=true,
    buttonAction: () -> Unit,
    content: @Composable() () -> Unit) {
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
            Box(
                modifier = Modifier
                    .height(180.dp)
            ) {
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.9f),
                        text = header,
                        fontSize = 50.sp,
                        style = TextStyle(
                            fontFamily = JuraFontFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.9f),
                        text = pageDescription,
                        fontSize = 30.sp,
                        style = TextStyle(
                            fontFamily = JuraFontFamily,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box (
                modifier = Modifier
                    .height(540.dp)
            ) {
                content()
            }

            if (visibleButton) {
                CommonButton(buttonDescription, buttonColor, onClick = buttonAction)
            }
        }
    }
}