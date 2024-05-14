package com.example.runtracker.location

interface LocationTracker {
    suspend fun startLocationUpdates()
}