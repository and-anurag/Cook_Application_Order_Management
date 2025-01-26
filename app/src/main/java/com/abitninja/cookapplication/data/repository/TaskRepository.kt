package com.abitninja.cookapplication.data.repository

import com.abitninja.cookapplication.data.model.Task
import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await

class TaskRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("tasks")

    // Add a new task to the database
    suspend fun addTask(task: Task): Boolean {
        return try {
            val newTaskRef = database.push()
            task.id = newTaskRef.key ?: ""
            newTaskRef.setValue(task).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Retrieve all tasks from the database
    suspend fun getTasks(): List<Task> {
        return try {
            val snapshot = database.get().await()
            val tasks = mutableListOf<Task>()
            for (child in snapshot.children) {
                val task = child.getValue(Task::class.java)
                task?.let { tasks.add(it) }
            }
            tasks
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Update an existing task in the database
    suspend fun updateTask(task: Task): Boolean {
        return try {
            database.child(task.id).setValue(task).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Delete a task from the database
    suspend fun deleteTask(taskId: String): Boolean {
        return try {
            database.child(taskId).removeValue().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Mark a task as completed
    suspend fun markTaskAsCompleted(taskId: String): Boolean {
        return try {
            database.child(taskId).child("completed").setValue(true).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Listen for real-time updates to tasks
    fun listenForTaskUpdates(onTaskChanged: (List<Task>) -> Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tasks = mutableListOf<Task>()
                for (child in snapshot.children) {
                    val task = child.getValue(Task::class.java)
                    task?.let { tasks.add(it) }
                }
                onTaskChanged(tasks)
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })
    }
}