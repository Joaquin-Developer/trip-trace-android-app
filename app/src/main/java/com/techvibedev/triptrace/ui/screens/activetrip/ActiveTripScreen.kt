package com.techvibedev.triptrace.ui.screens.activetrip

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// Placeholder until the real screen is built (see issue android#7).
@Composable
fun ActiveTripScreen(tripId: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Viaje en tiempo real ($tripId) - proximamente",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
