package com.example.runtracker.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runtracker.data.Run
import com.example.runtracker.data.RunDatabase
import com.example.runtracker.repository.RunRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RunViewModel(application: Application) : ViewModel() {

    private val repository: RunRepository
    private var _runState = MutableStateFlow<List<Run>>(emptyList())
    val runState: StateFlow<List<Run>>
        get() = _runState


    init {
        val db = RunDatabase.getDatabase(application)
        val dao = db.runDao()
        repository = RunRepository(dao)

        fetchRuns()
    }

    private fun fetchRuns() {
        viewModelScope.launch {
            repository.getRuns().collect { runs ->
                _runState.value = runs
            }
        }
    }

    fun addRun(run: Run){
        viewModelScope.launch {
            repository.add(run)
        }
    }

    fun deleteRun(id: Int){
        viewModelScope.launch {
            repository.delete(id)
        }
    }

    fun clear() {
        viewModelScope.launch {
            repository.clear()
        }
    }
}
