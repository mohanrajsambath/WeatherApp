package com.ganesh.weatherapp.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ganesh.weatherapp.util.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

open class BaseTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    protected val cityName = "Berlin"

    protected var errorMessage = "HTTP 410"
}