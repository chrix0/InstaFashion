package com.PisangHitam.InstaFashion.SharedPref

import android.content.Context
import android.content.SharedPreferences

class loginSharedPref(context: Context) {
    val ID_USER = "USER"

    private var myPreferences : SharedPreferences = context.getSharedPreferences("sharedprefLoginInfo", Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operator :(SharedPreferences.Editor) -> Unit){
        val editMe = edit() //method edit berasal dari SharedPreferences
        operator(editMe) //operator ini seperti putString, putInteger, putBoolean
        editMe.apply()
    }

    var idUser : Int?
        get() = myPreferences.getInt(ID_USER, -1) //Jika tidak ada, index default -1
        set(value){
            myPreferences.editMe{
                it.putInt(ID_USER, value!!)
            }
        }

    fun resetKept(){
        myPreferences.editMe{
            it.clear()
        }
    }
}