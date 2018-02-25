package com.keepcoding.madridshops.router

import android.content.Intent
import com.keepcoding.madridshops.activity.MainActivity
import com.keepcoding.madridshops.activity.PicassoActivity


class Router {

    fun navigateFromMainActivityToPicassoActivity(main: MainActivity) {
        main.startActivity(Intent(main, PicassoActivity::class.java))
    }

    fun navigateFromShopActivityToShopDetailActivity(currentActivity: ShopActivity, shop: Shop) {
        val intent = Intent(currentActivity, ShopDetailActivity::class.java)
        intent.putExtra(INTENT_SHOP_DETAIL, shop)

        currentActivity.startActivity(intent)
    }

    fun navigateFromMainActivityToShopActivity(currentActivity: MainActivity) {
        val intent = Intent(currentActivity, ShopActivity::class.java)

        currentActivity.startActivity(intent)
    }


    fun navigateFromActivityActivityToActivityDetailActivity(currentActivity: ActivityActivity, activity: Activity) {
        val intent = Intent(currentActivity, ActivityDetailActivity::class.java)
        intent.putExtra(INTENT_ACTIVITY_DETAIL, activity)

        currentActivity.startActivity(intent)
    }


}