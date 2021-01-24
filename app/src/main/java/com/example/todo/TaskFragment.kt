package com.example.todo

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import androidx.recyclerview.widget.RecyclerView.Adapter as Adapter1


var TAP_INDEX = "tab index"

@Suppress("UNREACHABLE_CODE", "DEPRECATION")
class TaskFragment : Fragment(),DialogFragmentin.InputCallbacks{
    val sdf = SimpleDateFormat("EEE, MMM d, ''yy")
    val currentDate = sdf.format(Date())
    val currentDate2 = sdf.format(Date())
    private lateinit var taskRecyclerView: RecyclerView

    private val taskViewModel by lazy{
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TaskViewModle::class.java)
    }
    var type:String=""
    private var adapter: TaskAdapter? = TaskAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
        type=arguments?.getSerializable("type")as String
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_blank, container, false)
        taskRecyclerView = view.findViewById(R.id.task_recycler) as RecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskViewModel.apply {
            if (type=="todo")
                taskListLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {tasks->tasks.let{ tasks
                        updateUI(tasks)
                }  })
            else  if (type=="done")
                taskListLiveDatainprogress.observe(viewLifecycleOwner, androidx.lifecycle.Observer {tasks->tasks.let{ tasks
                    updateUI(tasks)
                }  })
            else
                taskListLiveDataDone.observe(viewLifecycleOwner, androidx.lifecycle.Observer {tasks->tasks.let{ tasks
                    updateUI(tasks)
                }  })
        }


    }
    private inner class TaskHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = itemView.findViewById(R.id.title)
        val det: TextView = itemView.findViewById(R.id.det)
        val startdate: TextView = itemView.findViewById(R.id.stdate)
        val enddate: TextView = itemView.findViewById(R.id.endate)
        val next: Button = itemView.findViewById(R.id.next)
        val linearTodo: LinearLayout = itemView.findViewById(R.id.linear_todo)
        val back: Button = itemView.findViewById(R.id.back)
        val card: CardView = itemView.findViewById(R.id.card)
        val delete: ImageButton = itemView.findViewById(R.id.delete)
        var update:ImageButton=itemView.findViewById(R.id.update)
//
        private lateinit var tasks: OrganizeTasks
        fun bind(item: OrganizeTasks) {
            this.tasks = item
            var databaseDate=sdf.format(tasks.startdate)
            var databaseDate2=sdf.format(tasks.endDate)

            title.text = this.tasks.title
            det.text = this.tasks.details
            enddate.text =databaseDate2
            startdate.text =databaseDate

            var date1=currentDate
            var date2= databaseDate
            var date3=currentDate2
            var date4=databaseDate2
            val difference: Long = Math.abs(sdf.parse(date1).time - sdf.parse(date2).time)
            val differenceDates = difference / (24 * 60 * 60 * 1000)
            val dayDifference = differenceDates.toString().toInt()


            if (currentDate.equals(databaseDate))
            else (dayDifference <=3)

            val difference2: Long = Math.abs(sdf.parse(date3).time - sdf.parse(date4).time)
            val differenceDates2 = difference2 / (24 * 60 * 60 * 1000)
            val dayDifference2 = differenceDates2.toString().toInt()

            if (currentDate.equals(databaseDate))
            else (dayDifference2 <=3)


        }

        init {

            if (type=="todo"){

                back.visibility=View.GONE
            }
            else if (type=="done"){
                next.visibility=View.GONE
            }else if (type=="inprogress"){

                back.visibility=View.VISIBLE
                next.visibility=View.VISIBLE

            }
            next.setOnClickListener {
                if (type=="todo"){
                    taskViewModel.updateTaskState(tasks,3)
                }
                else{
                    taskViewModel.updateTaskState(tasks, 1)
                }
            }
            back.setOnClickListener {

                if (type=="inprogress"){

                        taskViewModel.updateTaskState(tasks,2)

                }else{

                        taskViewModel.updateTaskState(tasks, 3)
                }

            }
            delete.setOnClickListener { taskViewModel.deleteTasks(tasks) }
            update.setOnClickListener {
                DialogFragmentin.newInstance(tasks).apply{
                    setTargetFragment(this@TaskFragment,1)
                    show(this@TaskFragment.requireFragmentManager(),"update")
                }
            }
        }

    }
    private inner class TaskAdapter(var tasks: List<OrganizeTasks>) : Adapter1<TaskHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
            val view = layoutInflater.inflate(R.layout.todo_item, parent, false)
            return TaskHolder(view)
        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = tasks[position]
            holder.bind(task)

        }

        override fun getItemCount(): Int {
            return tasks.size
        }


    }
    private fun updateUI(tasks: List<OrganizeTasks>) {
        adapter = TaskAdapter(tasks)
        taskRecyclerView.adapter = adapter
    }
    companion object {

        @JvmStatic
        fun newInstance(data:String):TaskFragment{
            val args=Bundle().apply {
                putSerializable("type",data)
            }
            return  TaskFragment().apply {
                arguments=args
            }
        }

    }

   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.add_new_task -> {
                DialogFragmentin.newInstance().apply {
                    setTargetFragment(this@TaskFragment, 0)
                    show(this@TaskFragment.requireFragmentManager(), "input")

                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onTaskAdd(task: OrganizeTasks) {

            taskViewModel.addTask(task)
    }

    override fun onTaskDelete(task: OrganizeTasks) {

        taskViewModel.deleteTasks(task)
    }

    override fun onTaskUpdate(task: OrganizeTasks) {
        taskViewModel.updateTasks(task)
    }

}