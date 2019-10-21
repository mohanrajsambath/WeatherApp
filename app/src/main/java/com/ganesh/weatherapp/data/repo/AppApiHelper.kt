package com.ganesh.weatherapp.data.repo


import com.ganesh.weatherapp.data.model.CityWeatherModel
import com.ganesh.weatherapp.data.remote.APIInterface
import io.reactivex.Single
import javax.inject.Inject

class AppApiHelper @Inject constructor(var api: APIInterface) : APIHelper {


    override fun searchWeather(query: String, key: String): Single<CityWeatherModel> {
        return api.getWeatherAsync(query, key)
    }


}