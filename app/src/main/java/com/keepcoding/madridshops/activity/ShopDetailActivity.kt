package com.keepcoding.madridshops.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.domain.model.Shop
import com.keepcoding.madridshops.tools.GOOGLE_MAP_URL
import com.keepcoding.madridshops.tools.INTENT_SHOP_DETAIL
import com.keepcoding.madridshops.tools.getShopText
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_shop_detail.*

class ShopDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_detail)

        val shop = intent.getSerializableExtra(INTENT_SHOP_DETAIL) as Shop

        shop_name.text =  shop.name
        shop_description.text = getShopText(shop, "description")
        shop_address.text = shop.address
        shop_hours.text = getShopText(shop,"openingHours")

        Picasso
                .with(this)
                .load(shop.imageURL)
                .placeholder(R.drawable.default_no_image)
                .into(shop_image)

        val googleMapUrl = GOOGLE_MAP_URL + shop.latitude + "," + shop.longitude

        Picasso
                .with(this)
                .load(googleMapUrl)
                .placeholder(R.drawable.default_no_image)
                .into(shop_google_map)
    }
}