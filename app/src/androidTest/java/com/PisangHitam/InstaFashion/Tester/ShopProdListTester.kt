package com.PisangHitam.InstaFashion.Tester

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.PisangHitam.InstaFashion.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShopProdListTester {
    @Before
    fun setup(){
        Intents.init()
    }

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(shop_productList::class.java)

    @Test
    fun searchTest(){
        onView(withId(R.id.search)).perform(ViewActions.typeText("Adidas"))
        onView(withId(R.id.search)).perform(pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.productList))
            .check(matches(hasDescendant(withText("Adidas Response Run Men's Running Shoes - Black - Black, UK 9"))))
    }

    @After
    fun tearDown(){
        //untuk menghemat memori
        Intents.release()
    }
}