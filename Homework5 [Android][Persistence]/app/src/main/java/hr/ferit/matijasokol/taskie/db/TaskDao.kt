package hr.ferit.matijasokol.taskie.db

import androidx.room.*
import hr.ferit.matijasokol.taskie.model.Priority
import hr.ferit.matijasokol.taskie.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("DELETE FROM Tasks")
    fun deleteAll()

    @Query("SELECT * FROM Tasks")
    fun getAll(): MutableList<Task>

    @Query("SELECT * FROM Tasks WHERE taskDbId = :taskId")
    fun getTask(taskId: Long): Task

    @Query("UPDATE Tasks SET title= :taskTitle, description = :taskDescription, priority = :taskPriority WHERE taskDbId= :taskId")
    fun updateTask(taskId: Long?, taskTitle: String, taskDescription: String, taskPriority: Priority)
}