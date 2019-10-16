package com.ganesh.weatherapp.view_model


import androidx.lifecycle.MutableLiveData
import com.ganesh.data.model.UseCaseResult
import com.tamil.galassignment.data.model.CityWeatherModel
import com.tamil.galassignment.data.repo.APIRepoInterface

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class WeatherViewModel @Inject constructor(var galRepository: APIRepoInterface) :
    BaseViewModel() {


    val weatherResponse = MutableLiveData<CityWeatherModel>()


    fun getWeather(cityName: String) {

        showLoading.value = true

        launch {

            val result = withContext(Dispatchers.IO) {
                galRepository.searchWeather(cityName)
            }

            showLoading.value = false

            hadleResult(result)


        }
    }


    fun hadleResult(result: UseCaseResult<CityWeatherModel>) {

        when (result) {
            is UseCaseResult.Success -> {
                weatherResponse.value = result.data
            }
            is UseCaseResult.Error -> {
                errorResponse.value = result.exception.message
            }
        }
    }




}


