package com.ganesh.weatherapp


import android.os.Bundle
import com.ganesh.di.DaggerViewModelFactory
import com.ganesh.weatherapp.view.BaseActivity
import com.ganesh.weatherapp.view_model.WeatherViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {


    private val cityName = "Berlin"


    @Inject
    lateinit var viewModel: WeatherViewModel

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        collectWeatherFromServer()
    }

    override fun initDI() {
        (application as CurrencyApplication).initComponet().inject(this)
    }


    private fun collectWeatherFromServer() {
        viewModel.getWeather(cityName)
    }


}
