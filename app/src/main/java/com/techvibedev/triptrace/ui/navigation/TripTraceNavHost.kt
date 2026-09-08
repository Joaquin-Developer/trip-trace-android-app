package com.techvibedev.triptrace.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.techvibedev.triptrace.ui.components.TripTraceBottomNavBar
import com.techvibedev.triptrace.ui.screens.activetrip.ActiveTripScreen
import com.techvibedev.triptrace.ui.screens.createtrip.CreateTripScreen
import com.techvibedev.triptrace.ui.screens.history.HistoryScreen
import com.techvibedev.triptrace.ui.screens.login.LoginScreen
import com.techvibedev.triptrace.ui.screens.trips.TripsScreen

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
        floatingActionButton = {
            if (currentRoute == Routes.TRIPS) {
                FloatingActionButton(onClick = { navController.navigate(Routes.CREATE_TRIP) }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Nuevo viaje")
                }
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
                TripsScreen(
                    onStartTrip = { tripId ->
                        navController.navigate("${Routes.ACTIVE_TRIP}/$tripId")
                    },
                )
            }
            composable(Routes.HISTORY) {
                HistoryScreen()
            }
            composable(Routes.CREATE_TRIP) {
                CreateTripScreen()
            }
            composable("${Routes.ACTIVE_TRIP}/{tripId}") { backStackEntry ->
                val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
                ActiveTripScreen(tripId = tripId)
            }
        }
    }
}
