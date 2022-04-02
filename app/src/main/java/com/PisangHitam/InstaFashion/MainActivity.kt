package com.PisangHitam.InstaFashion

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.util.Calendar
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val EXTRA_SEARCH = "SRC"
    private val EXTRA_LAST_SELECTED_NAV = "LAST_NAV"

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Cek koneksi internet
        singletonData.inet_receiver = BR_inetCheck()
        singletonData.inet_filter = IntentFilter(CHECK_INTERNET)
        var service = Intent(this, service_inetCheck::class.java)
        service_inetCheck.enqueueWork(this, service)

        val navController = findNavController(R.id.fragmentContainerView)
        navBottom.setupWithNavController(navController)

        navController.navigate(R.id.shop_Main_Frag)

        navBottom.setOnItemSelectedListener {
            when(it.itemId){
                R.id.shop_Main_Frag -> {
                    navController.navigate(R.id.shop_Main_Frag)
                    true
                }
                R.id.wishlist_Main_Frag -> {
                    navController.navigate(R.id.wishlist_Main_Frag)
                    true
                }
                R.id.profile_Main_Frag -> {
                    navController.navigate(R.id.profile_Main_Frag)
                    true
                }
                else ->
                    false
            }
        }

        var intentData = intent
        when(intentData.hasExtra(RETURN_LAST_TAB)){
            (intentData.getStringExtra(RETURN_LAST_TAB).equals("SHOP")) -> {
                navBottom.selectedItemId = R.id.shop_Main_Frag
            }
        }

        main_cart.setOnClickListener{
            var intent = Intent(this, shop_basket::class.java)
            startActivity(intent)
        }

//        Kondisi awal
//        singletonData.mAlarmManager = null
//        singletonData.mPendingIntent = null

        singletonData.mAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //Kalau mPendingIntent ada isi
        if(singletonData.mPendingIntent != null){
            //Batalkan alarm manager
            singletonData.mAlarmManager?.cancel(singletonData.mPendingIntent)
            //Batalkan Pending Intent
            singletonData.mPendingIntent?.cancel()
        }

        //Ambil waktu saat ini
        var alarmTimer = Calendar.getInstance()
        //Tambahkan 15 detik di alarmTimer (untuk menghemat waktu pengecekan, baris kode di bawah ini sementara tidak digunakan)
        //alarmTimer.add(Calendar.SECOND, 15)

        var sendIntent = Intent(this, BR_recNotifier::class.java)
        singletonData.mPendingIntent = PendingIntent.getBroadcast(this, 200, sendIntent, 0 )
        singletonData.mAlarmManager!!.setInexactRepeating(AlarmManager.RTC, alarmTimer.timeInMillis, AlarmManager.INTERVAL_FIFTEEN_MINUTES,singletonData.mPendingIntent)
    }

    //Gunakan nanoTime dari sistem sebagai seed untuk Random
    fun rand(start : Int, end: Int) = Random(System.nanoTime()).nextInt(end - start + 1) + start

    fun getRandomProduct() : classProduk{
        var products = singletonData.outfitList
        var jumlahProduct = products.size
        var indexRandom = rand(0, jumlahProduct - 1)
        return products[indexRandom]
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_SEARCH, main_search.text.toString())
        outState.putInt(EXTRA_LAST_SELECTED_NAV, navBottom.selectedItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        main_search.setText(savedInstanceState?.getString(EXTRA_SEARCH,""))
        navBottom.selectedItemId = savedInstanceState?.getInt(EXTRA_LAST_SELECTED_NAV, R.id.shop_Main_Frag)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(singletonData.inet_receiver, singletonData.inet_filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(singletonData.inet_receiver)
    }
}