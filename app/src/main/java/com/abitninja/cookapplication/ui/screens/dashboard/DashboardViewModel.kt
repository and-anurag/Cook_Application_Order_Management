package com.abitninja.cookapplication.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abitninja.cookapplication.data.model.Task
import com.abitninja.cookapplication.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    // State to hold the list of tasks
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    // State to show loading status
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // State to handle errors
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadTasks()
    }

    // Load tasks from the repository
    fun loadTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val taskList = taskRepository.getTasks()
                _tasks.value = taskList
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load tasks: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Mark a task as completed
    fun markTaskAsCompleted(taskId: String) {
        viewModelScope.launch {
            try {
                val success = taskRepository.markTaskAsCompleted(taskId)
                if (success) {
                    loadTasks() // Refresh tasks after marking as completed
                } else {
                    _errorMessage.value = "Failed to mark task as completed"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    // Listen for real-time task updates
    fun listenForTaskUpdates() {
        taskRepository.listenForTaskUpdates { updatedTasks ->
            _tasks.value = updatedTasks
        }
    }

}
