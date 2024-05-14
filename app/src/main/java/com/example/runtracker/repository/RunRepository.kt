package com.example.runtracker.repository

import com.example.runtracker.data.Run
import com.example.runtracker.data.RunDao

class RunRepository(private val subjectDao: RunDao) {
    fun getRuns() = subjectDao.getRuns()
    suspend fun clear() = subjectDao.deleteAll()
    suspend fun add(subject: Run) = subjectDao.insert(subject)
    suspend fun delete(id: Int) = subjectDao.delete(id)
}