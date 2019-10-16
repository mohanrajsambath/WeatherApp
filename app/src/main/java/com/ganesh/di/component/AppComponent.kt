package com.ganesh.di.component

import com.ganesh.di.module.NetworkDIModule
import com.ganesh.di.module.WeatherViewModelModule
import com.ganesh.weatherapp.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkDIModule::class, WeatherViewModelModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

}