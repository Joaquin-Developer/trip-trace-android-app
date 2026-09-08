package com.techvibedev.triptrace.data.network

object ApiConfig {
    // Empty on purpose: trip-trace-api isn't deployed yet (api#1). Retrofit
    // will throw as soon as something actually calls the API while this is
    // blank — set it before wiring any screen to real network calls.
    //
    // For local testing against `uvicorn` running on your machine before a
    // prod URL exists, an Android emulator reaches your host via the special
    // alias 10.0.2.2, e.g. "http://10.0.2.2:8000/".
    const val BASE_URL = ""
}
