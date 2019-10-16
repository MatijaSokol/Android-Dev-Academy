package hr.ferit.matijasokol.taskie.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hr.ferit.matijasokol.taskie.model.BackendTask

@Database(version = 1, entities = [BackendTask::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase(){

    abstract fun taskDao(): TaskDao

    companion object{
        private const val NAME = "TASK DATABASE"
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as TaskDatabase
        }
    }
}