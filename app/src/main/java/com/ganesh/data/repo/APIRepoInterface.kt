package com.tamil.galassignment.data.repo

import com.ganesh.data.model.UseCaseResult
import com.tamil.galassignment.data.model.CityWeatherModel


interface APIRepoInterface {
    suspend fun searchWeather(query: String): UseCaseResult<CityWeatherModel>
}

