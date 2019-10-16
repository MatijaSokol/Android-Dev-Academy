package hr.ferit.matijasokol.taskie.db

import androidx.room.*
import hr.ferit.matijasokol.taskie.model.BackendTask

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(task: BackendTask)

    @Delete
    fun delete(task: BackendTask)

    @Query("DELETE FROM BackendTask")
    fun deleteAll()

    @Query("SELECT * FROM BackendTask")
    fun getAll(): MutableList<BackendTask>

    @Query("SELECT * FROM BackendTask WHERE id = :taskId")
    fun getTask(taskId: String): BackendTask

    @Query("UPDATE BackendTask SET title= :taskTitle, content = :taskContent, taskPriority = :taskPriority WHERE id= :taskId")
    fun updateTask(taskId: String?, taskTitle: String, taskContent: String, taskPriority: Int)
}