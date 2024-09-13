package com.wandev.v0to.navigation.views

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wandev.v0to.R
import com.wandev.v0to.models.CameraDetail
import com.wandev.v0to.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat

@Composable
fun DetailScreen(navController: NavHostController, id: String) {
    val context = LocalContext.current

    var camera by remember {
        mutableStateOf<CameraDetail?>(null)
    }

    LaunchedEffect(Unit) {
        GlobalScope.launch {
            camera = ApiService.getCameraDetail(id, context)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary))
            .padding(15.dp)
    ) {
        LazyColumn {
            item {
                Text(
                    color = colorResource(id = R.color.gray),
                    text = "Back",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.clickable { navController.popBackStack() })
            }

            if (camera != null) {

                item {
                    Column {
                        Row (modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp), horizontalArrangement = Arrangement.Center) {
                            Image(
                                contentScale = ContentScale.Crop,
                                bitmap = camera!!.photo.asImageBitmap(),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(300.dp)
                            )
                        }

                        Text(
                            text = camera!!.name,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Text(
                            modifier = Modifier.padding(top = 5.dp),
                            text = camera!!.sellerShop,
                            color = colorResource(id = R.color.gray),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Row(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column (modifier = Modifier.widthIn(max = 125.dp)) {
                                Text(
                                    text = "Sensor",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp),
                                    text = camera!!.sensor,
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.gray),
                                )

                                Text(
                                    modifier = Modifier.padding(top = 10.dp),
                                    text = "ISO Range",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp),
                                    text = camera!!.isoRange,
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.gray),
                                )

                                Text(
                                    modifier = Modifier.padding(top = 10.dp),
                                    text = "Weight",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp),
                                    text = camera!!.weight.toString() + "g",
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.gray),
                                )

                                Text(
                                    modifier = Modifier.padding(top = 10.dp),
                                    text = "Flash",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp),
                                    text = if (camera!!.flash) "Yes" else "No",
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.gray),
                                )
                            }

                            Column (modifier = Modifier.widthIn(max = 125.dp)) {
                                Text(
                                    text = "Resolution",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp),
                                    text = camera!!.resolution,
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.gray),
                                )

                                Text(
                                    modifier = Modifier.padding(top = 10.dp),
                                    text = "Shutter Speed",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp),
                                    text = camera!!.shuterSpeedRange,
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.gray),
                                )

                                Text(
                                    modifier = Modifier.padding(top = 10.dp),
                                    text = "Wi-Fi",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp),
                                    text = if (camera!!.wiFi) "Yes" else "No",
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.gray),
                                )

                                Text(
                                    modifier = Modifier.padding(top = 10.dp),
                                    text = "Bluetooth",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp),
                                    text = if (camera!!.bluetooth) "Yes" else "No",
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.gray),
                                )
                            }

                            Column (modifier = Modifier.widthIn(max = 125.dp)) {
                                Text(
                                    text = "Autofocus",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp),
                                    text = camera!!.autoFocusSystem,
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.gray),
                                )

                                Text(
                                    modifier = Modifier.padding(top = 10.dp),
                                    text = "Dimensions",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp),
                                    text = camera!!.dimensions,
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.gray),
                                )

                                Text(
                                    modifier = Modifier.padding(top = 10.dp),
                                    text = "Touch Screen",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp),
                                    text = if (camera!!.touchScreen) "Yes" else "No",
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.gray),
                                )
                            }

                        }


                    }
                }

            item {
                Column {
                    Text(
                        modifier = Modifier.padding(top = 15.dp),
                        text = "Rp" + NumberFormat.getIntegerInstance().format(camera!!.price) + ",-",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp),
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.secondary)
                        )) {
                        Text(text = "Add To Cart", color = colorResource(id = R.color.primary))
                    }
                }
            }
            }
        }
    }
}