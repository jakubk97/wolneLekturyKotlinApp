package com.example.kuba.androidmvvm.mainView.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kuba.androidmvvm.helpers.*
import com.example.kuba.androidmvvm.mainView.model.AudioBookModel
import com.example.kuba.androidmvvm.mainView.model.BookModel
import com.example.kuba.androidmvvm.mainView.network.ApiService
import com.example.kuba.androidmvvm.mainView.network.ApiServiceInterface
import io.reactivex.disposables.Disposable

interface MainViewModelBookInterface {
    val books: LiveData<List<BookModel>>

    val progress: LiveData<Boolean>
    val errors: LiveData<ErrorMessage>
    val booksCount: Int

    fun getBooksData(search: String = "")
}

class MainViewModelBook(private val apiService: ApiServiceInterface = ApiService()): ViewModel(), MainViewModelBookInterface {

    // Public Properties

    override val books: LiveData<List<BookModel>>
        get() = booksData

    override val progress: LiveData<Boolean>
        get() = progressData

    override val errors: LiveData<ErrorMessage>
        get() = errorsData

    override val booksCount: Int
        get() = booksData.value?.count() ?: 0

    // Private Properties

    private val booksData = MutableLiveData<List<BookModel>>()
    private val progressData = MutableLiveData<Boolean>(false)
    private val errorsData = MutableLiveData<ErrorMessage>()
    private var disposable: Disposable? = null

    // Internal Methods

    override fun getBooksData(search: String) {

        disposable?.dispose()

        if (search.equals("")) {
            disposable = apiService.getAllBooks()
                .subscribeOnIOThread()
                .observeOnMainThread()
                .withProgress(progressData)
                .showErrorMessages(errorsData)
                .subscribe {
                    booksData.value = it
                }
        } else {
            disposable = apiService.getAllBooks()
                .subscribeOnIOThread()
                .observeOnMainThread()
                .withProgress(progressData)
                .showErrorMessages(errorsData)
                .subscribe {
                    booksData.value = it.filter { s -> s.title.toLowerCase().contains(search.toLowerCase()) }
                }
        }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}