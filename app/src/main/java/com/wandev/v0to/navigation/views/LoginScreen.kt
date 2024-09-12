package com.wandev.v0to.navigation.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.wandev.v0to.R
import com.wandev.v0to.services.ApiService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    var context = LocalContext.current
    var email by remember {
        mutableStateOf("mahdi@gmail.com")
    }
    var password by remember {
        mutableStateOf("1234")
    }

    var clicked by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(clicked) {
        if (clicked == 0) { return@LaunchedEffect }
        if (email.isBlank()) {
            return@LaunchedEffect Toast.makeText(context, "Email required", Toast.LENGTH_SHORT).show()
        }

        if (password.isBlank()) {
            return@LaunchedEffect Toast.makeText(context, "Password required", Toast.LENGTH_SHORT).show()
        }
        GlobalScope.launch {
            ApiService.login(context, email, password, navController)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary))) {

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.logo_voto), contentDescription = "",
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp))

            OutlinedTextField(
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                value = email,
                onValueChange = {email = it},
                label = { Text(text = "Email") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ))

            OutlinedTextField(
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 30.dp, end = 30.dp),
                value = password,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {password = it},
                label = { Text(text = "Password") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                ))
            
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.secondary)
                ),
                onClick = {
                    clicked++
                }) {
                Text(text = "Login", color = colorResource(id = R.color.primary))
            }
        }

    }
}