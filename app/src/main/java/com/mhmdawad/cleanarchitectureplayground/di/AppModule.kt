package com.mhmdawad.cleanarchitectureplayground.di

import com.mhmdawad.cleanarchitectureplayground.common.Constants
import com.mhmdawad.cleanarchitectureplayground.data.network.CoinPaprikaApi
import com.mhmdawad.cleanarchitectureplayground.data.repository.CoinPaprikaRepositoryImpl
import com.mhmdawad.cleanarchitectureplayground.domain.repository.CoinPaprikaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCoinPaprikaApi(): CoinPaprikaApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinPaprikaRepository(api: CoinPaprikaApi): CoinPaprikaRepository{
        return CoinPaprikaRepositoryImpl(api)
    }
}