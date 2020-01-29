package com.example.kuba.androidmvvm.mainView.model

import com.google.gson.annotations.SerializedName

data class BookModel(
    @SerializedName("kind")
    val kind: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("author")
    val author: String = "",
    @SerializedName("url")
    val url: String = ""
//    @SerializedName("code")
//    val code: String = "",
//    @SerializedName("emblemUrl")
//    val emblemUrl: String = "",
//    @SerializedName("plan")
//    val plan: String = "",
//    @SerializedName("currentSeason")
//    val currentSeason: CurrentSeason = "",
//    @SerializedName("epoch")
//    val epoch: String = "",
//    @SerializedName("href")
//    val href: String = "",
//    @SerializedName("has_audio")
//    val has_audio: String = "",
//    @SerializedName("genre")
//    val genre: String = "",
//    @SerializedName("simple_thumb")
//    val simple_thumb: String = "",
//    @SerializedName("slug")
//    val slug: String = "",
//    @SerializedName("cover_thumb")
//    val symbol: String = "",
//    @SerializedName("liked")
//    val liked: String = ""
)