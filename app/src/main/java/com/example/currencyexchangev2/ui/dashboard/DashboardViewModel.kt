package com.example.currencyexchangev2.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyexchangev2.model.CurrencyPair
import com.example.currencyexchangev2.services.Repository

class DashboardViewModel : ViewModel() {

    private val _mFavorites = MutableLiveData<List<CurrencyPair>>().apply {
        value = Repository.getFavorites().value
    }
    val favorites: LiveData<List<CurrencyPair>> = _mFavorites
}