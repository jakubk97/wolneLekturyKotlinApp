package com.example.kuba.androidmvvm.mainView.model

import com.google.gson.annotations.SerializedName

data class AuthorModel(
    @SerializedName("url")
    val url: String = "",
    @SerializedName("href")
    val href: String = "",
    @SerializedName("name")
    val name: String = ""
)