package com.example.stockviewer.adapter.cryptocompare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.stockviewer.R
import com.example.stockviewer.api.cryptocompare.responce.Crypto


class CoinAdapter(

) : Adapter<CoinAdapter.CoinViewHolder>() {
    var dataCoins : List<Crypto> = ArrayList()
        set(value) {
           notifyDataSetChanged()
            field = value
        }

    var onReachEndListener: OnReachEndListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coins, parent, false)
        return CoinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataCoins.size
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = dataCoins[position]

        holder.coinPrice?.text = coin.display?.rate?.price
        holder.coinRateName?.text =
            String.format("%s/%s", coin.coin?.name, coin.display?.rate?.nameRate)

        if (dataCoins.size - 10 == position) onReachEndListener?.doOnReachEnd()
    }

    interface OnReachEndListener {
        fun doOnReachEnd()
    }

    class CoinViewHolder(
        itemView: View,
        val coinImage: ImageView? = itemView.findViewById(R.id.coinImage),
        val coinRateName: TextView? = itemView.findViewById(R.id.coinRateName),
        val coinPrice: TextView? = itemView.findViewById(R.id.coinPrice),
        val dataLastUpdate: TextView? = itemView.findViewById(R.id.dataLastUpdate),
    ) : ViewHolder(
        itemView,
    )
}