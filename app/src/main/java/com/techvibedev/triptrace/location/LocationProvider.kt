package com.techvibedev.triptrace.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

class LocationProvider(context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    // Caller must have already checked/requested ACCESS_FINE_LOCATION (or at
    // least ACCESS_COARSE_LOCATION) before calling this — this class doesn't
    // handle the runtime permission itself.
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Result<Pair<Double, Double>> {
        return try {
            val cancellationTokenSource = CancellationTokenSource()
            val request = CurrentLocationRequest.Builder()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()

            val location = suspendCancellableCoroutine { continuation ->
                fusedLocationClient.getCurrentLocation(request, cancellationTokenSource.token)
                    .addOnSuccessListener { location -> continuation.resume(location) }
                    .addOnFailureListener { exception -> continuation.resumeWithException(exception) }

                continuation.invokeOnCancellation { cancellationTokenSource.cancel() }
            }

            if (location != null) {
                Result.success(location.latitude to location.longitude)
            } else {
                Result.failure(IllegalStateException("Location unavailable"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
