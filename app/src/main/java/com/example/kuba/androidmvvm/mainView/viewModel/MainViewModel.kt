package com.example.kuba.androidmvvm.mainView.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kuba.androidmvvm.helpers.*
import com.example.kuba.androidmvvm.mainView.model.AudioBookModel
import com.example.kuba.androidmvvm.mainView.network.ApiService
import com.example.kuba.androidmvvm.mainView.network.ApiServiceInterface
import io.reactivex.disposables.Disposable

interface MainViewModelInterface {
    val audioBooks: LiveData<List<AudioBookModel>>

    val progress: LiveData<Boolean>
    val errors: LiveData<ErrorMessage>
    val audioBooksCount: Int

    fun getAudioBooksData(search: String = "")
}

class MainViewModel(private val apiService: ApiServiceInterface = ApiService()) : ViewModel(),
    MainViewModelInterface {

    // Public Properties

    override val audioBooks: LiveData<List<AudioBookModel>>
        get() = audioBooksData

    override val progress: LiveData<Boolean>
        get() = progressData

    override val errors: LiveData<ErrorMessage>
        get() = errorsData

    override val audioBooksCount: Int
        get() = audioBooksData.value?.count() ?: 0

    // Private Properties

    private val audioBooksData = MutableLiveData<List<AudioBookModel>>()
    private val progressData = MutableLiveData<Boolean>(false)
    private val errorsData = MutableLiveData<ErrorMessage>()

    private var disposable: Disposable? = null

    // Internal Methods

    override fun getAudioBooksData(search: String) {

        disposable?.dispose()
        if (search.equals("")) {
            disposable = apiService.getAllAudioBooks()
                .subscribeOnIOThread()
                .observeOnMainThread()
                .withProgress(progressData)
                .showErrorMessages(errorsData)
                .subscribe {
                    audioBooksData.value = it
                }
        } else {
            disposable = apiService.getAllAudioBooks()
                .subscribeOnIOThread()
                .observeOnMainThread()
                .withProgress(progressData)
                .showErrorMessages(errorsData)
                .subscribe {
                    audioBooksData.value = it.filter { s -> s.title.toLowerCase().contains(search.toLowerCase()) }
                }
        }

    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}