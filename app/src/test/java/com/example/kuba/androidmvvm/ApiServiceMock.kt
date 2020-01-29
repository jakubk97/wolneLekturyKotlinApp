package com.example.kuba.androidmvvm

import com.example.kuba.androidmvvm.mainView.network.ApiServiceInterface
import io.reactivex.Observable

class ApiServiceMock: ApiServiceInterface {

    override fun getAllCoins(): Observable<List<CoinModel>> {

        val coinModel1 = CoinModel(
            "",
            "",
            "",
            "",
            "",
            "",
            "Test Bitcoin"
        )

        val coinModel2 = CoinModel(
            "",
            "",
            "",
            "",
            "",
            "",
            "Test Ethereum"
        )

        val coins = mutableListOf(coinModel1, coinModel2)

        return Observable.just(coins)
    }
}