package com.ganesh.weatherapp


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WeatherActvityTest {


    @get:Rule
    var activityRule = ActivityTestRule(WeatherActivity::class.java)


    @Test
    fun failureMessageTest() {

        activityRule.activity.setErorrMessage("Invalid City Name")

        onView(withId(R.id.errorContent)).check(matches(withText("Invalid City Name")))

    }

    @Test
    fun successMessageTest() {

        val responseData = UITestData().getData()

        activityRule.activity.setWeatherDataToViews(responseData)

        onView(withId(R.id.cityName)).check(matches(withText("")))

    }





}