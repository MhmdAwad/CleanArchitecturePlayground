package com.mhmdawad.cleanarchitectureplayground.domain.use_case.coin_detail

import com.mhmdawad.cleanarchitectureplayground.common.Resource
import com.mhmdawad.cleanarchitectureplayground.data.network.dto.toCoin
import com.mhmdawad.cleanarchitectureplayground.data.network.dto.toCoinDetail
import com.mhmdawad.cleanarchitectureplayground.domain.model.Coin
import com.mhmdawad.cleanarchitectureplayground.domain.model.CoinDetail
import com.mhmdawad.cleanarchitectureplayground.domain.repository.CoinPaprikaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinDetailUseCase@Inject constructor(
    private val repo: CoinPaprikaRepository
) {

    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow{
        try{
            emit(Resource.Loading())
            val coins = repo.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success(coins))
        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: "Please check your internet connection!"))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An error occurred, Please try again!"))
        }catch (e: NullPointerException){
            emit(Resource.Error(e.localizedMessage ?: "An error occurred, Please try again!"))
        }
    }
}