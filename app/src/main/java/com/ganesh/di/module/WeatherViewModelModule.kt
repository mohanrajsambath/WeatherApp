package com.ganesh.di.module

import androidx.lifecycle.ViewModel

import com.ganesh.di.ViewModelKey
import com.ganesh.weatherapp.view_model.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class WeatherViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindMyViewModel(myViewModel: WeatherViewModel): ViewModel
}