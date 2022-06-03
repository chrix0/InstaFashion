package com.PisangHitam.InstaFashion

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_profile_sms.*
import kotlinx.android.synthetic.main.layout_contact_recy_view.view.*

import android.content.pm.PackageManager
import android.widget.Toast

import androidx.core.content.ContextCompat
import com.fondesa.kpermissions.allDenied
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.allPermanentlyDenied
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send


class profile_sms : AppCompatActivity(), LoaderManager.LoaderCallbacks<MutableList<classContact>> {
    override fun onStart() {
        super.onStart()
        //Dengan library KPermissions
        permissionsBuilder(Manifest.permission.READ_CONTACTS).build().send() {
            result ->
            if (result.allDenied()){
                Toast.makeText(this, "Permission denied. Unable to continue", Toast.LENGTH_SHORT).show()
                finish()
            }
            else if (result.allGranted()){
                Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_sms)
        LoaderManager.getInstance(this).initLoader(1,null,this)
        addContact.setOnClickListener {
            var layout = layoutInflater.inflate(R.layout.dialog_profile_sms_contact,null)
            var dialog = AlertDialog.Builder(this).apply{
                setView(layout)
                setTitle("Add new Contact")
            }
            var creator = dialog.create()
            var name = layout.findViewById<EditText>(R.id.tbContactName)
            var number = layout.findViewById<EditText>(R.id.tbContactNumber)
            var done = layout.findViewById<Button>(R.id.saveContact)
            var cancel = layout.findViewById<Button>(R.id.cancelContact)

            done.setOnClickListener {
                //ADD
            }

            cancel.setOnClickListener {
                creator.cancel()
            }

            creator.show()
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<MutableList<classContact>> {
        return FetchContact(this)
    }

    @SuppressLint("Range")
    override fun onLoadFinished(loader: Loader<MutableList<classContact>>, data: MutableList<classContact>) {
        var contactAdapter = recycler_contact_adapter(data)
        contactRecyView.apply{
            layoutManager = LinearLayoutManager(this@profile_sms)
            adapter = contactAdapter
        }
    }
    override fun onLoaderReset(loader: Loader<MutableList<classContact>>) {
        contactRecyView.adapter?.notifyDataSetChanged()
    }
}