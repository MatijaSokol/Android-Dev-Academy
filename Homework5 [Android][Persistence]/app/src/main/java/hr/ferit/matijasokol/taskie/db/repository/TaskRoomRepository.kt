package hr.ferit.matijasokol.taskie.db.repository

import hr.ferit.matijasokol.taskie.app.Taskie
import hr.ferit.matijasokol.taskie.db.TaskDatabase
import hr.ferit.matijasokol.taskie.model.Priority
import hr.ferit.matijasokol.taskie.model.Task

class TaskRoomRepository : TaskRepository {

    private var taskDatabase = TaskDatabase.getInstance(Taskie.instance)

    private var taskDao = taskDatabase.taskDao()

    override fun addTask(task: Task) = taskDao.insert(task)
    override fun getTask(taskId: Long): Task = taskDao.getTask(taskId)

    override fun getAllTasks(): MutableList<Task> = taskDao.getAll()

    override fun deleteTask(task: Task) = taskDao.delete(task)

    override fun deleteAll() = taskDao.deleteAll()

    override fun updateTask(taskId: Long?, title: String, description: String, priority: Priority)
        = taskDao.updateTask(taskId, title, description, priority)
}
