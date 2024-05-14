package com.example.runtracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.runtracker.utils.formatDistance
import com.example.runtracker.utils.formatTime
import com.example.runtracker.viewModel.RunViewModel
import java.util.Date

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
                .padding(vertical = 15.dp)
        )

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)

        ) {
            items(runs.size) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.hsv(197f, .7f, 1f))
                    .height(80.dp)
//                    .clickable { navCon.navigate("listNumber/$it") }

                ) {
                    Text(
                        text = Date(runs[it].date).toString(),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 10.dp)
                    )
                    Text(
                        text = runs[it].distance.formatDistance(),
                        fontSize = 15.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 10.dp)
                    )
                    Text(
                        text = runs[it].time.formatTime(),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 10.dp)
                    )
                }
            }
        }
    }
}