package hr.ferit.matijasokol.taskie.db.converters

import androidx.room.TypeConverter
import hr.ferit.matijasokol.taskie.model.Priority

class Converters {

    companion object{
        private const val PRIORITY_LOW = 2131099692
        private const val PRIORITY_MEDIUM = 2131099693
        private const val PRIORITY_HIGH = 2131099691

        @TypeConverter
        @JvmStatic
        fun fromPriority(value: Priority): Int = value.getColor()

        @TypeConverter
        @JvmStatic
        fun toPriority(value: Int): Priority = when(value){
            PRIORITY_LOW -> Priority.LOW
            PRIORITY_MEDIUM -> Priority.MEDIUM
            PRIORITY_HIGH -> Priority.HIGH
            else -> Priority.LOW
        }
    }
}