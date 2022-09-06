package com.challange.weatherapp.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.challange.weatherapp.R
import com.challange.weatherapp.databinding.ItemLocationBinding
import com.challange.weatherapp.model.PlaceAutoCompleteResponse


class SearchPlaceViewHolder(private val binding: ItemLocationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PlaceAutoCompleteResponse.Prediction) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }


    companion object {
        fun create(parent: ViewGroup): SearchPlaceViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_location, parent, false)
            val binding = ItemLocationBinding.bind(view)
            return SearchPlaceViewHolder(binding)
        }

    }

    class SearchPlaceDiffUtils(
        private val oldList: ArrayList<PlaceAutoCompleteResponse.Prediction>,
        private val newList: ArrayList<PlaceAutoCompleteResponse.Prediction>
    ) :
        DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }


}