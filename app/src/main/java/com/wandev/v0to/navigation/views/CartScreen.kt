package com.wandev.v0to.navigation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wandev.v0to.R
import com.wandev.v0to.services.ApiService
import java.text.NumberFormat

@Composable
fun CartScreen(navController : NavHostController) {
    var totalPrice by remember {
        mutableStateOf(0)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.primary))) {
    }

    LazyColumn (modifier = Modifier
        .padding(15.dp)) {
        item {
            Text(
                color = colorResource(id = R.color.gray),
                text = "Back",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.clickable { navController.popBackStack() })

            Text(
                text = "Cart",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(vertical = 15.dp))
        }

        items(ApiService.cartList.distinctBy { it.sellerShop }) { item ->
            var sellerChecked by remember {
                mutableStateOf(false)
            }

            Row (verticalAlignment = Alignment.CenterVertically) {

                Checkbox(
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(id = R.color.secondary)
                    ),
                    checked = sellerChecked,
                    onCheckedChange = {sellerChecked = !sellerChecked})
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    text = item.sellerShop,
                    color = Color.White)

            }

            Column (modifier = Modifier.padding(start = 30.dp, bottom = 60.dp)) {
                ApiService.cartList.filter { it.sellerShop == item.sellerShop }.forEach { camera ->
                    var checked by remember {
                        mutableStateOf(false)
                    }

                    var price by remember {
                        mutableStateOf(0)
                    }

                    var qty by remember {
                        mutableStateOf(camera.qty)
                    }

                    LaunchedEffect(checked) {
                        camera.checked = checked
                        totalPrice = ApiService.cartList.filter { it.checked }.sumOf { it.price * it.qty }
                    }

                    LaunchedEffect(qty) {
                        camera.qty = qty
                        price = camera.price * qty
                        totalPrice = ApiService.cartList.filter { it.checked }.sumOf { it.price * it.qty }
                    }

                    LaunchedEffect(sellerChecked) {
                        checked = sellerChecked
                    }
                    Row {
                        Checkbox(
                            colors = CheckboxDefaults.colors(
                                checkedColor = colorResource(id = R.color.secondary)
                            ),
                            checked = checked,
                            onCheckedChange = {checked = !checked})

                        Image(modifier = Modifier
                            .size(100.dp)
                            .padding(top = 15.dp, start = 15.dp), contentScale = ContentScale.Crop, bitmap = camera.photo.asImageBitmap(), contentDescription = "")

                        Column (
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                                .fillMaxHeight()
                        ) {
                            Text(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                text = item.name,
                                color = Color.White)
                            Row (verticalAlignment = Alignment.CenterVertically) {
                                Button(
                                    modifier = Modifier.width(60.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(id = R.color.secondary)
                                    ),
                                    onClick = { if(qty > 1) { qty-- } }) {
                                    Text(
                                        color = colorResource(id = R.color.primary),
                                        text = "-", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center
                                    )
                                }
                                
                                Text(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    color = Color.White,
                                    text = qty.toString())

                                Button(
                                    modifier = Modifier.width(60.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(id = R.color.secondary)
                                    ),
                                    onClick = { qty++ }) {
                                    Text(
                                        color = colorResource(id = R.color.primary),
                                        text = "+", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
                                }
                            }

                            Text(
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .fillMaxWidth(),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.End,
                                fontSize = 18.sp,
                                text = "Rp" + NumberFormat.getIntegerInstance().format(price) + ",-",
                                color = Color.White)
                        }

                    }
                }
            }
        }

        if (ApiService.cartList.isNotEmpty()) {
            item {

                Box (modifier = Modifier.heightIn(min = 370.dp)) {
                    Column (
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 15.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.White,
                            text = "Total: Rp" + NumberFormat.getIntegerInstance().format(totalPrice) + ",-")
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.secondary)
                            ),
                            onClick = {}) {
                            Text(text = "Checkout", color = colorResource(id = R.color.primary))
                        }
                    }
                }
            }
        }
    }
}