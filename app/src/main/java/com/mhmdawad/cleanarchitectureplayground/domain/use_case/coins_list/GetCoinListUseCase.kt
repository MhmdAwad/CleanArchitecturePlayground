package com.mhmdawad.cleanarchitectureplayground.domain.use_case.coins_list

import com.mhmdawad.cleanarchitectureplayground.common.Resource
import com.mhmdawad.cleanarchitectureplayground.data.network.dto.toCoin
import com.mhmdawad.cleanarchitectureplayground.domain.model.Coin
import com.mhmdawad.cleanarchitectureplayground.domain.repository.CoinPaprikaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinListUseCase @Inject constructor(
    private val repo: CoinPaprikaRepository
){

    operator fun invoke(): Flow<Resource<List<Coin>>> = flow{
        try{
            emit(Resource.Loading())
            val coins = repo.getCoins().map { it.toCoin() }
            emit(Resource.Success(coins))
        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: "Please check your internet connection!"))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An error occurred, Please try again!"))
        }
    }

}

