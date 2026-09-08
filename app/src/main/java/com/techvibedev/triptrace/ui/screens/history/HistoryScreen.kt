package com.techvibedev.triptrace.ui.screens.history

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class PastTrip(
    val id: String,
    val originLabel: String,
    val destinationLabel: String,
    val dateLabel: String,
    val durationLabel: String,
    val distanceLabel: String,
    val maxSpeedLabel: String,
    val avgSpeedLabel: String,
    val statusLabel: String,
    val wasOnTime: Boolean,
)

// Mock data for now — will be replaced once trip history comes from the API
// (api#8). UI only, matches the approved mockup.
private val mockPastTrips = listOf(
    PastTrip(
        id = "1",
        originLabel = "Casa",
        destinationLabel = "Oficina",
        dateLabel = "Hoy",
        durationLabel = "49 min",
        distanceLabel = "31 km",
        maxSpeedLabel = "78 km/h",
        avgSpeedLabel = "41 km/h",
        statusLabel = "+15 min",
        wasOnTime = false,
    ),
    PastTrip(
        id = "2",
        originLabel = "Oficina",
        destinationLabel = "Casa",
        dateLabel = "Ayer",
        durationLabel = "38 min",
        distanceLabel = "29 km",
        maxSpeedLabel = "65 km/h",
        avgSpeedLabel = "38 km/h",
        statusLabel = "a tiempo",
        wasOnTime = true,
    ),
    PastTrip(
        id = "3",
        originLabel = "Casa",
        destinationLabel = "San Jacinto",
        dateLabel = "Lun",
        durationLabel = "1h 12min",
        distanceLabel = "68 km",
        maxSpeedLabel = "92 km/h",
        avgSpeedLabel = "54 km/h",
        statusLabel = "a tiempo",
        wasOnTime = true,
    ),
)

@Composable
fun HistoryScreen() {
    var expandedTripId by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(mockPastTrips) { trip ->
            PastTripCard(
                trip = trip,
                expanded = trip.id == expandedTripId,
                onToggleExpanded = {
                    expandedTripId = if (expandedTripId == trip.id) null else trip.id
                },
            )
        }
    }
}

@Composable
private fun PastTripCard(
    trip: PastTrip,
    expanded: Boolean,
    onToggleExpanded: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable(onClick = onToggleExpanded),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${trip.originLabel} \u2192 ${trip.destinationLabel}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = trip.dateLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Icon(
                        imageVector = if (expanded) {
                            Icons.Filled.KeyboardArrowUp
                        } else {
                            Icons.Filled.KeyboardArrowDown
                        },
                        contentDescription = if (expanded) "Contraer" else "Expandir",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(10.dp))
                RouteMapPlaceholder()
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    TripStat(label = "Maxima", value = trip.maxSpeedLabel)
                    TripStat(label = "Promedio", value = trip.avgSpeedLabel)
                    TripStat(label = "Distancia", value = trip.distanceLabel)
                }
            } else {
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(
                        text = trip.durationLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = trip.distanceLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = trip.statusLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (trip.wasOnTime) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun TripStat(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

// Simplified placeholder for the actual route map, which needs a maps SDK
// wired up to the recorded GPS points (see api#8 for that data).
@Composable
private fun RouteMapPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Mapa de la ruta",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
