package com.PisangHitam.InstaFashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val EXTRA_SEARCH = "SRC"
    private val EXTRA_LAST_SELECTED_NAV = "LAST_NAV"

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
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_SEARCH, main_search.text.toString())
        outState.putInt(EXTRA_LAST_SELECTED_NAV, navBottom.selectedItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        main_search.setText(savedInstanceState?.getString(EXTRA_SEARCH,""))
        navBottom.selectedItemId = savedInstanceState?.getInt(EXTRA_LAST_SELECTED_NAV, R.id.shop_Main_Frag)
    }
}