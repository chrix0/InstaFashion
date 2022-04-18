package com.PisangHitam.InstaFashion.Tester

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.PisangHitam.InstaFashion.LoginActivity
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTester {
    @Rule @JvmField
    var activityTestRule = ActivityTestRule(LoginActivity::class.java)
}