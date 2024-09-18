package com.wandev.v0to.navigation.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wandev.v0to.R
import com.wandev.v0to.models.History
import com.wandev.v0to.services.ApiService
import com.wandev.v0to.services.TransactionItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.ArrayList

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun CheckoutScreen(navController: NavHostController) {

    var context = LocalContext.current

    var name by remember {
        mutableStateOf("")
    }

    var phone by remember {
        mutableStateOf("")
    }

    var address by remember {
        mutableStateOf("")
    }

    var checked by remember {
        mutableStateOf(false)
    }

    var clicked by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(clicked) {
        if (clicked == 0) {return@LaunchedEffect}
        if(name.isBlank()) {
            Toast.makeText(context, "Name is required", Toast.LENGTH_SHORT).show()
            return@LaunchedEffect
        }

        if(phone.isBlank()) {
            Toast.makeText(context, "Phone Number is required", Toast.LENGTH_SHORT).show()
            return@LaunchedEffect
        }

        if(address.isBlank()) {
            Toast.makeText(context, "Address is required", Toast.LENGTH_SHORT).show()
            return@LaunchedEffect
        }

        var data = ArrayList<TransactionItem>(arrayListOf())
        ApiService.cartList.filter { it.checked }.forEach {
            data.add(TransactionItem(it.id, it.qty, it.price * it.qty))
        }

        CoroutineScope(Dispatchers.IO).launch {
            val res = ApiService.postTransaction(name, phone, address, data.sumOf { it.subtotal } + 30000, data)
            if (res == "Success") {
                withContext(Dispatchers.Main) {
                    navController.navigate("home") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
                    println(res)
                }
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
                Text(
                    text = "Back",
                    color = colorResource(id = R.color.gray),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
            }

            item {
                Column {
                    Text(
                        text = "Checkout",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 15.dp)
                    )


                    Text(
                        text = "Recipient Name:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.gray),
                        modifier = Modifier.padding(vertical = 3.dp)
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        value = name,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = { name = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Text(
                        text = "Recipient Phone Number:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.gray),
                        modifier = Modifier.padding(vertical = 3.dp)
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        value = phone,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = { phone = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Text(
                        text = "Recipient Address:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.gray),
                        modifier = Modifier.padding(vertical = 3.dp)
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        value = address,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = { address = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            colors = CheckboxDefaults.colors(
                                checkedColor = colorResource(id = R.color.secondary)
                            ),
                            checked = checked, onCheckedChange = { checked = !checked })
                        Text(
                            text = "Use my information as recipient",
                            color = colorResource(id = R.color.gray)
                        )
                    }

                    Column(
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.width(200.dp),
                                text = "Subtotal:",
                                color = colorResource(id = R.color.gray)
                            )
                            Text(
                                text = "Rp" + NumberFormat.getIntegerInstance().format(ApiService.cartList.filter { it.checked }.sumOf { it.price * it.qty }) + ",-",
                                color = colorResource(id = R.color.gray)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.width(200.dp),
                                text = "Delivery Fee:",
                                color = colorResource(id = R.color.gray)
                            )
                            Text(
                                text = "Rp30.000,-",
                                color = colorResource(id = R.color.gray)
                            )
                        }
                    }

                    Column (
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 15.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.White,
                            text = "Total: Rp" + NumberFormat.getIntegerInstance().format(ApiService.cartList.filter { it.checked }.sumOf { it.price * it.qty } + 30000) + ",-")
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.secondary)
                            ),
                            onClick = {
                                clicked++
                            }) {
                            Text(text = "Checkout", color = colorResource(id = R.color.primary))
                        }
                    }
                }
            }
        }
    }
}