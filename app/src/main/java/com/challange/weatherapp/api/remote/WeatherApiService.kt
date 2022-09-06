package com.challange.weatherapp.api.remote

import com.challange.weatherapp.model.Weather
import com.challange.weatherapp.utils.Constants
import com.challange.weatherapp.utils.Utils.getWeatherApiKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {

    @GET("data/2.5/weather/")
    suspend fun getWeatherData(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("appid") appId : String = getWeatherApiKey()
    ): Response<Weather>

}