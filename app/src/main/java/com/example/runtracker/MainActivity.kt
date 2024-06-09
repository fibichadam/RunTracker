package com.example.runtracker

import android.app.Application
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.runtracker.ui.navigation.Navigation
import com.example.runtracker.ui.theme.RunTrackerTheme
import com.example.runtracker.viewModel.RunViewModel
import com.example.runtracker.viewModel.RunViewModelProvider


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContent {
            RunTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Nav()
                }
            }
        }
    }

    @Composable
    fun Nav(){
        val viewModel: RunViewModel = viewModel(
            LocalViewModelStoreOwner.current!!,
            "RunViewModel",
            RunViewModelProvider(LocalContext.current.applicationContext as Application)
        )
        val navController = rememberNavController()
        Navigation(navController, viewModel)
    }
}
