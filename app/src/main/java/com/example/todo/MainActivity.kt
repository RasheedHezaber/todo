package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var  tabLayout: TabLayout
    lateinit var tabViewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tabLayout=findViewById(R.id.main_tab_layout)
        tabViewPager=findViewById(R.id.main_view_Pager) as ViewPager2

        tabViewPager.adapter=object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return 3
            }


            override fun createFragment(position: Int): Fragment {
                return  when(position){

                    0->TaskFragment.newInstance("todo")
                    1->TaskFragment.newInstance("inprogress")
                    2->TaskFragment.newInstance("done")


                    else->TaskFragment.newInstance("done")
                }
            }


        }

        TabLayoutMediator(tabLayout,tabViewPager){tab,position->
            tab.text=when(position){
                0->"todo"
                1->"in progress"
                2->"done"
                else -> null
            }

        }.attach()
    }
}