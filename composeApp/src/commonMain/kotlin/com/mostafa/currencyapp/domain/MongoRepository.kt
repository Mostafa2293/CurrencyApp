package com.mostafa.currencyapp.domain

import com.mostafa.currencyapp.domain.model.Currency
import com.mostafa.currencyapp.domain.model.RequestState
import kotlinx.coroutines.flow.Flow

interface MongoRepository {
    fun configureTheRealm()
    suspend fun insertCurrencyData(currency: Currency)
    fun readCurrencyData(): Flow<RequestState<List<Currency>>>
    suspend fun cleanUp()
}