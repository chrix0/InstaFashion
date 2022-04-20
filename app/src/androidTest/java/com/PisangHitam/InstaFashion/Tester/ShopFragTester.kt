package com.PisangHitam.InstaFashion.Tester

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.PisangHitam.InstaFashion.*
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.android.synthetic.main.activity_main.*
import org.hamcrest.Matchers.endsWith
import org.junit.After
import org.junit.Before
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class ShopFragTester {
    @Before
    fun setup(){
        Intents.init()
    }

    @Test
    fun moreButtonIntent(){
        val bundle = Bundle()
//        var scenario = launchFragmentInContainer<Shop_Main_Frag>()
        launchFragmentInContainer(bundle, R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            Shop_Main_Frag()
        }
        onView(withId(R.id.more)).perform(ViewActions.click())
        intended(hasComponent(shop_productList::class.java.name))
    }
    @Test
    fun floatingButtonIntent(){
        val bundle = Bundle()
//        var scenario = launchFragmentInContainer<Shop_Main_Frag>()
        launchFragmentInContainer(bundle, R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            Shop_Main_Frag()
        }
        onView(withId(R.id.speedDial)).perform(ViewActions.click())
        onView(withText("Basket")).perform(ViewActions.click())

        intended(hasComponent(shop_basket::class.java.name))
    }
    @After
    fun tearDown(){
        //untuk menghemat memori
        Intents.release()
    }
}