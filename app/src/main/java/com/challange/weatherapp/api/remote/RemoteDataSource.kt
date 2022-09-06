package com.challange.weatherapp.api.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val googleApiService: GoogleApiService
) {

    suspend fun getWeatherData(latitude: String, longitude: String) =
        weatherApiService.getWeatherData(latitude, longitude)

    suspend fun getAutoCompletePlaces(input: String) =
        googleApiService.getAutoCompletePlaces(input);

    suspend fun getPlaceDetails(placeId: String) =
        googleApiService.getPlaceDetails(placeId);

}