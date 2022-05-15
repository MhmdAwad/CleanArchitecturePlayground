package com.mhmdawad.cleanarchitectureplayground.presentation.coins_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmdawad.cleanarchitectureplayground.common.Resource
import com.mhmdawad.cleanarchitectureplayground.domain.use_case.coins_list.GetCoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val useCase: GetCoinListUseCase
): ViewModel(){

    private val _state = MutableStateFlow(CoinListState())
    val state = _state.asStateFlow()

    init {
        getCoins()
    }

    private fun getCoins() {
        useCase().onEach {result->
            when(result){
                is Resource.Success-> {
                    _state.value = CoinListState(coins = result.data?: emptyList())
                }
                is Resource.Loading-> {
                    _state.value = CoinListState(isLoading = true)
                }
                is Resource.Error-> {
                    _state.value = CoinListState(error = "An error occurred!")
                }
            }
        }.launchIn(viewModelScope)
    }
}