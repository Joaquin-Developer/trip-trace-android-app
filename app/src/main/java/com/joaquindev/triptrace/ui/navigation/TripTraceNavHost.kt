package com.joaquindev.triptrace.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.joaquindev.triptrace.ui.components.TripTraceBottomNavBar
import com.joaquindev.triptrace.ui.screens.history.HistoryScreen
import com.joaquindev.triptrace.ui.screens.login.LoginScreen
import com.joaquindev.triptrace.ui.screens.trips.TripsScreen

private val routesWithBottomBar = setOf(Routes.TRIPS, Routes.HISTORY)

@Composable
fun TripTraceNavHost(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in routesWithBottomBar) {
                TripTraceBottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Routes.TRIPS) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.LOGIN,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Routes.TRIPS) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    },
                )
            }
            composable(Routes.TRIPS) {
                TripsScreen()
            }
            composable(Routes.HISTORY) {
                HistoryScreen()
            }
        }
    }
}
