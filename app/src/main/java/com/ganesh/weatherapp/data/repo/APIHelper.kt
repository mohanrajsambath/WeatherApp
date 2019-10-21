package com.ganesh.weatherapp.data.repo

import com.ganesh.weatherapp.data.model.CityWeatherModel
import io.reactivex.Single

interface APIHelper {


    fun searchWeather(query: String, key: String): Single<CityWeatherModel>


}