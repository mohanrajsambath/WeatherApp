package com.ganesh.weatherapp.view_model


import androidx.lifecycle.MutableLiveData
import com.ganesh.weatherapp.data.model.ErrorMessage
import com.ganesh.weatherapp.BuildConfig
import com.ganesh.weatherapp.binding.WeatherData
import com.ganesh.weatherapp.data.model.CityWeatherModel
import com.ganesh.weatherapp.data.repo.AppApiHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


open class WeatherViewModel @Inject constructor() :
    BaseViewModel() {


    @Inject
    lateinit var apiRepositoy: AppApiHelper

    var weatherResponseLiveData = MutableLiveData<CityWeatherModel>()


    fun getWeather(cityName: String) {
        showLoadingLiveData.value = true
        getWeatherFromServer(cityName)
    }

    private fun getWeatherFromServer(cityName: String) {


        disposable.add(
            apiRepositoy.searchWeather(cityName, BuildConfig.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CityWeatherModel>() {

                    override fun onSuccess(value: CityWeatherModel?) {
                        successMessage(value)
                    }

                    override fun onError(e: Throwable?) {
                        onErrorMessage(e)
                    }
                })
        )


    }

    fun successMessage(value: CityWeatherModel?) {
        weatherResponseLiveData.value = value
        showLoadingLiveData.value = false
    }

    fun onErrorMessage(e: Throwable?) {
        val modifiedMessage = ErrorMessage.getMessage(e!!.message!!)
        errorResponseLiveData.value = modifiedMessage
        showLoadingLiveData.value = false
    }


}


