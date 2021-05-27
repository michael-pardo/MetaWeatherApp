package com.mistpaag.metaweatherapp.utils

import androidx.recyclerview.widget.DiffUtil

class DiffCallback<K>(
    private val compareItems: (old: K, new: K) -> Boolean,
    private val compareContents: (old: K, new: K) -> Boolean
) : DiffUtil.ItemCallback<K>() {
    override fun areItemsTheSame(old: K, new: K) = compareItems(old, new)
    override fun areContentsTheSame(old: K, new: K) = compareContents(old, new)
}