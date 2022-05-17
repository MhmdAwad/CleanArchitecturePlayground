package com.mhmdawad.cleanarchitectureplayground.data.network

import com.mhmdawad.cleanarchitectureplayground.data.network.dto.CoinDetailDto
import com.mhmdawad.cleanarchitectureplayground.data.network.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path


interface CoinPaprikaApi {

    @GET("/v1/coins")
    suspend fun getCoins(): List<CoinDto>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") id: String): CoinDetailDto
}