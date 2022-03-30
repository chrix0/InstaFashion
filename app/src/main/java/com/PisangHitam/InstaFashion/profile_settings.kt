package com.PisangHitam.InstaFashion

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.PisangHitam.InstaFashion.BR_SettingsChanged.BR_productRecChanged
import kotlinx.android.synthetic.main.activity_profile_settings.*

class profile_settings : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        val actionbar = supportActionBar
        actionbar!!.title = "Settings"
        actionbar.setDisplayHomeAsUpEnabled(true)

        //====Recommendation Notification====
        recNotifSwitch.isChecked = singletonData.mAlarmManager != null

        recNotifSwitch.setOnCheckedChangeListener { button, b ->
            if(b){
                var sendIntent = Intent(this, BR_recNotifier::class.java)
                var alarmTimer = Calendar.getInstance()
                alarmTimer.add(Calendar.SECOND, 15)

                singletonData.mAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                singletonData.mPendingIntent = PendingIntent.getBroadcast(this, 200, sendIntent, 0 )
                singletonData.mAlarmManager!!.setInexactRepeating(AlarmManager.RTC, alarmTimer.timeInMillis, AlarmManager.INTERVAL_FIFTEEN_MINUTES,singletonData.mPendingIntent)
            }
            else{
                singletonData.mAlarmManager?.cancel(singletonData.mPendingIntent)
                singletonData.mPendingIntent?.cancel()
                singletonData.mAlarmManager = null
                singletonData.mPendingIntent = null

                //Notifikasi Undo
                val NotifyId = NOTIF_PRODUCTREC_CHANGED
                val Channel_id = "channel_recs_change"
                val name = "ProductRecommendationChangeAlert"
                val importance = NotificationManager.IMPORTANCE_HIGH

                val NotifyChannel : NotificationChannel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel(Channel_id, name, importance).apply {
                        //Dot Notification
                        description = "Product Recommendations Disabled"
                        setShowBadge(true)
                    }
                } else {
                    Toast.makeText(this, "Unable to show notification.", Toast.LENGTH_LONG).show()
                    null
                }

                val undoIntent = Intent(applicationContext, BR_productRecChanged::class.java)
                undoIntent.action = "Undo"
                val undoPendingIntent = PendingIntent.getBroadcast(applicationContext, 0, undoIntent,0)

                var mBuilder = NotificationCompat.Builder(this, Channel_id)
                    .setSmallIcon(R.drawable.logo01)
                    .setContentText("Product recommendation notifications has been disabled.")
                    .setContentTitle("Notification disabled")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .addAction(R.drawable.icon_undo, "Undo", undoPendingIntent)

                var mNotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                if (NotifyChannel != null) {
                    mNotificationManager.createNotificationChannel(NotifyChannel)
                }

                mNotificationManager.notify(NotifyId, mBuilder.build())
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun update(recNotifierState:Boolean){
        recNotifSwitch.isChecked = recNotifierState
    }

}