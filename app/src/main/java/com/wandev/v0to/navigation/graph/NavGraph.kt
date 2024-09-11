package com.wandev.v0to.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wandev.v0to.navigation.views.HomeScreen
import com.wandev.v0to.navigation.views.LoginScreen

@Composable
fun globalGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "home" ) {
        composable("login") {
            LoginScreen(navHostController)
        }
        composable("home") {
            HomeScreen(navHostController)
        }
    }
}