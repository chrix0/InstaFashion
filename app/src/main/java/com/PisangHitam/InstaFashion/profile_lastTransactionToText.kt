package com.PisangHitam.InstaFashion

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_profile_last_transaction_to_text.*
import java.io.*
import java.io.OutputStreamWriter

import java.io.FileOutputStream
import java.io.File

class profile_lastTransactionToText : AppCompatActivity() {

    private var inputData = mutableListOf<String>()
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_last_transaction_to_text)

        val actionbar = supportActionBar
        actionbar!!.title = "Last Transaction to Text"
        actionbar.setDisplayHomeAsUpEnabled(true)
        isExternalStorageReadable()

        var db = singletonData.getRoomHelper(this)
        var user = singletonData.getCurUserObj(this)
        var all = user?.transactionHistory
        var last = all?.get(all.size - 1)

        inputData = mutableListOf(
            "Transaction ID : ${last?.id}",
            "Username : ${user?.userName}",
            "Time of purchase : ${last?.datePurchase}",
            "Payment method : ${last?.method}",
            "Shipping address : ${singletonData.formatAlamat(last!!.address)}",
            "Phone Number : ${last?.phoneNumber}",
            "Subtotal : ${last?.subTotal}",
            "Shipping cost : ${last?.shippingCost}",

            "Total : ${last?.subTotal + last?.shippingCost}"
        )

        //Pengecekan jumlah space yang dibutuhkan
        sizeEstimation.text = "File size estimation : ${fileSizeInKb()} KB"

        internal.setOnClickListener{
            //READ - INTERNAL
            //fileList() merupakan method built-in

            var existed : String = ""
            var file : File? = null
            try{
                file = File(filesDir, "${user?.userName}LastTransaction.txt")
                if(!file.exists()){
                    file.createNewFile()
                }
                file.readLines().forEach {
                    existed += "$it \n"
                }
            }
            catch (e: IOException){
                Toast.makeText(this, "Unable to read the file.", Toast.LENGTH_SHORT)
            }

            if(existed.isNotEmpty()){
                var dialog = AlertDialog.Builder(this)
                    .setTitle("File contains details of previous transaction")
                    .setMessage("$existed \n\nDo you want to overwrite the file?")
                    .setPositiveButton("Yes") { _, i ->
                        saveInternal(file!!)
                    }
                    .setNegativeButton("No") { _, i ->
                    }
                dialog.show()
            }
            else{
                saveInternal(file!!)
            }
        }

        external.setOnClickListener {
            //Izin akses external storage
            if(isExternalStorageReadable()){
                var exDir = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.toURI())
                if(!exDir.exists()){
                    exDir.mkdir()
                }

                //READ - EXTERNAL
                var existed : String = ""
                var file = File(exDir, "external${user?.userName}LastTransaction.txt")
                if(!file.exists()){
                    file.createNewFile()
                }
                file.readLines().forEach {
                    existed += "$it\n"
                };

                if(existed.isNotEmpty()){
                    var dialog = AlertDialog.Builder(this)
                        .setTitle("File contains details of previous transaction")
                        .setMessage("$existed\n\nDo you want to overwrite the file?")
                        .setPositiveButton("Yes") { _, i ->
                            saveExternal(file)
                        }
                        .setNegativeButton("No") { _, i ->
                        }
                    dialog.show()
                }
                else{
                    saveExternal(file)
                }
            }
        }
    }

    fun saveInternal(target: File){
        //SAVE - INTERNAL
        var file =  target

        for (i in 0 until inputData.size){
            if(i == 0){
                file.writeText("${inputData[i]}, \n\r")
            }
            else
                file.appendText("${inputData[i]}, \n\r")
        }

        Snackbar.make(parentLayout, "Success! File is saved at ${file.absolutePath}", Snackbar.LENGTH_INDEFINITE)
            .setAction("OK") { }
            .show()
    }

    fun saveExternal(target: File){
        //SAVE - EXTERNAL
        var file = target
        for (i in 0 until inputData.size){
            if(i == 0){
                file.writeText("${inputData[i]}, \n\r")
            }
            else
                file.appendText("${inputData[i]}, \n\r")
        }

        Snackbar.make(parentLayout, "Success! File is saved at ${file.absolutePath}", Snackbar.LENGTH_INDEFINITE)
            .setAction("OK") { }
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isExternalStorageReadable():Boolean{
        //Munculkan permission dialog kalau permission write external belum ada
        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ){
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 123)
        }

        //Cek apakah external storage tersedia dengan getExternalStorageState()
        var state = Environment.getExternalStorageState()

        //Ini berarti kalau statenya MEDIA_MOUNTED atau MEDIA_MOUNTED_READ_ONLY itu diperbolehkan
        if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state){
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            123 -> {
                //permissions diambil dari parameter pertama requestPermissions
                //gratResults merupakan array yang isinya hasil code yang diterima dari permission tsb: PERMISSION_GRANTED / PERMISSION_DENIED

                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE){
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Permission denied. Unable to continue.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun fileSizeInKb(): Float {
        var byte = 0F
        inputData.forEach{
            byte += it.length
        }
        return byte / 1024F
    }

}