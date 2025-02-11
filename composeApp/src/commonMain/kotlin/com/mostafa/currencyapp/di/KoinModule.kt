package com.mostafa.currencyapp.di

import com.mostafa.currencyapp.data.local.MongoImpl
import com.mostafa.currencyapp.data.local.PreferencesImpl
import com.mostafa.currencyapp.data.remote.api.CurrencyApiServiceImpl
import com.mostafa.currencyapp.domain.CurrencyApiService
import com.mostafa.currencyapp.domain.MongoRepository
import com.mostafa.currencyapp.domain.PreferencesRepository
import com.mostafa.currencyapp.presentation.screen.HomeViewModel
import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single { Settings() }
    single<PreferencesRepository> { PreferencesImpl(settings = get()) }
    single<MongoRepository> { MongoImpl() }
    single<CurrencyApiService> { CurrencyApiServiceImpl(preferences = get()) }
    factory {
        HomeViewModel(
            preferences = get(),
            mongoDb = get(),
            api = get()
        )
    }
}

fun initializeKoin(){
    startKoin {
        modules(appModule)
    }
}