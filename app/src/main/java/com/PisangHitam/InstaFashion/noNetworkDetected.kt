package com.PisangHitam.InstaFashion

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_no_network_detected.*
import org.jetbrains.anko.companionDeviceManager
import kotlin.system.exitProcess

class noNetworkDetected : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_network_detected)

        retryConnect.setOnClickListener{
            if (singletonData.nw_receiver.connected(this, false)){
                Toast.makeText(applicationContext, "Connected.", Toast.LENGTH_SHORT).show()
                var intent = Intent(applicationContext, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, "Failed to connect.", Toast.LENGTH_SHORT).show()
            }
        }

        exitApp.setOnClickListener {
            finishAffinity()
            System.exit(0)
        }



    }
}