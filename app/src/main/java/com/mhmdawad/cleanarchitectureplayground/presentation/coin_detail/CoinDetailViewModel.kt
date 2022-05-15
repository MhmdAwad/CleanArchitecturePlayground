package com.mhmdawad.cleanarchitectureplayground.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmdawad.cleanarchitectureplayground.common.Constants
import com.mhmdawad.cleanarchitectureplayground.common.Resource
import com.mhmdawad.cleanarchitectureplayground.domain.use_case.coin_detail.GetCoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val useCase: GetCoinDetailUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _state = MutableStateFlow(CoinDetailState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let {id->
            getCoinById(id)
        }

    }

    private fun getCoinById(coinId: String) {
        useCase(coinId).onEach {result->
            when(result){
                is Resource.Success-> {
                    _state.value = CoinDetailState(coins = result.data)
                }
                is Resource.Loading-> {
                    _state.value = CoinDetailState(isLoading = true)
                }
                is Resource.Error-> {
                    _state.value = CoinDetailState(error = result.msg ?: "An error occurred!")
                }
            }
        }.launchIn(viewModelScope)
    }
}