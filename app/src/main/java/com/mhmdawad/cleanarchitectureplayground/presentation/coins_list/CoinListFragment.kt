package com.mhmdawad.cleanarchitectureplayground.presentation.coins_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mhmdawad.cleanarchitectureplayground.R
import com.mhmdawad.cleanarchitectureplayground.common.Resource
import com.mhmdawad.cleanarchitectureplayground.common.hide
import com.mhmdawad.cleanarchitectureplayground.common.show
import com.mhmdawad.cleanarchitectureplayground.databinding.FragmentCoinListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CoinListFragment : Fragment(R.layout.fragment_coin_list) {

    private val viewModel: CoinListViewModel by viewModels()
    private lateinit var binding: FragmentCoinListBinding
    private val coinListAdapter by lazy { CoinListAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
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
                                coinListRV.hide()
                            }
                        }
                        is Resource.Loading -> {
                            binding.run {
                                noInternetConnection.root.hide()
                                loadingProgress.root.show()
                                coinListRV.hide()
                            }
                        }
                        is Resource.Success -> {
                            binding.run {
                                coinListRV.show()
                                loadingProgress.root.hide()
                                noInternetConnection.root.hide()
                                coinListAdapter.submitList(result.data)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.coinListRV.adapter = coinListAdapter
        coinListAdapter.setonClickListener { coinId ->
            openCoinDetail(coinId)
        }
    }

    private fun openCoinDetail(coinId: String) {
        val action =
            CoinListFragmentDirections.actionCoinListFragmentToCoinDetailFragment(
                coinId
            )
        findNavController().navigate(action)
    }
}