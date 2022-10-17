package com.example.practiceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3

class DashActivity : AppCompatActivity() {
    private var titleList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private lateinit var view_pager2: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)
        postToList()
    }

    private fun addToList(title: String, description: String){
        titleList.add(title)
        descList.add(description)
    }

    private fun postToList(){
        for (i in 1..5){
            addToList("Title $i","Description $i")
        }
        view_pager2 = findViewById(R.id.view_pager2)
        view_pager2.adapter = ViewPagerAdapter(titleList, descList)
        val indicator: CircleIndicator3 = findViewById(R.id.indicator)
        indicator.setViewPager(view_pager2)
    }

}