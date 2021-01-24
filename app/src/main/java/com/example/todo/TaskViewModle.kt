package com.example.todo

import androidx.lifecycle.ViewModel



class TaskViewModle : ViewModel() {



    private  val taskRepo= TaskRepository.get()
    val taskListLiveData=taskRepo.todotasks()
    val taskListLiveDatainprogress=taskRepo.getInprogressTasks()
    val taskListLiveDataDone=taskRepo.getDoneTasks()

    fun addTask(task: OrganizeTasks) {
        taskRepo.addTask(task)
    }
    fun updateTaskState(task: OrganizeTasks, i: Int) {
        taskRepo.updateTask(task,i)
    }
    fun deleteTasks(task: OrganizeTasks){
        taskRepo.deleteTask(task)
    }
    fun updateTasks(task: OrganizeTasks){
        taskRepo.updateTask(task)
    }

}