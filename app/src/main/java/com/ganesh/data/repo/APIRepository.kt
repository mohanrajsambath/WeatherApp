package com.ganesh.data.repo


import com.ganesh.data.model.UseCaseResult
import com.ganesh.weatherapp.BuildConfig
import com.tamil.galassignment.data.model.CityWeatherModel
import com.tamil.galassignment.data.remote.APIInterface
import com.tamil.galassignment.data.repo.APIRepoInterface
import java.lang.Exception
import javax.inject.Inject

open class APIRepository @Inject constructor(val apiInterface: APIInterface) : APIRepoInterface {


    override suspend fun searchWeather(query: String): UseCaseResult<CityWeatherModel> {

        return try {
            val result = apiInterface.getWeatherAsync(query, BuildConfig.API_KEY).await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }

    }


}