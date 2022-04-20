package com.PisangHitam.InstaFashion.Tester

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.PisangHitam.InstaFashion.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AnalyzerTester {
    @Before
    fun setup(){
        Intents.init()
    }

    @Test
    fun openCameraIntent(){
        val bundle = Bundle()
//        var scenario = launchFragmentInContainer<Shop_Main_Frag>()
        launchFragmentInContainer(bundle, R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            oa_pic1_frag()
        }
        Espresso.onView(ViewMatchers.withId(R.id.openCamera)).perform(ViewActions.click())
        intending(toPackage("com.android.contacts"))
    }
    @Test
    fun openGalleryIntent(){
        val bundle = Bundle()
//        var scenario = launchFragmentInContainer<Shop_Main_Frag>()
        launchFragmentInContainer(bundle, R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            oa_pic1_frag()
        }
        Espresso.onView(ViewMatchers.withId(R.id.openGallery)).perform(ViewActions.click())
        intending(toPackage("com.android.gallery"))
    }


    @After
    fun tearDown(){
        //untuk menghemat memori
        Intents.release()
    }

}