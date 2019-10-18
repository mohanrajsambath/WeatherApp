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


    val weatherResponseLiveData = MutableLiveData<CityWeatherModel>()


    fun getWeather(cityName: String) {

        showLoadingLiveData.value = true

        launch {

            val result = withContext(Dispatchers.IO) {
                galRepository.searchWeather(cityName)
            }

            showLoadingLiveData.value = false

            resultHandler(result)


        }
    }


    fun resultHandler(result: UseCaseResult<CityWeatherModel>) {

        when (result) {
            is UseCaseResult.Success -> {
                weatherResponseLiveData.value = result.data
            }
            is UseCaseResult.Error -> {
                errorResponseLiveData.value = result.exception.message
            }
        }
    }




}


