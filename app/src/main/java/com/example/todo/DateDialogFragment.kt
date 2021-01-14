package com.example.todo

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import java.util.*


private const val ARG_DATE = "date"
private const val ARG_STRING = "string"
class DateDialogFragment:DialogFragment() {

    lateinit var str: String
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            str=arguments?.getString(ARG_STRING)!!
        val dateListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
            val resultDate : Date = GregorianCalendar(year, month, day).time
            targetFragment?.let { fragment ->
                if(str=="start")
                   (fragment as DateCallbacks).onstartDateSelected(resultDate)
                else
                    (fragment as DateCallbacks).onEndDateSelected(resultDate)
               Log.e("dt",str)
            }
        }

        var calendar= Calendar.getInstance()
        var year= calendar.get(Calendar.YEAR)
        var month=  calendar.get(Calendar.MONTH)
        var day= calendar.get(Calendar.DAY_OF_WEEK)

        return  DatePickerDialog(requireContext(),dateListener,year,month,day)
    }


    interface DateCallbacks {

        fun onstartDateSelected(date: Date)
        fun onEndDateSelected(endDate: Date)


    }

    companion object {
        fun newInstance(date: Date, s: String): DateDialogFragment {

            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
                putString(ARG_STRING, s)
            }
            return DateDialogFragment().apply {
                arguments = args
            }

        }
    }
}