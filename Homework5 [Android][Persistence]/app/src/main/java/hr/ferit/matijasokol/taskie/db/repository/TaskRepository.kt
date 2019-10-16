package hr.ferit.matijasokol.taskie.db.repository

import hr.ferit.matijasokol.taskie.model.Priority
import hr.ferit.matijasokol.taskie.model.Task

interface TaskRepository {
    fun addTask(task: Task)
    fun getTask(taskId: Long): Task
    fun getAllTasks(): List<Task>
    fun deleteTask(task: Task)
    fun deleteAll()
    fun updateTask(taskId: Long?, title: String, description: String, priority: Priority)
}