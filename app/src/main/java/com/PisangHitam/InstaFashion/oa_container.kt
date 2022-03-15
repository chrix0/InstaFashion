package com.PisangHitam.InstaFashion

import android.app.Fragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class oa_container : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oa_container)

        var actionbar = supportActionBar
        actionbar!!.title = "Outfit Analyzer"

        val fragManager = supportFragmentManager
        val fragmentTransaction = fragManager.beginTransaction()

        val frag = oa_pic1_frag()
        fragmentTransaction.add(R.id.container, frag, "Step 1")
        fragmentTransaction.commit()
    }
}