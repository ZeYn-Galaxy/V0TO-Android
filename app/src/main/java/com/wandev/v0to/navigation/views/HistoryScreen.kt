package com.wandev.v0to.navigation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wandev.v0to.R
import com.wandev.v0to.models.History
import com.wandev.v0to.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat

@Composable
fun HistoryScreen(navController : NavHostController) {

    var historyList by remember {
        mutableStateOf(emptyList<History>())
    }

    LaunchedEffect(Unit) {
        GlobalScope.launch {
            historyList = ApiService.getHistory()
            println(historyList)
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.primary))) {

        LazyColumn (
            modifier = Modifier.padding(15.dp)
        ) {

            item {
                Text(
                    text = "Back",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    },
                    color = colorResource(id = R.color.gray)
                )
            }

            item {
                Text(
                    text = "Order History",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }

            items(historyList) {item ->
                Spacer(modifier = Modifier.size(15.dp))
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(colorResource(id = R.color.gray2))
                        .padding(15.dp)
                ) {
                    
                    Text(
                        text = item.id,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.gray)
                    )

                    Text(
                        text = item.status,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.gray)
                    )

                    item.transactions.forEach { transaction ->
                        Column (modifier = Modifier.padding(top = 10.dp)) {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = transaction.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = colorResource(id = R.color.gray)
                                )

                                Text(
                                    text = "Rp" + NumberFormat.getIntegerInstance().format(transaction.subtotal) + ",-",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = colorResource(id = R.color.gray)
                                )
                            }

                            Text(
                                text = transaction.qty.toString() + "x",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = colorResource(id = R.color.gray)
                            )
                        }
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        textAlign = TextAlign.End,
                        text = "Total: Rp" + NumberFormat.getNumberInstance().format(item.totalPrice) + ",-",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }


        }

    }
}