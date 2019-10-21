package com.ganesh.weatherapp.data.remote

import com.ganesh.weatherapp.data.model.CityWeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("weather")
    fun getWeatherAsync(@Query("q") query: String, @Query("appid") appid: String): Single<CityWeatherModel>

}