package com.mhmdawad.cleanarchitectureplayground.data.repository

import com.mhmdawad.cleanarchitectureplayground.data.network.CoinPaprikaApi
import com.mhmdawad.cleanarchitectureplayground.data.network.dto.CoinDetailDto
import com.mhmdawad.cleanarchitectureplayground.data.network.dto.CoinDto
import com.mhmdawad.cleanarchitectureplayground.domain.repository.CoinPaprikaRepository
import javax.inject.Inject

class CoinPaprikaRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi
) : CoinPaprikaRepository {

    override suspend fun getCoins(): List<CoinDto>  = api.getCoins()

    override suspend fun getCoinById(coinId: String): CoinDetailDto = api.getCoinById(coinId)
}