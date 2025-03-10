package com.mostafa.currencyapp.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mostafa.currencyapp.domain.model.Currency
import com.mostafa.currencyapp.domain.model.RequestState
import com.mostafa.currencyapp.util.DoubleConverter
import com.mostafa.currencyapp.util.GetBebasFontFamily
import com.mostafa.currencyapp.util.calculateExchangeRate
import com.mostafa.currencyapp.util.convert
import headerColor

@Composable
fun HomeBody(
    source: RequestState<Currency>,
    target: RequestState<Currency>,
    amount: String
) {
    var exchangedAmount by rememberSaveable { mutableStateOf(0.0) }
    val animatedExchangedAmount by animateValueAsState(
        targetValue = exchangedAmount,
        animationSpec = tween(durationMillis = 300),
        typeConverter = DoubleConverter()
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding()
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${(animatedExchangedAmount * 100).toLong() / 100.0}",
                fontSize = 60.sp,
                fontFamily = GetBebasFontFamily(),
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                textAlign = TextAlign.Center
            )
            AnimatedVisibility(visible = source.isSuccess() && target.isSuccess()) {
                println("Tagsource: ${source.getSuccessData().code} ${source.getSuccessData().value}")
                println("Tagtarget: ${target.getSuccessData().code} ${target.getSuccessData().value}")
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "1 ${source.getSuccessData().code} = " +
                                "${target.getSuccessData().value} " +
                                target.getSuccessData().code,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        color = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.5f)
                        else Color.Black.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                }
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 24.dp)
                .background(
                    color = Color.Unspecified,
                    shape = RoundedCornerShape(99.dp)
                ),
            onClick = {
                if (source.isSuccess() && target.isSuccess()) {
                    val exchangeRate = calculateExchangeRate(
                        source = source.getSuccessData().value,
                        target = target.getSuccessData().value
                    )
                    exchangedAmount = convert(
                        amount = if (amount.isEmpty()) 0.0 else amount.toDouble(),
                        exchangeRate = exchangeRate
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = headerColor,
                contentColor = Color.White
            )
        ) {
            Text("Convert")
        }
    }
}