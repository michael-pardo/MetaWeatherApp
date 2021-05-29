package com.mistpaag.imagemanager

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadAbbrImage(url: String){
    Glide.with(this.context)
        .load("https://www.metaweather.com/static/img/weather/png/" + url + ".png")
        .into(this)
}