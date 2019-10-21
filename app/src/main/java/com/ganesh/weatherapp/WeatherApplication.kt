package com.ganesh.weatherapp

import android.app.Application
import com.ganesh.weatherapp.di.component.AppComponent
import com.ganesh.weatherapp.di.component.DaggerAppComponent


class WeatherApplication : Application() {

    fun initComponet(): AppComponent {
        return DaggerAppComponent.create()
    }

}