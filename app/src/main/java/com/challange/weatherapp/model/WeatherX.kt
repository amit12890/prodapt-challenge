package com.challange.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherX(
    var icon: String? = null,
    @SerializedName("main")
    var situation: String? = null,
)