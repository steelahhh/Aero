package io.github.steelahhh.data.remote.model

import com.google.gson.annotations.SerializedName

data class FilmResponse(
    val title: String,
    @SerializedName("episode_id")
    val episodeId: Int,
    @SerializedName("opening_crawl")
    val openingCrawl: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val url: String
)
