package com.PisangHitam.InstaFashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val EXTRA_USER = "USER"
    private val EXTRA_PASS = "PASSWORD"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button2.setOnClickListener{
            var userName = usernameLogin.text.toString()
            var password = passwordLogin.text.toString()

            if (userName.isEmpty()||password.isEmpty()) {
                Toast.makeText(this, "Please fill in username and password.", Toast.LENGTH_SHORT).show()
            }
            else {
                var found = false
                for ((index, i ) in singletonData.accList.withIndex()) {
                    if (userName == i.userName && password == i.password) {
                        found = true
                        singletonData.currentAccId = i.id
                    }
                }

                if(found){
                    Toast.makeText(this, "Successfully logged in.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Account is not found. Please try again.", Toast.LENGTH_SHORT).show()
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
        unregisterReceiver(singletonData.reciever)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(singletonData.reciever, singletonData.filter)
    }
}