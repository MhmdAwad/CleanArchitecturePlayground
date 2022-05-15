package com.mhmdawad.cleanarchitectureplayground.domain.repository

import com.mhmdawad.cleanarchitectureplayground.data.network.dto.CoinDetailDto
import com.mhmdawad.cleanarchitectureplayground.data.network.dto.CoinDto

interface CoinPaprikaRepository {

    suspend fun getCoins(): List<CoinDto>

    suspend fun getCoinById(coinId: String): CoinDetailDto

}