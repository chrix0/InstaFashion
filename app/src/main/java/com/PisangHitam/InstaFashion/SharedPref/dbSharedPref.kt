package com.PisangHitam.InstaFashion.SharedPref

import android.content.Context
import android.content.SharedPreferences
import com.PisangHitam.InstaFashion.Room.roomHelper
import com.PisangHitam.InstaFashion.singletonData
import com.google.gson.Gson

class dbSharedPref(var context: Context) {
    val DB = "DB_INSTAFASHION"

    private var myPreferences : SharedPreferences = context.getSharedPreferences("sharedprefDB", Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operator :(SharedPreferences.Editor) -> Unit){
        val editMe = edit() //method edit berasal dari SharedPreferences
        operator(editMe) //operator ini seperti putString, putInteger, putBoolean
        editMe.apply()
    }

    var db : roomHelper
        get(){
            return singletonData.getRoomHelper(context)
        } //Jika tidak ada, index default -1
        set(value) {}

    fun resetKept(){
        myPreferences.editMe{
            it.clear()
        }
    }
}