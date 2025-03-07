package com.mostafa.currencyapp.data.local

import com.mostafa.currencyapp.domain.MongoRepository
import com.mostafa.currencyapp.domain.model.Currency
import com.mostafa.currencyapp.domain.model.RequestState
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MongoImpl: MongoRepository {

    private var realm: Realm? = null

    init {
        configureTheRealm()
    }

    override fun configureTheRealm() {
        if (realm == null || realm!!.isClosed()) {
            val config = RealmConfiguration.Builder(
                schema = setOf(Currency::class),
            )
                .compactOnLaunch()
                .build()
            realm = Realm.open(config)
        }
    }

    override suspend fun insertCurrencyData(currency: Currency) {
        realm?.write {
            copyToRealm(currency)
        }
    }

    override fun readCurrencyData(): Flow<RequestState<List<Currency>>> {
        return realm?.query<Currency>()?.asFlow()?.map{ result ->
            RequestState.Success(data = result.list)
        } ?: flow { RequestState.Error("Realm is not configured.") }
    }

    override suspend fun cleanUp() {
        realm?.write {
            val currencyCollection = this.query<Currency>()
            delete(currencyCollection)
        }
    }
}