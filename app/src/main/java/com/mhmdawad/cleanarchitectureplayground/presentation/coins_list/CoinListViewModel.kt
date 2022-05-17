package com.mhmdawad.cleanarchitectureplayground.presentation.coins_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmdawad.cleanarchitectureplayground.common.Resource
import com.mhmdawad.cleanarchitectureplayground.domain.model.Coin
import com.mhmdawad.cleanarchitectureplayground.domain.use_case.coins_list.GetCoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val useCase: GetCoinListUseCase
): ViewModel(){

    private val _state = MutableStateFlow<Resource<List<Coin>>>(Resource.Loading())
    val state = _state.asStateFlow()

    init {
        getCoins()
    }

    private fun getCoins() {
        useCase().onEach {result->
            _state.value = result
        }.launchIn(viewModelScope)
    }
}