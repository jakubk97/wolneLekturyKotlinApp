package com.example.kuba.androidmvvm.mainView.network

import com.example.kuba.androidmvvm.mainView.model.AudioBookModel
import com.example.kuba.androidmvvm.mainView.model.AuthorModel
import com.example.kuba.androidmvvm.mainView.model.BookModel
import com.example.kuba.androidmvvm.mainView.model.EpochModel
import io.reactivex.Observable
import retrofit2.http.GET
interface ApiClient {

//    @Headers("X-Auth-Token:36305f804d564e38aa4652c3f633221a")

    @GET("books/")
    fun getAllBooks(): Observable<List<BookModel>>

    @GET("audiobooks/")
    fun getAllAudioBooks(): Observable<List<AudioBookModel>>

    @GET("authors/")
    fun getAllAuthors(): Observable<List<AuthorModel>>

    @GET("epochs/")
    fun getAllEpochs(): Observable<List<EpochModel>>
}