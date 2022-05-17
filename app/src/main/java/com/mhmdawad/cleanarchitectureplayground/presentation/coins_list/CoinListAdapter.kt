package com.mhmdawad.cleanarchitectureplayground.presentation.coins_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mhmdawad.cleanarchitectureplayground.R
import com.mhmdawad.cleanarchitectureplayground.common.changeColorIfIsActive
import com.mhmdawad.cleanarchitectureplayground.databinding.CoinListItemLayoutBinding
import com.mhmdawad.cleanarchitectureplayground.domain.model.Coin

class CoinListAdapter : RecyclerView.Adapter<CoinListAdapter.CoinListViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(newCoinsList: List<Coin>?) {
        if (newCoinsList == null || newCoinsList.isEmpty()) return
        differ.submitList(newCoinsList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder =
        CoinListViewHolder(
            CoinListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class CoinListViewHolder(private val binding: CoinListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(coin: Coin) = with(binding.root) {

            binding.coinRankingWithNameTextView.text =
                resources.getString(R.string.rankWithName, coin.rank, coin.name)

            binding.isActiveTextView.changeColorIfIsActive(coin.isActive)
            setOnClickListener {
                onItemClickListener?.let {
                    it(coin.id)
                }
            }
        }
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setonClickListener(listener: (String) -> Unit){
        onItemClickListener = listener
    }
}