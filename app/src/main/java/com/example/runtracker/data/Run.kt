package com.example.runtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "run_table")
data class Run(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val distance: Int,
    val time: Long,
    val trace: Trace
    )