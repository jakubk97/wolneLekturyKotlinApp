package com.example.kuba.androidmvvm.mainView.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kuba.androidmvvm.helpers.*
import com.example.kuba.androidmvvm.mainView.model.EpochModel
import com.example.kuba.androidmvvm.mainView.network.ApiService
import com.example.kuba.androidmvvm.mainView.network.ApiServiceInterface
import io.reactivex.disposables.Disposable

interface MainViewModelEpochInterface {
    val epochs: LiveData<List<EpochModel>>

    val progress: LiveData<Boolean>
    val errors: LiveData<ErrorMessage>
    val epochsCount: Int

    fun getEpochsData(search: String = "")
}

class MainViewModelEpoch(private val apiService: ApiServiceInterface = ApiService()): ViewModel(), MainViewModelEpochInterface {

    // Public Properties

    override val epochs: LiveData<List<EpochModel>>
        get() = epochsData

    override val progress: LiveData<Boolean>
        get() = progressData

    override val errors: LiveData<ErrorMessage>
        get() = errorsData

    override val epochsCount: Int
        get() = epochsData.value?.count() ?: 0

    // Private Properties

    private val epochsData = MutableLiveData<List<EpochModel>>()
    private val progressData = MutableLiveData<Boolean>(false)
    private val errorsData = MutableLiveData<ErrorMessage>()

    private var disposable: Disposable? = null

    // Internal Methods

    override fun getEpochsData(search: String) {

        disposable?.dispose()
        if (search.equals("")) {
            disposable = apiService.getAllEpochs()
                .subscribeOnIOThread()
                .observeOnMainThread()
                .withProgress(progressData)
                .showErrorMessages(errorsData)
                .subscribe {
                    epochsData.value = it
                }
        } else {
            disposable = apiService.getAllEpochs()
                .subscribeOnIOThread()
                .observeOnMainThread()
                .withProgress(progressData)
                .showErrorMessages(errorsData)
                .subscribe {
                    epochsData.value = it.filter { s -> s.name.toLowerCase().contains(search.toLowerCase()) }
                }
        }

    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}