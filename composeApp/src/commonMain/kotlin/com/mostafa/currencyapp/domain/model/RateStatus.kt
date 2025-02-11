package com.mostafa.currencyapp.domain.model

import androidx.compose.ui.graphics.Color
import freshColor
import staleColor

enum class RateStatus(
    val title: String,
    val color: Color
) {
    Idle("Rates", Color.White),
    Fresh("Fresh rates", freshColor),
    Stale("Rates are not fresh", staleColor),
}