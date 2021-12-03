package com.sarco.todoapp.data

import androidx.room.TypeConverter
import com.sarco.todoapp.data.models.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String = priority.name

    @TypeConverter
    fun toPriority(priority: String): Priority = Priority.valueOf(priority)


}