package com.keepcoding.madridshops.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.domain.model.Shop
import com.keepcoding.madridshops.domain.model.Shops
import com.squareup.picasso.Picasso

class ShopRecyclerViewAdapter(val shopList: Shops?) :
        RecyclerView.Adapter<ShopRecyclerViewAdapter.ShopListViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    override fun onBindViewHolder(holder: ShopListViewHolder?, position: Int) {
        shopList?.let {
            holder?.bindShop(shopList.get(position))
        }
    }

    override fun getItemCount(): Int {
        shopList?.let {
            return it.count()
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShopListViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.content_shop_list, parent, false)
        view.setOnClickListener(onClickListener)

        return ShopListViewHolder(view)
    }


    inner class ShopListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val shopImage = itemView.findViewById<ImageView>(R.id.shop_logo)
        val shopName = itemView.findViewById<TextView>(R.id.shop_name)
        val shopHours = itemView.findViewById<TextView>(R.id.shop_hours)

        fun bindShop(shop: Shop) {
            shopName.text = shop.name

            Picasso.with(itemView.context).setIndicatorsEnabled(true)
            Picasso.with(itemView.context).isLoggingEnabled = true

            Picasso
                    .with(itemView.context)
                    .load(shop.logoURL)
                    .placeholder(R.drawable.default_no_image)
                    .into(shopImage)

            // TODO mostar formato hora en función idioma
            shopHours.text = shop.openingHours_es
        }
    }
}
