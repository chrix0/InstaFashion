package com.PisangHitam.InstaFashion.BR_SettingsChanged

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import com.PisangHitam.InstaFashion.BR_recNotifier
import com.PisangHitam.InstaFashion.NOTIF_PRODUCTREC_CHANGED
import com.PisangHitam.InstaFashion.profile_settings
import com.PisangHitam.InstaFashion.singletonData
import org.jetbrains.anko.appcompat.v7.actionBarContainer

class BR_productRecChanged() : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == "Undo"){
            var sendIntent = Intent(context, BR_recNotifier::class.java)
            var alarmTimer = Calendar.getInstance()
            //alarmTimer.add(Calendar.SECOND, 15)

            singletonData.mAlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            singletonData.mPendingIntent = PendingIntent.getBroadcast(context, 200, sendIntent, 0 )
            singletonData.mAlarmManager!!.setInexactRepeating(AlarmManager.RTC, alarmTimer.timeInMillis,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,singletonData.mPendingIntent)

            //Tutup notifikasi
            var manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.cancel(NOTIF_PRODUCTREC_CHANGED)

            //Buka kembali activity.
            var intent = Intent()
               intent.apply {
                   //Memulai activity di luar activity context harus menyebutkan nama package dan class cara manual
                   setClassName("com.PisangHitam.InstaFashion", "com.PisangHitam.InstaFashion.profile_settings")
                   //dan menambahkan flag FLAG_ACTIVITY_NEW_TASK
                   flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
               }
            context.startActivity(intent)
        }
    }
}