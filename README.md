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

## Branching model

- `main`: released code only (production). Updated via a `release/{version}` branch merged with a PR.
- `develop`: integration branch. All `feature/{name}`, `doc/{name}`, etc. branches merge here via PR.
- `feature/{name}`, `doc/{name}`, ...: one branch per unit of work, branched off `develop`.
- `release/{version}` (e.g. `release/0.0.1`): cut from `develop` when preparing a production release; merged into `main` via PR.

## Releases

| Version | PR | Summary |
| --- | --- | --- |
