package com.example.runtracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.pill
import androidx.graphics.shapes.toPath
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.runtracker.Routes
import com.example.runtracker.utils.formatDistance
import com.example.runtracker.utils.formatTime
import com.example.runtracker.viewModel.RunViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ListScreen(navCon: NavHostController, viewModel: RunViewModel) {
    val runs by viewModel.runState.collectAsStateWithLifecycle()

    Column {
        Text(
            text = "Moje Raporty",
            textAlign = TextAlign.Center,
            fontSize = TextUnit(30f, TextUnitType.Sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .weight(0.1f)
        )

        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 10.dp)
                .weight(0.85f)

        ) {
            items(runs.size) {
                Box(modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxSize()
                    .height(120.dp)
                    .clickable { navCon.navigate(Routes.Report.route + "/$it") }
                    .drawWithCache {
                        val roundedPolygon = RoundedPolygon.pill(
                            width = size.width,
                            height = size.height,
                            centerX = size.width / 2,
                            centerY = size.height / 2,
                            smoothing = -0.6f
                        )
                        val roundedPolygonPath = roundedPolygon
                            .toPath()
                            .asComposePath()
                        onDrawBehind {
                            drawPath(roundedPolygonPath, color = Color(255, 200, 100))
                        }
                    }

                ) {
                    Text(
                        text = SimpleDateFormat("yyyy MMMM dd - EEEE HH:mm", Locale.ENGLISH).format(Date(runs[it].date)),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 15.dp, top = 10.dp)
                    )
                    Text(
                        text = runs[it].distance.formatDistance(),
                        fontSize = 26.sp,
                        lineHeight = TextUnit(20f, TextUnitType.Sp),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 15.dp)
                    )
                    Text(
                        text = runs[it].time.formatTime(),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 15.dp, bottom = 10.dp)
                    )
                }
            }
        }
        Column(Modifier.weight(0.1f)){}
    }
}