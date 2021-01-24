package com.example.todo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.todo.database.TaskDatabase
import java.util.concurrent.Executors

private const val DATABASE_NAME = "task-database"
class TaskRepository private constructor(context: Context){
    private val database: TaskDatabase = Room.databaseBuilder(context.applicationContext, TaskDatabase::class.java, DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()
    private val taskDao = database.taskDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun todotasks(): LiveData<List<OrganizeTasks>> = taskDao.getToDoTasks(1)
    fun getInprogressTasks(): LiveData<List<OrganizeTasks>> = taskDao.getToDoTasks(2)
    fun getDoneTasks(): LiveData<List<OrganizeTasks>> = taskDao.getToDoTasks(3)


    fun addTask(task: OrganizeTasks)
    {
        executor.execute {
            taskDao.addTask(task)
        }
    }
    fun updateTask(task: OrganizeTasks, i: Int) {
        executor.execute {
            taskDao.updateTaskToInprogress(task.id,i)
        }
    }
    fun deleteTask(task: OrganizeTasks){
        executor.execute {
            taskDao.delete(task)
    }

    }

    fun updateTask(task: OrganizeTasks){
        executor.execute {
            taskDao.updateTask(task)
        }

    }

    companion object {
        private var INSTANCE: TaskRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }
        }

        fun get(): TaskRepository {
            return INSTANCE ?: throw IllegalStateException("Task Repository must be initialized")
        }
    }
}