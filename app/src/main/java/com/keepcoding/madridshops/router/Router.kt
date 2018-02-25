package com.keepcoding.madridshops.router

import android.content.Intent
import com.keepcoding.madridshops.activity.*
import com.keepcoding.madridshops.domain.model.Activity
import com.keepcoding.madridshops.domain.model.Shop
import com.keepcoding.madridshops.tools.INTENT_ACTIVITY_DETAIL
import com.keepcoding.madridshops.tools.INTENT_SHOP_DETAIL

class Router {

    fun navigateFromShopActivityToShopDetailActivity(currentActivity: ShopActivity, shop: Shop) {
        val intent = Intent(currentActivity, ShopDetailActivity::class.java)
        intent.putExtra(INTENT_SHOP_DETAIL, shop)

        currentActivity.startActivity(intent)
    }

    fun navigateFromMainActivityToShopActivity(currentActivity: MainActivity) {
        val intent = Intent(currentActivity, ShopActivity::class.java)

        currentActivity.startActivity(intent)
    }


    fun navigateFromActivityActivityToActivityDetailActivity(currentActivity: MapActivity, activity: Activity) {
        val intent = Intent(currentActivity, MapDetailActivity::class.java)
        intent.putExtra(INTENT_ACTIVITY_DETAIL, activity)

        currentActivity.startActivity(intent)
    }

    fun navigateFromMainActivityToActivityActivity(currentActivity: MainActivity) {
        val intent = Intent(currentActivity, MapActivity::class.java)

        currentActivity.startActivity(intent)
    }

}


