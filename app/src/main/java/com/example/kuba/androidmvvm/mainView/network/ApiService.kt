package com.example.kuba.androidmvvm.mainView.network

import com.example.kuba.androidmvvm.mainView.model.AudioBookModel
import com.example.kuba.androidmvvm.mainView.model.AuthorModel
import com.example.kuba.androidmvvm.mainView.model.BookModel
import com.example.kuba.androidmvvm.mainView.model.EpochModel
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


interface ApiServiceInterface {
    fun getAllBooks(): Observable<List<BookModel>>
    fun getAllAudioBooks(): Observable<List<AudioBookModel>>
    fun getAllAuthors(): Observable<List<AuthorModel>>
    fun getAllEpochs(): Observable<List<EpochModel>>
}

class ApiService(private val baseUrl: String = "https://wolnelektury.pl/api/"): ApiServiceInterface {

    // Public Methods

    override fun getAllBooks(): Observable<List<BookModel>> {

        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(okHttpClient)
            .baseUrl(baseUrl).build()


        val postsApi = retrofit.create(ApiClient::class.java)

        return postsApi.getAllBooks()
    }

    override fun getAllAudioBooks(): Observable<List<AudioBookModel>> {

        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(okHttpClient)
            .baseUrl(baseUrl).build()


        val postsApi = retrofit.create(ApiClient::class.java)

        return postsApi.getAllAudioBooks()
    }

    override fun getAllAuthors(): Observable<List<AuthorModel>> {

        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(okHttpClient)
            .baseUrl(baseUrl).build()


        val postsApi = retrofit.create(ApiClient::class.java)

        return postsApi.getAllAuthors()
    }

    override fun getAllEpochs(): Observable<List<EpochModel>> {

        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(okHttpClient)
            .baseUrl(baseUrl).build()


        val postsApi = retrofit.create(ApiClient::class.java)

        return postsApi.getAllEpochs()
    }
}