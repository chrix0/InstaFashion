package com.PisangHitam.InstaFashion.locChecker

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import android.widget.Toast
import com.PisangHitam.InstaFashion.singletonData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class js_getGeo : JobService() {

    var country : String? = null
    var countryCode : String? = null
    var timezone : String? = null
    var timezoneName : String? = null

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.w("Get Geo", "Job Service js_getGeo berjalan")
        getGeo(p0, getIP()!!)

        var calendar = Calendar.getInstance()
        val formatTanggal = SimpleDateFormat("EEEE, d MMMM yyyy, h:mm:ss a")
        Toast.makeText(this, formatTanggal.format(calendar.time), Toast.LENGTH_SHORT).show()

        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.w("Get Geo", "onStopJob Dipanggil")
        return false
    }

    fun getGeo(jobParameters: JobParameters?, ip : String){
        var client = AsyncHttpClient()
        var url = "http://ipwhois.app/json/$ip"
        var charSet = Charsets.UTF_8
        var handler = object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                var result = responseBody?.toString(charSet) ?: "Nothing..?"
                Log.w("getGeo", result)
                singletonData.jsonStringGeo = result

                var JSONObj = JSONObject(result)
                country = JSONObj!!.getString("country")
                countryCode = JSONObj!!.getString("country_code")
                timezone = JSONObj!!.getString("timezone_gmt")
                timezoneName = JSONObj!!.getString("timezone")

                singletonData.geoRes = mutableListOf(country!!, countryCode!!, timezone!!, timezoneName!!)

                jobFinished(jobParameters, false) //Tidak perlu reschedule
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.e("RIP", "GAGAL")
                jobFinished(jobParameters, true) //Reschedule
            }
        }
        client.get(url, handler)
    }

    fun getIP() : String?{
        var ip : String? = ""
        var client = AsyncHttpClient()
        var url = "http://www.ip-api.com/json"
        var charSet = Charsets.UTF_8
        var handler = object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                var result = responseBody?.toString(charSet) ?: "Nothing..?"
                Log.w("getGeo", result)

                var JSONObj = JSONObject(result)
                ip = JSONObj!!.getString("query")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.w("getIP", "Apakah koneksi internet bermasalah? $error")
            }
        }
        client.get(url, handler)

        return ip
    }

    fun getGeoFirst(ip : String){
        var client = AsyncHttpClient()
        var url = "http://ipwhois.app/json/$ip"
        var charSet = Charsets.UTF_8
        var handler = object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                var result = responseBody?.toString(charSet) ?: "Nothing..?"
                Log.w("getGeo", result)
                singletonData.jsonStringGeo = result

                var JSONObj = JSONObject(result)
                country = JSONObj!!.getString("country")
                countryCode = JSONObj!!.getString("country_code")
                timezone = JSONObj!!.getString("timezone_gmt")
                timezoneName = JSONObj!!.getString("timezone")

                singletonData.geoRes = mutableListOf(country!!, countryCode!!, timezone!!, timezoneName!!)
                hasil.addAll(singletonData.geoRes!!)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.e("RIP", "GAGAL")
            }
        }
        client.get(url, handler)
    }

    companion object{
        var hasil : MutableList<String> = mutableListOf()


    }
}