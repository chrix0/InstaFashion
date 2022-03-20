package com.PisangHitam.InstaFashion

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.PisangHitam.InstaFashion.LoginActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

@Suppress("Deprecation")
class splash_screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        singletonData.nw_filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)

        //Dengan library anko
        doAsync{
            registerReceiver(singletonData.nw_receiver, singletonData.nw_filter)
            for(i in 0..1500){
                uiThread {
                    progBar.progress += 1
                }
                Thread.sleep(1)
                 //Beri jeda 1ms untuk setiap iterasi
            }

            //Thread.sleep(2000)
            uiThread {
                if (singletonData.nw_receiver.connected(this@splash_screen, true)) {
                    var intent = Intent(applicationContext, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }

            //Ada kemungkinan progress bar stuck di max
            if (progBar.progress == progBar.max){
                recreate()
            }

        }

        /*
        Handler().postDelayed(Runnable{
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
         */
    }

    override fun onBackPressed() {

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(singletonData.nw_receiver)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(singletonData.nw_receiver, singletonData.nw_filter)
    }
}