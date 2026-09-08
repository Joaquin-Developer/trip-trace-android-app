package com.techvibedev.triptrace.ui.screens.trips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class PlannedTrip(
    val id: String,
    val originLabel: String,
    val destinationLabel: String,
    val departureTimeLabel: String,
    val isStale: Boolean,
)

// Mock data for now — Room (android#4) and the create-trip flow will replace
// this. UI only, matches the approved mockup.
private val mockPlannedTrips = listOf(
    PlannedTrip(
        id = "1",
        originLabel = "Casa",
        destinationLabel = "Aeropuerto",
        departureTimeLabel = "18:30",
        isStale = false,
    ),
    PlannedTrip(
        id = "2",
        originLabel = "Oficina",
        destinationLabel = "Casa",
        departureTimeLabel = "09:00",
        isStale = true,
    ),
)

@Composable
fun TripsScreen(onStartTrip: (String) -> Unit) {
    if (mockPlannedTrips.isEmpty()) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Text(
                text = "No tenes viajes planeados todavia.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(mockPlannedTrips) { trip ->
            PlannedTripCard(trip = trip, onStartTrip = onStartTrip)
        }
    }
}

@Composable
private fun PlannedTripCard(trip: PlannedTrip, onStartTrip: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "${trip.originLabel} \u2192 ${trip.destinationLabel}",
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (trip.isStale) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(
                        text = "Hora planeada (${trip.departureTimeLabel}) ya paso",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    OutlinedButton(
                        onClick = { /* TODO: update departure time to now */ },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text("Usar hora actual")
                    }
                    Button(
                        onClick = { onStartTrip(trip.id) },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text("Iniciar")
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp),
                    )
                    Text(
                        text = "Sale ${trip.departureTimeLabel}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f),
                    )
                    Button(onClick = { onStartTrip(trip.id) }) {
                        Text("Iniciar")
                    }
                }
            }
        }
    }
}
