package com.example.runtracker.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.runtracker.utils.formatDistance
import com.example.runtracker.utils.formatTime
import com.example.runtracker.viewModel.RunViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ReportScreen(navCon: NavHostController, viewModel: RunViewModel, index: Int) {
    val run = viewModel.runState.value[index]
    val cam = rememberCameraPositionState {position = CameraPosition.fromLatLngZoom(run.trace.trace[run.trace.trace.size/2], 14f)}
    Column (){
        Row(modifier = Modifier
            .weight(0.7f)
            .background(Color.Green)
            .fillMaxWidth())
        {
            GoogleMap (cameraPositionState = cam){
                Polyline(
                    points = run.trace.trace,
                    width = 10f,
                    color = Color.Red)
            }
        }


        Row (modifier = Modifier.weight(0.1f).fillMaxWidth().background(Color.White)){
            Text(
                buildAnnotatedString {
                    append("dystans\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append(run.distance.formatDistance())
                    }
                },
                modifier = Modifier.weight(1f)
                    .align(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp)
            Text(
                buildAnnotatedString {
                    append("czas\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append(run.time.formatTime())
                    }
                },
                modifier = Modifier.weight(1f)
                    .align(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp)
        }
        Row (modifier = Modifier.weight(0.1f).fillMaxWidth().background(Color.White)){
            Text(
                buildAnnotatedString {
                    append("śr. tempo\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append("${(1000.0/run.distance*run.time).toLong().formatTime()} / km")
                    }
                },
                modifier = Modifier.weight(1f)
                    .align(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp)
            Text(
                buildAnnotatedString {
                    append("kalorie\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append("${80*run.distance/1000} kcal")
                    }
                },
                modifier = Modifier.weight(1f)
                    .align(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp)
        }
        Row(modifier = Modifier.weight(0.1f)){}
    }
}