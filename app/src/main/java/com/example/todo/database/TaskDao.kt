package com.example.todo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo.OrganizeTasks
import java.util.*

@Dao
interface TaskDao {
    @Query("SELECT* FROM  task_table where state=(:state) ")
    fun getToDoTasks(state:Int): LiveData<List<OrganizeTasks>>


 @Query("SELECT* FROM  task_table WHERE state=(:state)  ")
    fun getStateTasks(state:Int): LiveData<List<OrganizeTasks>>

    @Query("SELECT * FROM task_table WHERE id=(:id)")
    fun getTask(id: UUID): LiveData<OrganizeTasks?>

    @Insert
    fun addTask(task: OrganizeTasks)

    @Query("UPDATE task_table SET state=(:state) Where id=(:id) ")
    fun updateTaskToInprogress(id:UUID,state:Int)
    @Delete
    fun delete(task: OrganizeTasks)

    @Update()
    fun updateTask(task: OrganizeTasks)
}