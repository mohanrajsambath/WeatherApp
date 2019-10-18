package com.ganesh.weatherapp.binding

import android.content.Context
import com.ganesh.weatherapp.R
import com.tamil.galassignment.data.model.CityWeatherModel


class WeatherData constructor(private val context: Context) {

    var cityName: String? = null

    var lat: String? = null

    var lng: String? = null

    var countryName: String? = null

    var temp: String? = null

    var tempMin: String? = null

    var tempMax: String? = null

    var desc: String? = null


    fun setValueToFields(weatherModel: CityWeatherModel) {
        //city name
        cityName = weatherModel.name + "," + weatherModel.sys.country

        lat = String.format(context.getString(R.string.lat), weatherModel.coord.lat)
        lng = String.format(context.getString(R.string.lng), weatherModel.coord.lon)
        countryName = weatherModel.sys.country

        val temperature: Double = weatherModel.main.temp - 273.15
        val temperatureMin: Double = weatherModel.main.temp_min - 273.15
        val temperatureMax: Double = weatherModel.main.temp_max - 273.15

        temp = String.format(context.getString(R.string.temp), temperature)

        tempMin =
            String.format(context.getString(R.string.temp_min), temperatureMin)
        tempMax =
            String.format(context.getString(R.string.temp_max), temperatureMax)

        if (weatherModel.weather.isNotEmpty()) {
            desc = String.format(
                context.getString(R.string.description),
                weatherModel.weather[0].description
            )
        }

    }


    fun setEmptyValues() {

        cityName = ""
        lat = ""
        lng = ""
        countryName = ""
        temp = ""
        tempMin = ""
        tempMax = ""
        desc = ""

    }
}