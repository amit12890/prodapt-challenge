package com.challange.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challange.weatherapp.api.Repository
import com.challange.weatherapp.model.Weather
import com.challange.weatherapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private var _weather =
        MutableLiveData<NetworkResult<Weather>>()

    val weather: LiveData<NetworkResult<Weather>>
        get() = _weather

    fun getWeather(latitude : String, longitude: String) {
        _weather.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                repository.getWeatherData(latitude, longitude).collect { values ->
                    _weather.value = values

                }

            } catch (e: Exception) {
                _weather.value =
                    NetworkResult.Error(data = null, message = "Something went wrong!")
            }
        }
    }

    fun date(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }


}