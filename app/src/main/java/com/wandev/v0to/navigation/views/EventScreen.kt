package com.wandev.v0to.navigation.views

import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wandev.v0to.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun EventScreen(navHostController: NavHostController) {
    val context = LocalContext.current

    var isSpinning by remember {
        mutableStateOf(false)
    }

    var rotationAngle by remember {
        mutableStateOf(0f)
    }

    val rotation by animateFloatAsState(targetValue = rotationAngle, animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing))
    val presents = listOf("+3000 Votoken", "Jackpot", "+2500 Votoken", "Draw Again", "+1500 Votoken", "Try again next time")

    LaunchedEffect(isSpinning) {
        if (isSpinning) {
            rotationAngle += (360f * 5f) + (30..360).random().toFloat()
            val index = (( (rotationAngle % 360) / (360f / 6) ).toInt()) % 6
            delay(3000)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, presents[index], Toast.LENGTH_SHORT).show()
                isSpinning = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary))
    ) {

        LazyColumn(
            modifier = Modifier.padding(15.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Back",
                        color = colorResource(id = R.color.gray),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            navHostController.popBackStack()
                        })

                    Text(
                        text = "Lucky Spin",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 15.dp)
                    )

                    Text(
                        text = "Your Votoken:",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.gray)
                    )
                    Text(
                        text = "3500",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable {
                                isSpinning = true
                            },
                        painter = painterResource(id = R.drawable.roulette_wheel),
                        contentDescription = ""
                    )

                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .graphicsLayer(
                                translationY = -90f,
                                rotationZ = rotation,
                                transformOrigin = TransformOrigin(.5f, .85f)
                            ),
                        painter = painterResource(id = R.drawable.roulette_needle),
                        contentDescription = ""
                    )
                }
            }
            
            item { 
                Text(
                    modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                    textAlign = TextAlign.Center,
                    text = "Tap the wheel to start your lucky spin", color = colorResource(id = R.color.gray))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Each spin cost 2200 votoken", color = colorResource(id = R.color.gray))
            }
        }

    }

}