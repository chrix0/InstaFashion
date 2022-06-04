package com.PisangHitam.InstaFashion

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentProviderOperation
import android.content.Intent
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
import android.provider.ContactsContract
import android.widget.Toast

import androidx.core.content.ContextCompat
import com.fondesa.kpermissions.allDenied
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.allPermanentlyDenied
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import org.jetbrains.annotations.Contract


class profile_sms : AppCompatActivity(), LoaderManager.LoaderCallbacks<MutableList<classContact>>{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_sms)
        LoaderManager.getInstance(this).initLoader(1,null,this)

        val actionbar = supportActionBar
        actionbar!!.title = "Your contacts"
        actionbar.setDisplayHomeAsUpEnabled(true)

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
                permissionsBuilder(Manifest.permission.WRITE_CONTACTS).build().send() {
                        result ->
                    if (result.allDenied()){
                        Toast.makeText(this, "Permission denied. Please grant the permission to add contacts.", Toast.LENGTH_SHORT).show()
                    }
                    else if (result.allGranted()){
                        Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show()
                        var operations = ArrayList<ContentProviderOperation>()

                        operations.add(ContentProviderOperation.newInsert(
                            ContactsContract.RawContacts.CONTENT_URI)
                            .withValue(
                                ContactsContract.RawContacts.ACCOUNT_TYPE, null
                            )
                            .withValue(
                                ContactsContract.RawContacts.ACCOUNT_NAME, null
                            )
                            .build())

                        //Insert name
                        operations.add(
                            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                    //Beri MIMETYPE StructuredName
                                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name.text.toString())
                                .build()
                        )

                        //Insert number
                        operations.add(
                            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                    //Beri MIMETYPE Phone
                                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number.text.toString())
                                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                                .build()
                        )

                        contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)
                        Toast.makeText(this, "Contact successfully added", Toast.LENGTH_SHORT).show()
                        creator.cancel()
                        //Restart loader ketika kontak sudah ditambah
                        LoaderManager.getInstance(this).restartLoader(1, null, this)
                    }
                }
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
        var contactAdapter = recycler_contact_adapter(this, data){
            var layout = layoutInflater.inflate(R.layout.dialog_profile_sms_contact,null)
            var dialog = AlertDialog.Builder(this).apply{
                setView(layout)
                setTitle("Edit Contact")
            }
            var creator = dialog.create()
            var name = layout.findViewById<EditText>(R.id.tbContactName)
            var number = layout.findViewById<EditText>(R.id.tbContactNumber)
            var done = layout.findViewById<Button>(R.id.saveContact)
            var cancel = layout.findViewById<Button>(R.id.cancelContact)

            done.text = "Done"

            var target = it
            name.setText("${target.firstName} ${target.lastName}")
            number.setText("${target.number1}")

            done.setOnClickListener {
                var operations = ArrayList<ContentProviderOperation>()
                var selection = ContactsContract.Data.RAW_CONTACT_ID + "=? AND " +
                        ContactsContract.Data.MIMETYPE + "=?"

                //Update name
                operations.add(
                    ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(selection, arrayOf(target.id.toString(), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE))
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name.text.toString())
                        .build()
                )

                //Update number
                operations.add(
                    ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(selection, arrayOf(target.id.toString(), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE))
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number.text.toString())
                        .build()
                )



                contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)
                Toast.makeText(this, "Contact successfully edited", Toast.LENGTH_SHORT).show()
                creator.cancel()
                //Restart loader ketika kontak sudah ditambah
                LoaderManager.getInstance(this).restartLoader(1, null, this)
            }

            cancel.setOnClickListener{
                creator.cancel()
            }

            creator.show()
        }
        contactRecyView.apply{
            layoutManager = LinearLayoutManager(this@profile_sms)
            adapter = contactAdapter
        }
    }
    override fun onLoaderReset(loader: Loader<MutableList<classContact>>) {
        contactRecyView.adapter?.notifyDataSetChanged()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}