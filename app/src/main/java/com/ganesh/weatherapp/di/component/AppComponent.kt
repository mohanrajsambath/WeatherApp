package com.ganesh.weatherapp.di.component

import com.ganesh.weatherapp.WeatherActivity
import com.ganesh.weatherapp.di.module.NetworkDIModule
import com.ganesh.weatherapp.di.module.WeatherViewModelModule
import com.ganesh.weatherapp.data.repo.AppApiHelper
import com.ganesh.weatherapp.view_model.WeatherViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkDIModule::class, WeatherViewModelModule::class])
interface AppComponent {

    fun inject(activity: WeatherActivity)

}