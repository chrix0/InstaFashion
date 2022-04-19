package com.PisangHitam.InstaFashion.Tester

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.PisangHitam.InstaFashion.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PersonalDataTester {

    val db = singletonData.getRoomHelper(ApplicationProvider.getApplicationContext())

    @Rule @JvmField
    var activityTestRule = object : ActivityTestRule<Personaldata>(Personaldata::class.java){
        override fun getActivityIntent(): Intent {
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
            return Intent(targetContext, Personaldata::class.java).apply {
                putExtra(dummy_for_test, db.daoAccount().getAccUserCheck("TEST")[0])
            }
        }
    }

    @Test
    fun clickSaveButton(){
        onView(withId(R.id.name)).perform(ViewActions.clearText())
        onView(withId(R.id.name)).perform(ViewActions.typeText("Test"))
        onView(withId(R.id.email)).perform(ViewActions.clearText())
        onView(withId(R.id.email)).perform(ViewActions.typeText("Test@gmail.com"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.address)).perform(ViewActions.clearText())
        onView(withId(R.id.address)).perform(ViewActions.typeText("Test test"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.Province)).perform(ViewActions.clearText())
        onView(withId(R.id.Province)).perform(ViewActions.typeText("Test test"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.City)).perform(ViewActions.clearText())
        onView(withId(R.id.City)).perform(ViewActions.typeText("Medan"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.psCode)).perform(ViewActions.clearText())
        onView(withId(R.id.psCode)).perform(ViewActions.typeText("12345"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.Pnumber)).perform(ViewActions.clearText())
        onView(withId(R.id.Pnumber)).perform(ViewActions.typeText("12345"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.save)).perform(ViewActions.click())
    }
}