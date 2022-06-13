package com.PisangHitam.InstaFashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_notification_main.*
import kotlinx.android.synthetic.main.activity_shop_tracker.*

class NotificationMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_main)
        val actionbar = supportActionBar
        actionbar!!.title = "Notification"
        actionbar.setDisplayHomeAsUpEnabled(true)
        var db = singletonData.getRoomHelper(this)
        var user = singletonData.getCurUserObj(this)
        var data = user!!.notifications.reversed()
        if(data.isEmpty()){
            hiddenTextBehindTheWaterfall.visibility = TextView.VISIBLE
        }
        else{
            hiddenTextBehindTheWaterfall.visibility = TextView.GONE
        }
        var adapter = recycler_notification(data)
        notifRecyView.layoutManager = LinearLayoutManager(this)
        notifRecyView.adapter = adapter
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}