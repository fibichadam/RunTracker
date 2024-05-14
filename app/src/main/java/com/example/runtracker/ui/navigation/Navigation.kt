package com.example.runtracker.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.runtracker.Routes
import com.example.runtracker.ui.screens.ListScreen
import com.example.runtracker.ui.screens.MainScreen
import com.example.runtracker.ui.screens.ReportScreen
import com.example.runtracker.viewModel.RunViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(navCon: NavHostController, viewModel: RunViewModel) {
    Scaffold(
        bottomBar = { BottomMenu(navCon)},
        content = { BottomNavGraph(navCon, viewModel) }
    )

}

@Composable
fun BottomNavGraph(navCon: NavHostController, viewModel: RunViewModel) {
    NavHost(
        navController = navCon,
        startDestination = Routes.Main.route
    ) {
        composable(route = Routes.Main.route){ MainScreen(navCon, viewModel) }
        composable(route = Routes.List.route){ ListScreen(navCon, viewModel) }
        composable(route = Routes.Report.route){ navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("listNumber")
            id?.let {
                ReportScreen(navCon, it.toInt())
            }
        }
    }
}

@Composable
fun BottomMenu(navCon: NavHostController) {
    val screens = listOf(
        BottomBar.Main, BottomBar.Reports
    )
    val navBackStackEntry by navCon.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(modifier = Modifier.background(Color(200,200,230))){
        screens.forEach{ screen ->
            NavigationBarItem(
                label = { Text(text = screen.title) },
                icon = { Icon(imageVector = screen.icon, contentDescription = "test") },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = { if(currentDestination?.route != screen.route) navCon.navigate(screen.route) }
            )
        }
    }}
