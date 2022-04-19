package com.PisangHitam.InstaFashion.Tester

import android.content.Intent
import android.view.View
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.PisangHitam.InstaFashion.R
import com.PisangHitam.InstaFashion.register
import org.hamcrest.core.AllOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class RegisterTester {
    @Rule @JvmField
    var activityTestRule = ActivityTestRule(register::class.java)
    var intentTestRule = IntentsTestRule(register::class.java)

    @Test
    fun clickRegisterButton(){
        onView(withId(R.id.fullname)).perform(ViewActions.typeText("Test test"))
        onView(withId(R.id.email)).perform(ViewActions.typeText("Test@gmail.com"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.username)).perform(ViewActions.typeText("username"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.password)).perform(ViewActions.typeText("password"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.signup)).perform(ViewActions.click())
    }
    @Before
    fun setup(){
        init()
    }
    @Test
    fun clickTextView(){

        onView(withId(R.id.text)).perform(ViewActions.click())
        hasComponent("com.PisangHitam.InstaFashion.LoginActivity")

    }
    @After
    fun tearDown(){
        release()
    }
}