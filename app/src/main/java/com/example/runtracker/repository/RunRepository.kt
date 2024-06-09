package com.example.runtracker.repository

import com.example.runtracker.data.Run
import com.example.runtracker.data.RunDao

class RunRepository(private val runDao: RunDao) {
    fun getRuns() = runDao.getRuns()

    suspend fun clear() = runDao.deleteAll()
    suspend fun add(subject: Run) = runDao.insert(subject)
    suspend fun delete(id: Int) = runDao.delete(id)
}