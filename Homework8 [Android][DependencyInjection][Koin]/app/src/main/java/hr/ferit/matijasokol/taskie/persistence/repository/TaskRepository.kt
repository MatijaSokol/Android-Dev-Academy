package hr.ferit.matijasokol.taskie.persistence.repository

import hr.ferit.matijasokol.taskie.model.BackendTask

interface TaskRepository {
    fun addTask(task: BackendTask)
    fun getTask(taskId: String): BackendTask
    fun getAllTasks(): List<BackendTask>
    fun deleteTask(task: BackendTask)
    fun deleteAll()
    fun updateTask(taskId: String?, title: String, content: String, priority: Int)
}