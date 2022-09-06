package com.challange.weatherapp.api.remote

import com.challange.weatherapp.model.PlaceAutoCompleteResponse
import com.challange.weatherapp.model.PlaceDetailsResponse
import com.challange.weatherapp.utils.Constants
import com.challange.weatherapp.utils.Utils.getGoogleClientApiKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GoogleApiService {

    @Headers(Constants.GoogleAPIs.HEADER_ACCEPT_ENCODING)
    @GET(Constants.GoogleAPIs.GOOGLE_PLACE_AUTOCOMPLETE)
    suspend fun getAutoCompletePlaces(
        @Query("input") input: String?,
        @Query("key") googleMapApiKey: String = getGoogleClientApiKey()
    ): Response<PlaceAutoCompleteResponse>


    @Headers(Constants.GoogleAPIs.HEADER_ACCEPT_ENCODING)
    @GET(Constants.GoogleAPIs.GOOGLE_PLACE_DETAILS)
    suspend fun getPlaceDetails(
        @Query("place_id") input: String?,
        @Query("key") googleMapApiKey: String = getGoogleClientApiKey()
    ): Response<PlaceDetailsResponse>
}