package com.PisangHitam.InstaFashion

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.core.app.JobIntentService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception
import java.net.InetAddress


@Suppress("Deprecation")
class service_inetCheck : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        var intent = Intent(CHECK_INTERNET)
        intent.putExtra(INTERNET_CONNECTED, isConnected())
        sendBroadcast(intent)
    }

    private fun isConnected() : Boolean{
        val command = "ping -c 1 example.com"
        return try {
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

    override fun onDestroy(){
        super.onDestroy()
    }

    companion object{
        fun enqueueWork(context: Context, intent: Intent){
            enqueueWork(context, service_inetCheck::class.java, JOB_CHECK_INTERNET, intent)
        }
    }
}