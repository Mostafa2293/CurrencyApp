package com.mostafa.currencyapp.domain

import com.mostafa.currencyapp.domain.model.Currency
import com.mostafa.currencyapp.domain.model.RequestState

interface CurrencyApiService {
    suspend fun getLatestExchangeRates(): RequestState<List<Currency>>
}