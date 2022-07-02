package com.example.loginlogout.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Splash.route){
        composable(route = Screen.Splash.route){

        }
        composable(route = Screen.Welcome.route){

        }
        composable(route = Screen.Login.route){

        }
        composable(route = Screen.Home.route){

        }
    }
}