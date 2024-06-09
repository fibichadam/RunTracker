package com.example.runtracker.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.runtracker.data.Run
import com.example.runtracker.data.Trace
import com.example.runtracker.location.DefaultLocationTracker
import com.example.runtracker.utils.calculateDistance
import com.example.runtracker.utils.formatTime
import com.example.runtracker.viewModel.RunViewModel
import com.example.runtracker.viewModel.TimerViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Calendar

val traceList: MutableList<LatLng> = mutableListOf()

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(navCon: NavHostController, viewModel: RunViewModel) {

    var startedTimer: Boolean by remember {
        mutableStateOf(false)
    }
    val timerViewModel: TimerViewModel by remember{
        mutableStateOf(TimerViewModel())
    }
    val timerValue by timerViewModel.timer.collectAsState()

    var buttonText: String by remember {
        mutableStateOf("Start")
    }

    val cameraPositionState = rememberCameraPositionState {position = CameraPosition.fromLatLngZoom(LatLng(51.0, 14.0), 16f) }

    val locationPermissions = rememberMultiplePermissionsState(permissions = listOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    var currentLocation: LatLng by remember{ mutableStateOf(LatLng(0.0, 0.0)) }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            for (location in p0.locations) {
                currentLocation = LatLng(location.latitude, location.longitude)
                cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLocation, 17f)

                if(startedTimer) {
                    traceList.add(currentLocation)
                    Log.i("size=", traceList.size.toString())
                }
            }
        }
    }

    val locationTracker = DefaultLocationTracker(locationCallback, LocationServices.getFusedLocationProviderClient(LocalContext.current))


    LaunchedEffect(true) {
        locationPermissions.launchMultiplePermissionRequest()

        if(locationPermissions.allPermissionsGranted)
            locationTracker.startLocationUpdates()
    }

    Column()
    {
        Row(modifier = Modifier
            .weight(0.6f)
            .background(Color.Green)
            .fillMaxWidth())
        {
             GoogleMap(
                cameraPositionState = cameraPositionState,
            ) {
                MarkerComposable(
                    state = MarkerState(currentLocation)
                ){
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "myPosition",
                        modifier = Modifier.size(Dp(30f))
                    )
                }
            }
        }

        Row(modifier = Modifier
            .weight(0.15f)
            .fillMaxWidth()
            .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center)
        {
            Text(text = timerValue.formatTime(), fontSize = TextUnit(60f, TextUnitType.Sp))
        }

        Row(modifier = Modifier
            .weight(0.15f)
            .fillMaxWidth()
            .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center)
        {
            Button(
                onClick = {
                    if(!startedTimer)
                    {
                        traceList.clear()
                        timerViewModel.stopTimer()
                        timerViewModel.startTimer()
                        buttonText = "Stop"
                    }
                    else
                    {
                        timerViewModel.pauseTimer()
                        viewModel.addRun(Run(
                            date = Calendar.getInstance().time.time,
                            distance = calculateDistance(traceList),
                            time = timerViewModel.timer.value,
                            trace = Trace(traceList.toList())
                        ))
                        buttonText = "Start"
                    }
                    startedTimer = !startedTimer
                },
                border = BorderStroke(2.dp, Color(255,200,100)),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(255,200,100,100)),
            ) {
                Text( text = buttonText, fontSize = TextUnit(30f, TextUnitType.Sp), color = Color.Black)
            }
        }
        Row(modifier = Modifier.weight(0.1f)){}
    }
}

