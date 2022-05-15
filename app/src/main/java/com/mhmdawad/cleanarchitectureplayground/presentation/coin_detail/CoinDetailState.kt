package com.mhmdawad.cleanarchitectureplayground.presentation.coin_detail

import com.mhmdawad.cleanarchitectureplayground.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coins: CoinDetail? = null,
    val error: String = ""
)