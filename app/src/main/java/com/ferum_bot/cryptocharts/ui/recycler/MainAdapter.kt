package com.ferum_bot.cryptocharts.ui.recycler

import androidx.recyclerview.widget.DiffUtil
import com.ferum_bot.cryptocharts.core.models.Ticker
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

private val callback = object: DiffUtil.ItemCallback<Ticker>() {

    override fun areContentsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
        return oldItem.time == newItem.time
    }

}

class MainAdapter: AsyncListDifferDelegationAdapter<Ticker>(callback) {



}
