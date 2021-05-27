package com.mistpaag.metaweatherapp.presentation.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mistpaag.domain.WLocation
import com.mistpaag.metaweatherapp.databinding.SearchLocationItemBinding
import com.mistpaag.metaweatherapp.utils.DiffCallback


class SearchLocationAdapter(val itemClick:(WLocation) -> Unit) :ListAdapter<WLocation, SearchLocationAdapter.ViewHolder>(
    DiffCallback<WLocation>(
        {old, new -> old == new},
        { old: WLocation, new: WLocation -> old.woeid == new.woeid}
    )
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder( SearchLocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false) )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo( getItem(position) )
    }

    inner class ViewHolder(
        private val binding: SearchLocationItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: WLocation){
            binding.apply {
                titleText.text = item.title
                locationText.text = item.locationType

                cardContainer.setOnClickListener { itemClick(item) }
            }
        }
    }


}