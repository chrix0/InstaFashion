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

@Suppress("Deprecation")
class service_inetCheck : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        var intent = Intent(CHECK_INTERNET)
        intent.putExtra(INTERNET_CONNECTED, isConnected())
        sendBroadcast(intent)
    }

    private fun isConnected() : Boolean{
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
        return false
    }

    override fun onDestroy(){
        super.onDestroy()
        doAsync {
            if(!isConnected()){
                uiThread {
                    Toast.makeText(applicationContext, "Connection timeout! Please check your internet connection.", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                uiThread {
                    Toast.makeText(applicationContext, "Internet found (tolong hapus toast ini nanti).", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object{
        fun enqueueWork(context: Context, intent: Intent){
            enqueueWork(context, service_inetCheck::class.java, JOB_CHECK_INTERNET, intent)
        }
    }
}