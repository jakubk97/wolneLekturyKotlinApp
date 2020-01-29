package com.example.kuba.androidmvvm.mainView.model

import com.google.gson.annotations.SerializedName

data class AudioBookModel(
    @SerializedName("kind")
    val kind: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("author")
    val author: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("epoch")
    val epoch: String = "",
    @SerializedName("genre")
    val genre: String = "",
    @SerializedName("href")
    val href: String = ""
)