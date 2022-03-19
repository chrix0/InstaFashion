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

    val reciever = BR_networkCheck()
    var filter = IntentFilter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(reciever, filter)

        //Dengan library anko
        doAsync{
            for(i in 0..1500){
                uiThread {
                    progBar.progress += 1
                }
                Thread.sleep(1) //Beri jeda 1ms untuk setiap iterasi
            }

            uiThread {
                if (reciever.connected(this@splash_screen)){
                    var intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
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
    /*
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(reciever)
    }
    */

    override fun onBackPressed() {

    }

    override fun onRestart() {
        super.onRestart()
    }
}