package com.example.runtracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.gson.Gson


@Database(entities = [Run::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RunDatabase : RoomDatabase() {
    abstract fun runDao(): RunDao

    companion object {
        @Volatile
        private var Instance: RunDatabase? = null

        fun getDatabase(context: Context): RunDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RunDatabase::class.java, "run_database")
                    .apply { addTypeConverter(Converters(Gson())) }
                    .build()
                    .also { Instance = it }
            }
        }
    }
}