package com.wandev.v0to.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wandev.v0to.navigation.views.CartScreen
import com.wandev.v0to.navigation.views.CheckoutScreen
import com.wandev.v0to.navigation.views.DetailScreen
import com.wandev.v0to.navigation.views.EventScreen
import com.wandev.v0to.navigation.views.HistoryScreen
import com.wandev.v0to.navigation.views.HomeScreen
import com.wandev.v0to.navigation.views.LoginScreen

@Composable
fun globalGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "login" ) {
        composable("login") {
            LoginScreen(navHostController)
        }
        composable("home") {
            HomeScreen(navHostController)
        }
        composable("detail/{id}") {
            val id = it.arguments?.getString("id")
            DetailScreen(navHostController, id = id!!)
        }
        composable("cart") {
            CartScreen(navHostController)
        }

        composable("checkout") {
            CheckoutScreen(navHostController)
        }

        composable("history") {
            HistoryScreen(navHostController)
        }
        
        composable("event") {
            EventScreen(navHostController = navHostController)
        }
    }
}