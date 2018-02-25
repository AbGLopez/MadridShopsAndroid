package com.keepcoding.madridshops.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.domain.model.Shop
import com.keepcoding.madridshops.tools.getShopText
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.info_window_adapter.view.*

class ShopAdapter(val context: Context) : GoogleMap.InfoWindowAdapter {

    @SuppressLint("InflateParams")
    override fun getInfoContents(m: Marker): View {
        val view = LayoutInflater.from(context).inflate(R.layout.info_window_adapter, null)

        if (m.tag is Shop) {
            val shop = m.tag as Shop

            view.info_window_name.text = shop.name
            view.info_window_hours.text = getShopText(shop, "openingHours")
            val shopImage = view.info_window_image

            Picasso
                    .with(context)
                    .load(shop.logoURL)
                    .placeholder(R.drawable.default_no_image)
                    .into(shopImage, MarkerCallback(m, shop.logoURL, shopImage, context))
        }

        return view
    }

    override fun getInfoWindow(m: Marker): View? {
        return null
    }
}

class MarkerCallback(val marker: Marker,
                     val url: String,
                     val imageView: ImageView,
                     val context: Context): Callback {

    override fun onSuccess() {
        if (marker.isInfoWindowShown) {
            marker.hideInfoWindow()

            Picasso
                    .with(context)
                    .load(url)
                    .placeholder(R.drawable.default_no_image)
                    .into(imageView)

            marker.showInfoWindow()
        }
    }

    override fun onError() { Log.d("PICASSO", "Error updating pinView image on googleMap.") }
}
