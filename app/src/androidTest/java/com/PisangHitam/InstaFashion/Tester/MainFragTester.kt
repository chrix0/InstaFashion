package com.PisangHitam.InstaFashion.Tester

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.PisangHitam.InstaFashion.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragTester {
    @Rule @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup(){
        Intents.init()
    }

    @After
    fun tearDown(){
        //untuk menghemat memori
        Intents.release()
    }

    @Test
    fun checkShopFrag(){
        val bundle = Bundle()
        launchFragmentInContainer(bundle, R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            Shop_Main_Frag()
        }
        onView(withId(R.id.fragmentShop)).check(matches(isDisplayed()));
    }

    @Test
    fun checkWishFrag(){
        val bundle = Bundle()
        launchFragmentInContainer(bundle, R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            Wishlist_Main_Frag()
        }
        onView(withId(R.id.fragmentWish)).check(matches(isDisplayed()));
    }

    @Test
    fun checkProfileFrag(){
        val bundle = Bundle()
        var db = singletonData.getRoomHelper(ApplicationProvider.getApplicationContext())
        bundle.putParcelable(dummy_for_test, db.daoAccount().getAccUserCheck("TEST")[0])
        launchFragmentInContainer(bundle, R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            Profile_Main_Frag()
        }
        onView(withId(R.id.fragmentProfile)).check(matches(isDisplayed()));
    }
}