package com.PisangHitam.InstaFashion

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class BR_recNotifier : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val NotifyId = 30103
        val Channel_id = "channel_recs"
        val name = "ProductRecommendation"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val nNotifyChannel : NotificationChannel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(Channel_id, name, importance)
        } else {
            Toast.makeText(context, "Unable to show notification.", Toast.LENGTH_LONG).show()
            null
        }

        /*
        var bundleIntent = intent.getBundleExtra(EXTRA_NOTIF_MSG_REC)
        var obj : classRecNotif? = null
        if (bundleIntent != null){
            obj = bundleIntent.getParcelable(BUNDLE_NOTIF_MSG_REC)!!
        }
         */

        var product = getRandomProduct()
        var notifText = "${product.namaProduk}"
        var notifTitle = "Check this out!"

        //Notifikasi
        var mBuilder = NotificationCompat.Builder(context, Channel_id)
            .setSmallIcon(R.drawable.logo01)
            .setContentText(notifText)
            .setContentTitle(notifTitle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        var mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        for(s in mNotificationManager.notificationChannels){
            mNotificationManager.deleteNotificationChannel(s.id)
        }
        if (nNotifyChannel != null) {
            mNotificationManager.createNotificationChannel(nNotifyChannel)
        }

        //Munculkan notifikasi
        mNotificationManager.notify(NotifyId, mBuilder.build())
    }

    fun rand(start : Int, end: Int) = Random(System.nanoTime()).nextInt(end - start + 1) + start

    fun getRandomProduct() : classProduk{
        var products = singletonData.outfitList
        var jumlahProduct = products.size
        var indexRandom = rand(0, jumlahProduct - 1)
        return products[indexRandom]
    }
}