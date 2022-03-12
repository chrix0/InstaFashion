package com.PisangHitam.InstaFashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.fragmentContainerView)
        navBottom.setupWithNavController(navController)

        navController.navigate(R.id.shop_Main_Frag)

        navBottom.setOnItemSelectedListener {
            when(it.itemId){
                R.id.shop_Main_Frag -> {
                    navController.navigate(R.id.shop_Main_Frag)
                    true
                }
                R.id.wishlist_Main_Frag -> {
                    navController.navigate(R.id.wishlist_Main_Frag)
                    true
                }
                R.id.profile_Main_Frag -> {
                    navController.navigate(R.id.profile_Main_Frag)
                    true
                }
                else ->
                    false
            }
        }

        var intentData = intent
        when(intentData.hasExtra(RETURN_LAST_TAB)){
            (intentData.getStringExtra(RETURN_LAST_TAB).equals("SHOP")) -> {
                navBottom.selectedItemId = R.id.shop_Main_Frag
            }
            (intentData.getStringExtra(RETURN_LAST_TAB).equals("ADOPT")) -> {
                navBottom.selectedItemId = R.id.wishlist_Main_Frag
            }
            (intentData.getStringExtra(RETURN_LAST_TAB).equals("BREED")) -> {
                navBottom.selectedItemId = R.id.profile_Main_Frag
            }
        }
    }
}