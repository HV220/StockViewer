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
import com.squareup.picasso.Picasso


class CoinAdapter(

) : Adapter<CoinAdapter.CoinViewHolder>() {
    var dataCoins: List<Crypto> = ArrayList()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    var onReachEndListener: OnReachEndListener? = null
    var onContextNoInformationListener: OnContextNoInformationListener? = null

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
        if (coin.coin?.name != null && coin.display?.rate?.nameRate != null) {
            holder.coinRateName?.text =
                String.format("%s/%s", coin.coin.name, coin.display.rate.nameRate)

            holder.dataLastUpdate?.text = coin.raw?.coinInfo?.timeLastUpdate

            Picasso.get().load(coin.coin.imageUrl.toString())
                .resize(40, 40)
                .centerCrop()
                .into(holder.coinImage)
        } else {
            holder.coinRateName?.text =
                String.format("%s", onContextNoInformationListener?.setContextErrorForUser())
        }

        if (dataCoins.size - 10 == position) onReachEndListener?.doOnReachEnd()
    }

    interface OnReachEndListener {
        fun doOnReachEnd()
    }

    interface OnContextNoInformationListener {
        fun setContextErrorForUser(): String
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