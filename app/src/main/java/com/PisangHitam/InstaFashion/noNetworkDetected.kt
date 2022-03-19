package com.PisangHitam.InstaFashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_no_network_detected.*

class noNetworkDetected : AppCompatActivity() {
    var checker = BR_networkCheck()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_network_detected)

        retryConnect.setOnClickListener{
            if (checker.connected(this)){
                Toast.makeText(applicationContext, "Connected.", Toast.LENGTH_SHORT).show()
//                var intent = Intent(applicationContext, LoginActivity::class.java)
//                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(applicationContext, "Failed to connect.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {

    }
}