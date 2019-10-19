package com.ganesh.weatherapp.util

import com.tamil.galassignment.data.model.CityWeatherModel

class TestData {

    private var wether = CityWeatherModel.Weather(1, "", "", "")
    private var lsit = listOf(wether)

    private var response = CityWeatherModel(
        CityWeatherModel.Coord("", ""),
        lsit,
        "",
        CityWeatherModel.Main(1.0, "", "", 1.0, 1.0),
        "",
        CityWeatherModel.Wind("", ""),
        CityWeatherModel.Clouds(""),
        "",
        CityWeatherModel.Sys("", 0, "", "DE", "", ""),
        "",
        1,
        "",
        ""
    )


    fun getData(): CityWeatherModel {
        return response
    }

}