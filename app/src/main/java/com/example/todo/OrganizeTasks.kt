package com.example.todo

import android.provider.ContactsContract
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "task_table")

        data class OrganizeTasks(
                       @PrimaryKey
                        val id: UUID = UUID.randomUUID(),
                         var title: String,
                         var details: String="",
                          var startdate:Date=Date(),
                           var endDate: Date = Date(),
                            var state:Int=1
                         ) {}