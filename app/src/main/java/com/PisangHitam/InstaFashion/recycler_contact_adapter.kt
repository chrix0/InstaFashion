package com.PisangHitam.InstaFashion

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_register.view.*
import kotlinx.android.synthetic.main.layout_contact_recy_view.view.*
import kotlinx.android.synthetic.main.layout_contact_recy_view.view.email

class recycler_contact_adapter(private val contact: List<classContact>) :RecyclerView.Adapter<myHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        return myHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_contact_recy_view,parent,false)
        )
    }

    override fun onBindViewHolder(holder: myHolder, position: Int) {
        holder.bindContact(contact[position])
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
