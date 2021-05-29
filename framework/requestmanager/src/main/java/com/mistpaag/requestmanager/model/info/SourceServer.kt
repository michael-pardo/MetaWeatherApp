package com.mistpaag.requestmanager.model.info


import com.google.gson.annotations.SerializedName

data class SourceServer(
    @SerializedName("crawl_rate")
    val crawlRate: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)