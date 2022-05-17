package com.mhmdawad.cleanarchitectureplayground.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmdawad.cleanarchitectureplayground.common.Constants
import com.mhmdawad.cleanarchitectureplayground.common.Resource
import com.mhmdawad.cleanarchitectureplayground.domain.model.CoinDetail
import com.mhmdawad.cleanarchitectureplayground.domain.use_case.coin_detail.GetCoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val useCase: GetCoinDetailUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _state = MutableStateFlow<Resource<CoinDetail>>(Resource.Loading())
    val state = _state.asStateFlow()

    init {
        CoinDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).let {
            getCoinById(it.coinId)
        }
    }

    private fun getCoinById(coinId: String) {
               useCase(coinId).onEach {result->
            _state.value = result
        }.launchIn(viewModelScope)
    }
}