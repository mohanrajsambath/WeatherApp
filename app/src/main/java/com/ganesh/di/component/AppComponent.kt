package com.ganesh.di.component

import com.ganesh.di.module.NetworkDIModule
import com.ganesh.di.module.WeatherViewModelModule
import com.ganesh.weatherapp.view.WeatherActivity
import dagger.Component
import org.jetbrains.annotations.TestOnly
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkDIModule::class, WeatherViewModelModule::class])
interface AppComponent {

    fun inject(activity: WeatherActivity)

}