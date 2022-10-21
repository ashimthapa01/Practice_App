package com.example.practiceapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.*
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import android.content.SharedPreferences;
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class DashActivity : AppCompatActivity() {

    private var titleList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private lateinit var view_pager2: ViewPager2
    lateinit var preferences: SharedPreferences
    lateinit var progressbar: ProgressBar
    var txtBatteryStatus: TextView? = null
    var back: ImageView? = null;
    var edit = preferences.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)
        val name1: TextView = findViewById(R.id.tv_name)
        var logoutbtn = findViewById<Button>(R.id.logoutbtn)
        logoutbtn.setOnClickListener(){
            edit.remove("full name")

        }
        preferences = getSharedPreferences(LandingActivity.FILE_NAME, Context.MODE_PRIVATE)
        val name = preferences.getString("full name", "")
        name1.text = name
        progressbar = findViewById(R.id.progressbar)

        postToList()
        txtBatteryStatus = findViewById(R.id.txtbattery)
        registerReceiver(this.mBatteryInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        back?.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Please Confirm.")
                .setMessage("You sure you want to Exit??")
                .setPositiveButton("Yes") { _, _ ->
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(this.mBatteryInfoReceiver)
    }


    override fun onBackPressed() {
        Toast.makeText(this, "close", Toast.LENGTH_SHORT).show()
        AlertDialog.Builder(this)
            .setTitle("Please Confirm.")
            .setMessage("You sure you want to Exit??")
            .setPositiveButton("Yes") { _, _ ->
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private val mBatteryInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context?, intent: Intent?) {
            val level = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, 101)

            txtBatteryStatus!!.text = "Battery Status: $level%"
            progressbar.progress = level

        }
    }

    private fun addToList(title: String, description: String) {
        titleList.add(title)
        descList.add(description)
    }

    private fun postToList() {
        for (i in 1..5) {
            addToList("Title $i", "Description $i")
        }
        view_pager2 = findViewById(R.id.view_pager2)
        view_pager2.adapter = ViewPagerAdapter(titleList, descList)
        val indicator: CircleIndicator3 = findViewById(R.id.indicator)
        indicator.setViewPager(view_pager2)
    }

}