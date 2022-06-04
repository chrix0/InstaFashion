package com.PisangHitam.InstaFashion

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_register.view.*
import kotlinx.android.synthetic.main.layout_contact_recy_view.view.*
import kotlinx.android.synthetic.main.layout_contact_recy_view.view.email

class recycler_contact_adapter(var acontext : Context, private val contact: List<classContact>,
var editor : (classContact) -> Unit) :RecyclerView.Adapter<myHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        return myHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_contact_recy_view,parent,false)
        )
    }

    override fun onBindViewHolder(holder: myHolder, position: Int) {
        holder.bindContact(contact[position])
        holder.item.setOnClickListener {
            Toast.makeText(acontext, contact[position].id.toString(), Toast.LENGTH_SHORT).show()
            editor(contact[position])
        }
    }

    override fun getItemCount(): Int = contact.size
}

class myHolder(view : View) : RecyclerView.ViewHolder(view){
    var firstName = view.firstName
    var lastName = view.lastName
    var number1 = view.number1
    var numberDevice1 = view.numberDevice1
    var email = view.email
    var emailStyle = view.emailStyle
    var picture = view.picture
    var item = view.contactItem

    fun bindContact(tmp : classContact){
        firstName.text = "${tmp.firstName}"
        lastName.text = "${tmp.lastName}"
        number1.text = "${tmp.number1}"
        numberDevice1.text = "${tmp.numberDevice1}"
        email.text = "${tmp.email}"
        emailStyle.text = "${tmp.emailStyle}"
        picture.setImageURI((tmp.picture).toUri())
    }

}
