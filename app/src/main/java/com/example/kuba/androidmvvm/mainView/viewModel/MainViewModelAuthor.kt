package com.example.kuba.androidmvvm.mainView.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kuba.androidmvvm.helpers.*
import com.example.kuba.androidmvvm.mainView.model.AuthorModel
import com.example.kuba.androidmvvm.mainView.network.ApiService
import com.example.kuba.androidmvvm.mainView.network.ApiServiceInterface
import io.reactivex.disposables.Disposable

interface MainViewModelAuthorInterface {
    val authors: LiveData<List<AuthorModel>>

    val progress: LiveData<Boolean>
    val errors: LiveData<ErrorMessage>
    val authorsCount: Int

    fun getAuthorsData(search: String = "")
}

class MainViewModelAuthor(private val apiService: ApiServiceInterface = ApiService()): ViewModel(), MainViewModelAuthorInterface {

    // Public Properties

    override val authors: LiveData<List<AuthorModel>>
        get() = authorsData

    override val progress: LiveData<Boolean>
        get() = progressData

    override val errors: LiveData<ErrorMessage>
        get() = errorsData

    override val authorsCount: Int
        get() = authorsData.value?.count() ?: 0

    // Private Properties

    private val authorsData = MutableLiveData<List<AuthorModel>>()
    private val progressData = MutableLiveData<Boolean>(false)
    private val errorsData = MutableLiveData<ErrorMessage>()

    private var disposable: Disposable? = null

    // Internal Methods

    override fun getAuthorsData(search: String) {

        disposable?.dispose()

        if (search.equals("")) {
            disposable = apiService.getAllAuthors()
                .subscribeOnIOThread()
                .observeOnMainThread()
                .withProgress(progressData)
                .showErrorMessages(errorsData)
                .subscribe {
                    authorsData.value = it
                }
        } else {
            disposable = apiService.getAllAuthors()
                .subscribeOnIOThread()
                .observeOnMainThread()
                .withProgress(progressData)
                .showErrorMessages(errorsData)
                .subscribe {
                    authorsData.value = it.filter { s -> s.name.toLowerCase().contains(search.toLowerCase()) }
                }
        }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}