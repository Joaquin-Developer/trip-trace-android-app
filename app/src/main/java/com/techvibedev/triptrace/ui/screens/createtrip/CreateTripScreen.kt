package com.techvibedev.triptrace.ui.screens.createtrip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// UI only for now: local state, mock calculated ETA, no real geocoding,
// routing, or persistence yet. Wiring this up to the create-trip API
// endpoint is tracked separately in api#6.
@Composable
fun CreateTripScreen() {
    var useCurrentLocation by remember { mutableStateOf(true) }
    var destination by remember { mutableStateOf("") }
    val stops = remember { mutableStateListOf<String>() }
    var newStop by remember { mutableStateOf("") }
    var departureTime by remember { mutableStateOf("18:30") }
    var desiredArrivalTime by remember { mutableStateOf("19:15") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Text(
            text = "Nuevo viaje",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(16.dp))

        OriginField(
            useCurrentLocation = useCurrentLocation,
            onChangeClick = { useCurrentLocation = !useCurrentLocation },
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destino") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(10.dp))

        stops.forEachIndexed { index, stop ->
            StopRow(label = stop, onRemove = { stops.removeAt(index) })
            Spacer(modifier = Modifier.height(6.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = newStop,
                onValueChange = { newStop = it },
                label = { Text("Agregar parada") },
                singleLine = true,
                modifier = Modifier.weight(1f),
            )
            IconButton(
                onClick = {
                    if (newStop.isNotBlank()) {
                        stops.add(newStop)
                        newStop = ""
                    }
                },
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Agregar parada")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                value = departureTime,
                onValueChange = { departureTime = it },
                label = { Text("Hora de salida") },
                singleLine = true,
                modifier = Modifier.weight(1f),
            )
            OutlinedTextField(
                value = desiredArrivalTime,
                onValueChange = { desiredArrivalTime = it },
                label = { Text("Quiero llegar") },
                singleLine = true,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        CalculatedArrivalCard(estimatedArrivalTime = "19:22")

        Spacer(modifier = Modifier.height(10.dp))

        RoutePreviewPlaceholder()

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { /* TODO: save trip via api#6 once wired up */ },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { /* TODO: save + navigate to active trip once wired up */ },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Guardar e iniciar ahora")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun OriginField(useCurrentLocation: Boolean, onChangeClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (useCurrentLocation) {
            Icon(
                imageVector = Icons.Filled.MyLocation,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = if (useCurrentLocation) "Ubicacion actual" else "Origen",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
        )
        TextButton(onClick = onChangeClick) {
            Text(if (useCurrentLocation) "Cambiar" else "Usar ubicacion actual")
        }
    }
}

@Composable
private fun StopRow(label: String, onRemove: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
        )
        IconButton(onClick = onRemove) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Quitar parada")
        }
    }
}

@Composable
private fun CalculatedArrivalCard(estimatedArrivalTime: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Llegada calculada",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Text(
            text = estimatedArrivalTime,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

// Simplified placeholder for the real route preview, which needs a maps SDK
// wired up to the calculated route (see api#6).
@Composable
private fun RoutePreviewPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Vista previa de la ruta",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
