package com.example.runtracker

sealed class Routes(val route: String) {
    object Main : Routes("Main")
    object List : Routes("List")
    object Report : Routes("Report")
}