package com.example.runtracker.data

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson

@ProvidedTypeConverter
class Converters(private val gson: Gson) {
    @TypeConverter
    fun fromTrace(t: Trace): String {
        return gson.toJson(t)
    }

    @TypeConverter
    fun toTrace(s: String): Trace{
        return gson.fromJson(s, Trace::class.java)
    }
}