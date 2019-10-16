package com.ganesh.weatherapp

import android.app.Application
import com.ganesh.di.component.AppComponent
import com.ganesh.di.component.DaggerAppComponent

class CurrencyApplication : Application() {


    fun initComponet(): AppComponent {
        return DaggerAppComponent.create()
    }

}