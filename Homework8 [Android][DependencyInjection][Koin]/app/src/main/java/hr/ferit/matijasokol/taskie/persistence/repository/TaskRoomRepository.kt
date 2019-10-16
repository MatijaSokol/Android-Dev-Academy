package hr.ferit.matijasokol.taskie.persistence.repository

import hr.ferit.matijasokol.taskie.app.Taskie
import hr.ferit.matijasokol.taskie.db.TaskDatabase
import hr.ferit.matijasokol.taskie.model.BackendTask

class TaskRoomRepository : TaskRepository {

    private var taskDatabase = TaskDatabase.getInstance(Taskie.instance)

    private var taskDao = taskDatabase.taskDao()

    override fun addTask(task: BackendTask) = taskDao.insert(task)

    override fun getTask(taskId: String): BackendTask = taskDao.getTask(taskId)

    override fun getAllTasks(): MutableList<BackendTask> = taskDao.getAll()

    override fun deleteTask(task: BackendTask) = taskDao.delete(task)

    override fun deleteAll() = taskDao.deleteAll()

    override fun updateTask(taskId: String?, title: String, content: String, priority: Int)
        = taskDao.updateTask(taskId, title, content, priority)
}
