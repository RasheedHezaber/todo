package com.example.todo

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import java.text.DateFormat
import java.util.*


@Suppress("DEPRECATION")
open class DialogFragmentin : DialogFragment(),DateDialogFragment.DateCallbacks {



    lateinit var tasks: OrganizeTasks
    var up:Boolean = false
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (!up)
        tasks= OrganizeTasks(UUID.randomUUID(),"","", Date(),Date(),1)
        val r =activity?.layoutInflater?.inflate(R.layout.moving_in, null)
        val det= r?.findViewById(R.id.ed_det) as EditText
        val title=r.findViewById(R.id.ed_title) as EditText
        val ed_date=r.findViewById(R.id.ed_date) as Button
        val start_date=r.findViewById(R.id.start_date) as Button
        if (up){
            det.setText(tasks.details)
            title.setText(tasks.title)
        }
       ed_date?.setOnClickListener {
            DateDialogFragment.newInstance(tasks.endDate,"end").apply {
                setTargetFragment(this@DialogFragmentin,0)
                show(this@DialogFragmentin.requireFragmentManager(), "input")
            }
        }
        start_date?.setOnClickListener {
           DateDialogFragment.newInstance(tasks.startdate,"start").apply {
               setTargetFragment(this@DialogFragmentin,1)
               show(this@DialogFragmentin.requireFragmentManager(),"jnhjnl")
           }
            }
           var op:String
        if(up) op="UPDATE" else op="ADD"
        return  android.app.AlertDialog.Builder(requireContext(),R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(r)
            .setPositiveButton(op){dialog,_->
                Log.e("updated title",title.text.toString())
                val data=OrganizeTasks( tasks.id,title.text.toString(),
                    det?.text.toString()
                    , tasks.startdate ,tasks.endDate,

                1)
                targetFragment.let {fragment ->
                    if(!up)
                    (fragment as InputCallbacks).onTaskAdd(data)
                    else{
                        val taskViewModel by lazy{
                            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TaskViewModle::class.java)
                        }

                        taskViewModel.updateTasks(data)
//                        (fragment as InputCallbacks).onTaskUpdate(data)
                    }
                }
            }.setNegativeButton("cancel"){dialog,_->
                dialog.cancel()
            }.create()


    }
    interface InputCallbacks {
        fun onTaskAdd(task: OrganizeTasks)
        fun onTaskDelete(task: OrganizeTasks)
        fun onTaskUpdate(task:OrganizeTasks)
    }

    override fun onstartDateSelected(date: Date) {
        tasks.startdate = date
    }

    override fun onEndDateSelected(date2: Date) {
        tasks.endDate = date2
    }
    companion object {
        fun newInstance(): DialogFragmentin {

            return DialogFragmentin()
        }
        fun newInstance(t:OrganizeTasks): DialogFragmentin {
            var ins:DialogFragmentin=DialogFragmentin()
            ins.tasks=t
            ins.up=true
            return ins
        }
    }

}