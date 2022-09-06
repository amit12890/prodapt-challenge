package com.challange.weatherapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.challange.weatherapp.databinding.ActivityMainBinding
import com.challange.weatherapp.listener.GenericListeners
import com.challange.weatherapp.model.PlaceAutoCompleteResponse
import com.challange.weatherapp.utils.NetworkResult
import com.challange.weatherapp.utils.placeHolderProgressBar
import com.challange.weatherapp.viewmodel.MainViewModel
import com.challange.weatherapp.viewmodel.SearchPlacesViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.apache.commons.lang3.math.NumberUtils
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val searchViewModel by viewModels<SearchPlacesViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding

    private var currentLat: Double? = null
    private var currentLong: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listener = object : GenericListeners {
            override fun onTapGoToPlacesSearchScreen() {
                val intent = Intent(this@MainActivity, SearchPlacesActivity::class.java)
                startActivityForResult(intent, NumberUtils.INTEGER_ONE)
            }

        }

        searchViewModel.placeDetailsResponse.observe(this, Observer {
            it?.let {
                when (it) {
                    is NetworkResult.Success -> {
                        binding.progressBar.visibility = View.GONE
                        if (it.data != null) {
                            val location = it.data.result?.geometry?.location
                            currentLat = location?.lat
                            currentLong = location?.lng
                            mainViewModel.getWeather(currentLat.toString(), currentLong.toString())
                        }
                    }
                    is NetworkResult.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is NetworkResult.Error -> {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        })

        mainViewModel.weather.observe(this) { weather ->
            weather?.let {
                when (weather){
                    is NetworkResult.Success -> {
                        if (weather.data != null) {
                            Glide.with(this).setDefaultRequestOptions(
                                RequestOptions().placeholder(placeHolderProgressBar(applicationContext))
                                    .fitCenter()
                            ).load("https://openweathermap.org/img/wn/${weather.data.weather!![0].icon}@4x.png")
                                .into(binding.iconImageView)
                            binding.situationTextView.text = weather.data.weather!![0].situation
                            val celsius = (weather.data.degree!!.temp!!).roundToInt()
                            binding.degreeTextView.text = "$celsius°C"

                            weather.data.location?.let { location ->
                                binding.locationTextView.text = location
                            }
                            hideLoading()

                        }
                    }

                    is NetworkResult.Loading -> {
                        showLoading()
                    }

                    is NetworkResult.Error -> {
                        hideLoading()
                    }
                }

            }
        }

    }

    private fun showLoading(){
        binding.progressBar.visibility = View.VISIBLE
        binding.iconImageView.visibility = View.GONE
        binding.degreeTextView.visibility = View.GONE
        binding.situationTextView.visibility = View.GONE
        binding.locationTextView.visibility = View.GONE
        binding.timeTextView.visibility = View.GONE
    }

    private fun hideLoading(){
        binding.timeTextView.text = mainViewModel.date()
        binding.progressBar.visibility = View.GONE
        binding.iconImageView.visibility = View.VISIBLE
        binding.degreeTextView.visibility = View.VISIBLE
        binding.situationTextView.visibility = View.VISIBLE
        binding.locationTextView.visibility = View.VISIBLE
        binding.timeTextView.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == NumberUtils.INTEGER_ONE) {
                if (data != null && data.extras != null && data.extras!!.containsKey("placeId")) {
                    val placeId = data.getStringExtra("placeId")
                    searchViewModel.getPlaceDetails(placeId!!)
                }
            }
        }
    }
}