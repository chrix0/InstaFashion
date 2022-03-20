package com.PisangHitam.InstaFashion

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class BR_inetCheck : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        var connected = intent?.getBooleanExtra(INTERNET_CONNECTED, false)

        doAsync {
            if(!connected){
                uiThread {
                    Toast.makeText(context, "Connection timeout! Please check your internet connection.", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                uiThread {
                    Toast.makeText(context, "Internet found (tolong hapus toast ini nanti).", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}