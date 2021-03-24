package com.example.currencyexchangev2.services

import androidx.lifecycle.MutableLiveData
import com.example.currencyexchangev2.model.CurrencyPair

object Repository {

    private var favorites = ArrayList<CurrencyPair>()


    fun  getFavorites() : MutableLiveData<List<CurrencyPair>> {
        val mutable = MutableLiveData<List<CurrencyPair>>()
        mutable.setValue(favorites)
        return mutable
    }
}