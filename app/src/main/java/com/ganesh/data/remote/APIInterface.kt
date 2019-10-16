package com.tamil.galassignment.data.remote

import com.tamil.galassignment.data.model.CityWeatherModel

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("weather")
    fun getWeatherAsync(@Query("q") query: String, @Query("appid") appid: String): Deferred<CityWeatherModel>

}