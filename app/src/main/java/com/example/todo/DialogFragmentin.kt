package com.example.todo

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.util.*


@Suppress("DEPRECATION")
open class DialogFragmentin : DialogFragment(),DateDialogFragment.DateCallbacks {



    lateinit var tasks: OrganizeTasks
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        tasks= OrganizeTasks(UUID.randomUUID(),"","", Date(),Date(),1)
        val r =activity?.layoutInflater?.inflate(R.layout.moving_in, null)
        val det= r?.findViewById(R.id.ed_det) as EditText
        val title=r.findViewById(R.id.ed_title) as EditText
        val ed_date=r.findViewById(R.id.ed_date) as Button
        val start_date=r.findViewById(R.id.start_date) as Button

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

        return  android.app.AlertDialog.Builder(requireContext(),R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(r)
            .setPositiveButton("ADD"){dialog,_->
                val data=OrganizeTasks( UUID.randomUUID(),title.text.toString(),
                    det?.text.toString()
                    , tasks.startdate ,tasks.endDate,

                1)
                targetFragment.let {fragment ->
                    (fragment as InputCallbacks).onTaskAdd(data)
                }
            }.setNegativeButton("cancel"){dialog,_->
                dialog.cancel()
            }.create()


    }
    interface InputCallbacks {
        fun onTaskAdd(task: OrganizeTasks)
        fun onTaskDelete(task: OrganizeTasks)

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
    }

}