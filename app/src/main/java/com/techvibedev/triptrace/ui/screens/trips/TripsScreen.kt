package com.techvibedev.triptrace.ui.screens.trips

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// Placeholder until the real "planned trips, ready to start" screen is built.
// (No dedicated issue yet — it came up after the original screen list was filed.)
@Composable
fun TripsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Viajes - proximamente",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
