package com.challange.weatherapp.api

import com.challange.weatherapp.api.remote.RemoteDataSource
import com.challange.weatherapp.model.BaseApiResponse
import com.challange.weatherapp.model.PlaceAutoCompleteResponse
import com.challange.weatherapp.model.PlaceDetailsResponse
import com.challange.weatherapp.model.Weather
import com.challange.weatherapp.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    fun getAutoCompletePlaces(
        input: String
    ): Flow<NetworkResult<PlaceAutoCompleteResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getAutoCompletePlaces(input) })
        }.flowOn(Dispatchers.IO)
    }


    fun getPlaceDetails(
        placeId: String
    ): Flow<NetworkResult<PlaceDetailsResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getPlaceDetails(placeId) })
        }.flowOn(Dispatchers.IO)
    }

    fun  getWeatherData(
        latitude: String, longitude: String
    ): Flow<NetworkResult<Weather>>{
        return  flow {
            emit( safeApiCall { remoteDataSource.getWeatherData(latitude, longitude) })
        }.flowOn(Dispatchers.IO)
    }



}