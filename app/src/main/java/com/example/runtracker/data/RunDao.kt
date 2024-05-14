package com.example.runtracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RunDao {
    @Query("SELECT * FROM run_table ORDER BY id DESC")
    fun getRuns(): Flow<List<Run>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(run: Run)

    @Query("DELETE FROM run_table WHERE id = :index")
    suspend fun delete(index: Int)

    @Query("DELETE FROM run_table")
    suspend fun deleteAll()
}