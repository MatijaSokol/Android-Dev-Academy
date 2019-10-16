package hr.ferit.matijasokol.taskie.db

import androidx.room.TypeConverter
import hr.ferit.matijasokol.taskie.model.BackendPriorityTask

class Converters {

    companion object{
        @TypeConverter
        @JvmStatic
        fun fromPriority(value: BackendPriorityTask): Int = value.getValue()

        @TypeConverter
        @JvmStatic
        fun toPriority(value: Int): BackendPriorityTask = when(value){
            BackendPriorityTask.LOW.getValue() -> BackendPriorityTask.LOW
            BackendPriorityTask.MEDIUM.getValue() -> BackendPriorityTask.MEDIUM
            else -> BackendPriorityTask.HIGH
        }
    }
}