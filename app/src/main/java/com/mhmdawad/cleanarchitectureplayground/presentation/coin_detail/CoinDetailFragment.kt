package com.mhmdawad.cleanarchitectureplayground.presentation.coin_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mhmdawad.cleanarchitectureplayground.R
import com.mhmdawad.cleanarchitectureplayground.common.Resource
import com.mhmdawad.cleanarchitectureplayground.common.changeColorIfIsActive
import com.mhmdawad.cleanarchitectureplayground.common.hide
import com.mhmdawad.cleanarchitectureplayground.common.show
import com.mhmdawad.cleanarchitectureplayground.databinding.FragmentCoinDetailBinding
import com.mhmdawad.cleanarchitectureplayground.domain.model.CoinDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CoinDetailFragment : Fragment(R.layout.fragment_coin_detail) {

    private val viewModel: CoinDetailViewModel by viewModels()
    private lateinit var binding: FragmentCoinDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        observeListener()
    }

    private fun observeListener() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            binding.run {
                                noInternetConnection.root.show()
                                loadingProgress.root.hide()
                                coinDetailLayout.hide()
                            }
                        }
                        is Resource.Loading -> {
                            binding.run {
                                noInternetConnection.root.hide()
                                loadingProgress.root.show()
                                coinDetailLayout.hide()
                            }
                        }
                        is Resource.Success -> {
                            binding.run {
                                result.data?.let {
                                    noInternetConnection.root.hide()
                                    loadingProgress.root.hide()
                                    coinDetailLayout.show()
                                    initViews(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initViews(coinDetail: CoinDetail) {
        binding.run {
            coinName.text =
                getString(R.string.coinName, coinDetail.rank, coinDetail.name, coinDetail.symbol)
            isActiveCoin.changeColorIfIsActive(coinDetail.isActive)
            coinDescription.text = coinDetail.description
        }
    }

}