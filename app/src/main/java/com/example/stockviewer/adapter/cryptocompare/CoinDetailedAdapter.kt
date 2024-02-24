package com.example.stockviewer.adapter.cryptocompare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.stockviewer.R

class CoinDetailedAdapter : Adapter<CoinDetailedAdapter.CoinDetailedViewHolder>() {
     var nameFolder: List<String> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
     var infoFolder: List<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinDetailedViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_about_coin, parent, false)
        return CoinDetailedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nameFolder.size
    }

    override fun onBindViewHolder(holder: CoinDetailedViewHolder, position: Int) {
        val itemTitle = nameFolder[position]
        val itemInfo = infoFolder[position]

        holder.nameFolder?.text = itemTitle
        holder.infoFolder?.text = itemInfo
    }

    class CoinDetailedViewHolder(
        itemView: View,
        val nameFolder: TextView? = itemView.findViewById(R.id.nameFolder),
        val infoFolder: TextView? = itemView.findViewById(R.id.infoFolder),
    ) : ViewHolder(
        itemView,
    )
}