package com.mohi.mainventar.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohi.mainventar.components.AddEntityScreen
import com.mohi.mainventar.model.viewmodel.EntitiesViewModel
import com.mohi.mainventar.components.DisplayAllScreen
import com.mohi.mainventar.components.DisplayTopScreen

@ExperimentalMaterialApi
@Composable
fun AppNavigation(
    viewModel: EntitiesViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Screen1.route) {
        composable(
            route = Screen.Screen1.route
        ) {
            DisplayAllScreen(
                onClick = {
                    navController.navigate(Screen.Screen2.route)
                },
                onSwitchToTopView = {
                    navController.navigate(Screen.Screen3.route)
                }
            )
        }
        composable(
            route = Screen.Screen2.route
        ) {
            AddEntityScreen(
                onClick =  { nume: String, tip: String, cantitate: Int, pret: Int, discount: Int, status: Boolean ->
                    viewModel.add(nume, tip, cantitate, pret, discount, status)
                    navController.navigate(Screen.Screen1.route)
                }
            )
        }
        composable(
            route = Screen.Screen3.route
        ) {
            DisplayTopScreen()
        }
    }
}