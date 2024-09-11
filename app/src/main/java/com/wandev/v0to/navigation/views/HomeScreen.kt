package com.wandev.v0to.navigation.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.wandev.v0to.R

@Composable
fun HomeScreen(navController : NavHostController) {
    Text(text = "Hello Sir", color = Color.White)
}