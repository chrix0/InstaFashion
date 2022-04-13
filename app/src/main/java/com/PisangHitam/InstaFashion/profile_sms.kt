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
    /*var firstName = ContactsContract.PhoneLookup.DISPLAY_NAME_PRIMARY
    var lastName = ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME
    var number1 = ContactsContract.CommonDataKinds.Phone.NUMBER
    var numberDevice1 = ContactsContract.CommonDataKinds.Phone.TYPE
    var email = ContactsContract.RawContactsEntity.CONTACT_ID
    var emailStyle = ContactsContract.CommonDataKinds.Email.TYPE
    var picture = ContactsContract.Contacts.PHOTO_URI
    var myListContact : MutableList<classContact> = mutableListOf()*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_sms)
        LoaderManager.getInstance(this).initLoader(1,null,this)
    }
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<MutableList<classContact>> {
        //var myContactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        //var myProjection = arrayOf(firstName,lastName,number1,
        //    numberDevice1, email,emailStyle,picture)
        //return CursorLoader(this,myContactUri,myProjection,null,null,"$number1 ASC")
        return FetchContact(this)
    }
    @SuppressLint("Range")
    override fun onLoadFinished(loader: Loader<MutableList<classContact>>, data: MutableList<classContact>) {
        /*myListContact.clear()
        if(data!=null){
            data.moveToFirst()
            while(!data.isAfterLast){
                myListContact.add(
                    classContact(
                        data.getString(data.getColumnIndex(firstName)) ?:"", "",
                        data.getString(data.getColumnIndex(number1))?:"",
                        typeNumber(data.getString(data.getColumnIndex(numberDevice1)))?:"",
                        getNameEmailDetails(data.getString(data.getColumnIndex(email)))[0]?:"",
                        typeEmail(getNameEmailDetails(data.getString(data.getColumnIndex(email)))[1])?:"",
                        data.getString(data.getColumnIndex(picture)) ?: ""
                    )
                )
                data.moveToNext()
            }

        }*/
        Log.e("veh",data.toString())
        var contactAdapter = recycler_contact_adapter(data)
        contactRecyView.apply{
            layoutManager = LinearLayoutManager(this@profile_sms)
            adapter = contactAdapter
        }
    }

    override fun onLoaderReset(loader: Loader<MutableList<classContact>>) {
        contactRecyView.adapter?.notifyDataSetChanged()
    }
    fun typeNumber(type : String) : String{
        var type1 = type.toInt()
        return when(type1) {
            1 -> "Mobile"
            2 -> "Home"
            3 -> "Work"
            4 -> "Work Fax"
            5 -> "Home Fax"
            6 -> "Pager"
            7 -> "Other"
            8 -> "Custom"
            9 -> "Callback"
            else -> "Other"
        }
    }
    fun typeEmail(type : String) : String{
        if(type == "x"){
            return "-"
        }
        var type1 = type.toInt()
        return when(type1) {
            1 -> "Home"
            2 -> "Work"
            3 -> "Other"
            4 -> "Mobile"
            else -> "Other"
        }
    }
    fun getNameEmailDetails(id : String): MutableList<String> {
        var result : MutableList<String> = mutableListOf()
        val cr: ContentResolver = getContentResolver()
        val PROJECTION = arrayOf(
            ContactsContract.RawContacts.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_ID,
            ContactsContract.CommonDataKinds.Email.DATA,
            ContactsContract.CommonDataKinds.Email.TYPE,
            ContactsContract.CommonDataKinds.Photo.CONTACT_ID
        )
        val cur = cr.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            PROJECTION,
            null,
            null,
            null
        )
        if (cur!!.moveToFirst()) {
            do {
                // names comes in hand sometimes
                val idC = cur.getString(0)
                val emlAddr = cur.getString(3)
                result.clear()
                // keep unique only
                if (id == idC) {
                    Log.i("VehSl2",cur.getString(4))
                    result.add(emlAddr)
                    result.add(cur.getString(4))
                    return result
                }
            } while (cur.moveToNext())
        }
        cur.close()
        result.add("-")
        result.add("x")
        return result
    }
}