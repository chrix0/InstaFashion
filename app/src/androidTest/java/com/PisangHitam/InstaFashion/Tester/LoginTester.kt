package com.PisangHitam.InstaFashion.Tester

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.PisangHitam.InstaFashion.LoginActivity
import com.PisangHitam.InstaFashion.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTester {
    @Rule @JvmField
    var activityTestRule = ActivityTestRule(LoginActivity::class.java)
    @Test
    fun clickLoginButton(){
        Espresso.onView(ViewMatchers.withId(R.id.usernameLogin)).perform(ViewActions.typeText("TEST"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.passwordLogin)).perform(ViewActions.typeText("TEST"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.keepLoggedIn)).perform(ViewActions.click())
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.button2)).perform(ViewActions.click())
    }
    @Before
    fun setup(){
        Intents.init()
    }
    @Test
    fun clickTextView(){

        Espresso.onView(ViewMatchers.withId(R.id.toSignUp)).perform(ViewActions.click())
        IntentMatchers.hasComponent("com.PisangHitam.InstaFashion.register")

    }
    @After
    fun tearDown(){
        Intents.release()
    }
}