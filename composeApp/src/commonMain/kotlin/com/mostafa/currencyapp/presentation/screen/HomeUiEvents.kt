package com.mostafa.currencyapp.presentation.screen

sealed class HomeUiEvents {
    data object RefreshRates : HomeUiEvents()
    data object SwitchCurrencies : HomeUiEvents()
    data class SaveSourceCurrencyCode(val code: String): HomeUiEvents()
    data class SaveTargetCurrencyCode(val code: String): HomeUiEvents()}