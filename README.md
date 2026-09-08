# TripTrace Android App

Android app (Kotlin + Jetpack Compose) that records car trips: actual route, planned route,
stops, and speed metrics. Tracking runs via a foreground service so it keeps recording with
the screen off.

Trips are stored locally first (Room) and synced to trip-trace-api once a trip ends.

## Stack

- Kotlin + Jetpack Compose
- Room (local database)
- Retrofit + OkHttp (API sync)
- DataStore (auth token storage)

## Getting started

Open this project in Android Studio. Gradle sync will download the wrapper and dependencies
automatically.
