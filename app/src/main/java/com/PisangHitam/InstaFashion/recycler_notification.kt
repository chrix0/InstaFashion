package com.PisangHitam.InstaFashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class recycler_notification(data : List<classRecNotif>) : RecyclerView.Adapter<recycler_notification.myHolder>() {
    private var myData = data
    class myHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById<TextView>(R.id.title)
        val text: TextView = itemView.findViewById<TextView>(R.id.text)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): recycler_notification.myHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_notification,parent,false)
        return recycler_notification.myHolder(inflate)
    }

    override fun onBindViewHolder(holder: recycler_notification.myHolder, position: Int) {
        holder.text.setText(myData.get(position).ContextText)
        holder.title.setText(myData.get(position).ContextTitle)
    }

    override fun getItemCount(): Int = myData.size


}