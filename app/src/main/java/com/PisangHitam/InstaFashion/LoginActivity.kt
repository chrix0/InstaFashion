package com.PisangHitam.InstaFashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.PisangHitam.InstaFashion.SharedPref.loginSharedPref
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val EXTRA_USER = "USER"
    private val EXTRA_PASS = "PASSWORD"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var db = singletonData.getRoomHelper(applicationContext)

        button2.setOnClickListener{
            var userName = usernameLogin.text.toString()
            var password = passwordLogin.text.toString()

            if (userName.isEmpty()||password.isEmpty()) {
                Toast.makeText(this, "Please fill in username and password.", Toast.LENGTH_SHORT).show()
            }
            else {
                var found = false
//                for ((index, i ) in singletonData.accList.withIndex()) {
//                    if (userName == i.userName && password == i.password) {
//                        found = true
//                        singletonData.currentAccId = i.id
//                    }
//                }
                var getAccount = db.daoAccount().getAcc(userName, password)
                if (getAccount.isNotEmpty()) {
                    found = true
                    singletonData.currentAccId = getAccount[0].id
                }

                if(found){
                    Toast.makeText(this, "Successfully logged in.", Toast.LENGTH_SHORT).show()
                    var sharedpref = loginSharedPref(this)
                    if(keepLoggedIn.isChecked){
                        sharedpref.idUser = getAccount[0].id
                    }
                    var intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Account is not found. Please sign up.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        toSignUp.setOnClickListener{
            var intent = Intent(this, register::class.java)
            startActivity(intent)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_USER, usernameLogin.text.toString())
        outState.putString(EXTRA_PASS, passwordLogin.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        usernameLogin.setText(savedInstanceState?.getString(EXTRA_USER,""))
        passwordLogin.setText(savedInstanceState?.getString(EXTRA_PASS,""))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(singletonData.nw_receiver)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(singletonData.nw_receiver, singletonData.nw_filter)
    }
}