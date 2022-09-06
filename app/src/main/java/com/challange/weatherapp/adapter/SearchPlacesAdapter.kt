package com.challange.weatherapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.challange.weatherapp.model.PlaceAutoCompleteResponse
import com.challange.weatherapp.viewholder.SearchPlaceViewHolder


class SearchPlacesAdapter(private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<SearchPlaceViewHolder>() {

    var items: ArrayList<PlaceAutoCompleteResponse.Prediction> = ArrayList()
        set(value) {
            field = value
            val diffCallback = SearchPlaceViewHolder.SearchPlaceDiffUtils(itemsFiltered, items)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            if (itemsFiltered.size > 0)
                itemsFiltered.clear()
            itemsFiltered.addAll(value)
            diffResult.dispatchUpdatesTo(this)
        }

    var itemsFiltered: ArrayList<PlaceAutoCompleteResponse.Prediction> = ArrayList()
        set(value) {
            field = value
            val diffCallback = SearchPlaceViewHolder.SearchPlaceDiffUtils(itemsFiltered, items)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPlaceViewHolder {
        return SearchPlaceViewHolder.create(parent)
    }

    override fun getItemCount(): Int = itemsFiltered.size

    override fun onBindViewHolder(holder: SearchPlaceViewHolder, position: Int) {
        val item = itemsFiltered[position]
        if (item != null) {
            holder.bind(item)
            holder.itemView.setOnClickListener { clickListener.onItemClick(item) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: PlaceAutoCompleteResponse.Prediction)
    }
}