package com.challange.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challange.weatherapp.api.Repository
import com.challange.weatherapp.model.PlaceAutoCompleteResponse
import com.challange.weatherapp.model.PlaceDetailsResponse
import com.challange.weatherapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchPlacesViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private var _autocompleteResult =
        MutableLiveData<NetworkResult<PlaceAutoCompleteResponse>>()

    val autocompleteResult: LiveData<NetworkResult<PlaceAutoCompleteResponse>>
        get() = _autocompleteResult

    private var _placeDetailsResponse =
        MutableLiveData<NetworkResult<PlaceDetailsResponse>>()
    val placeDetailsResponse: LiveData<NetworkResult<PlaceDetailsResponse>>
        get() = _placeDetailsResponse


    fun getPlacesFromAutocomplete(input: String) {
        _autocompleteResult.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                repository.getAutoCompletePlaces(input).collect { values ->
                    _autocompleteResult.value = values

                }

            } catch (e: Exception) {
                _autocompleteResult.value =
                    NetworkResult.Error(data = null, message = "Something went wrong!")
            }
        }
    }


    fun getPlaceDetails(placeId: String) {
        _placeDetailsResponse.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                repository.getPlaceDetails(placeId).collect{ values ->
                        _placeDetailsResponse.value = values
                }
            } catch (e: java.lang.Exception) {
                _placeDetailsResponse.value =
                    NetworkResult.Error(data = null, message = "Something went wrong!")
            }
        }
    }

}