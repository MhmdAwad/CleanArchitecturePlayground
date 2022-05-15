package com.mhmdawad.cleanarchitectureplayground.presentation.coins_list

import com.mhmdawad.cleanarchitectureplayground.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)