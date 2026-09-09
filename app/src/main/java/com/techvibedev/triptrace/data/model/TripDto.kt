package com.techvibedev.triptrace.data.model

import com.google.gson.annotations.SerializedName

data class TripCreateRequest(
    @SerializedName("origin_name") val originName: String,
    @SerializedName("origin_lat") val originLat: Double,
    @SerializedName("origin_lng") val originLng: Double,
    @SerializedName("destination_name") val destinationName: String,
    @SerializedName("destination_lat") val destinationLat: Double,
    @SerializedName("destination_lng") val destinationLng: Double,
    @SerializedName("planned_departure_at") val plannedDepartureAt: String? = null,
    @SerializedName("desired_arrival_at") val desiredArrivalAt: String? = null,
)

data class TripUpdateRequest(
    val status: String? = null,
    @SerializedName("started_at") val startedAt: String? = null,
    @SerializedName("ended_at") val endedAt: String? = null,
)

data class TripResponse(
    val id: String,
    @SerializedName("origin_name") val originName: String,
    @SerializedName("destination_name") val destinationName: String,
    val status: String,
    @SerializedName("planned_departure_at") val plannedDepartureAt: String?,
    @SerializedName("desired_arrival_at") val desiredArrivalAt: String?,
)
