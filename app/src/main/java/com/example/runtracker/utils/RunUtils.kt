package com.example.runtracker.utils

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

fun Long.formatTime(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60

    return if(this < 3600)
        String.format("%02d:%02d", minutes, remainingSeconds)
    else
        String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}


fun Int.formatDistance(): String{
    return if(this < 1000)
        String.format("%d m", this)
    else
    {
        val kilo = this / 1000;
        val meters = (this % 1000) / 10;
        String.format("%d.%02d km", kilo, meters)
    }
}

fun calculateDistance(traceList: MutableList<LatLng>): Int{
    var dist = 0.0
    for(i in 1..<traceList.size)
    {
        dist += SphericalUtil.computeDistanceBetween(traceList[i-1], traceList[i])
    }
    return dist.toInt()
}