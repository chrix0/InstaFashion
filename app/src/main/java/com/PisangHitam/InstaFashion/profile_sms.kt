package com.PisangHitam.InstaFashion

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_profile_sms.*
import kotlinx.android.synthetic.main.layout_contact_recy_view.view.*

class profile_sms : AppCompatActivity(), LoaderManager.LoaderCallbacks<MutableList<classContact>> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_sms)
        LoaderManager.getInstance(this).initLoader(1,null,this)
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