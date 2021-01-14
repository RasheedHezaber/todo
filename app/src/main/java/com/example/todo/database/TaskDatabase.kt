package com.example.todo.database

import androidx.room.Database
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.OrganizeTasks

@Database(entities = [OrganizeTasks::class], version = 2, exportSchema = false)

@TypeConverters(ConDb::class)

abstract class TaskDatabase:RoomDatabase() {
    abstract fun taskDao(): TaskDao
}