package com.ganesh.weatherapp

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ganesh.weatherapp.view.WeatherActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.core.AllOf.allOf


@RunWith(AndroidJUnit4::class)
class WeatherActvityTest {


    @get:Rule
    var activityRule = ActivityTestRule(WeatherActivity::class.java)



    @Test
    fun successMessageTest() {

        val responseData = UITestData().getData()

        activityRule.activity.setValueToViews(responseData)

        onView(withId(R.id.cityName)).check(matches(withText("")))

    }


    @Test
    fun failureMessageTest() {

        activityRule.activity.setErorrMessage("Invalid City Name")

        onView(withId(R.id.errorContent)).check(matches(withText("Invalid City Name")))

    }


}