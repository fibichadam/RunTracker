package com.example.runtracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.runtracker.Routes

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Main: BottomBar(Routes.Main.route, "Bieg", Icons.Default.Home)
    object Reports: BottomBar(Routes.List.route, "Moje raporty", Icons.AutoMirrored.Filled.List)
}