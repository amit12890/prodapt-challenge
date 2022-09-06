package com.challange.weatherapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.challange.weatherapp.adapter.SearchPlacesAdapter
import com.challange.weatherapp.databinding.ActivitySearchPlacesBinding
import com.challange.weatherapp.model.PlaceAutoCompleteResponse
import com.challange.weatherapp.utils.NetworkResult
import com.challange.weatherapp.viewmodel.SearchPlacesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPlacesActivity : AppCompatActivity() {

    private val viewModel  by viewModels<SearchPlacesViewModel>()

    private lateinit var binding : ActivitySearchPlacesBinding
    private lateinit var adapter: SearchPlacesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel.autocompleteResult.observe(this, Observer {
            it?.let {
                when (it) {
                    is NetworkResult.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.placesList.visibility = View.VISIBLE
                        adapter.items =
                            it.data?.predictions as ArrayList<PlaceAutoCompleteResponse.Prediction>
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

        binding.searchPlacesEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length > 3) {
                    viewModel.getPlacesFromAutocomplete(s.toString())
                } else {
                    binding.placesList.visibility = View.GONE
                }
            }
        })
    }

    private fun setupRecyclerView() {

        adapter = SearchPlacesAdapter(object : SearchPlacesAdapter.OnItemClickListener {
            override fun onItemClick(item: PlaceAutoCompleteResponse.Prediction) {
                val intent = Intent()
                intent.putExtra("placeId", item.placeId)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        })
        binding.placesList.adapter = adapter
    }
}