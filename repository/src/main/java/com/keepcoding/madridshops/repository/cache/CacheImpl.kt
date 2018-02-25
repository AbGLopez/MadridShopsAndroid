package com.keepcoding.madridshops.repository.cache

import android.content.Context
import com.keepcoding.madridshops.repository.db.DBHelper
import com.keepcoding.madridshops.repository.db.build
import com.keepcoding.madridshops.repository.db.dao.ActivityDAO
import com.keepcoding.madridshops.repository.db.dao.ShopDAO
import com.keepcoding.madridshops.repository.model.ActivityEntity
import com.keepcoding.madridshops.repository.model.ShopEntity
import com.keepcoding.madridshops.repository.thread.DispatchOnMainThread
import java.lang.ref.WeakReference

internal class CacheImpl(context: Context) : Cache {

    val context = WeakReference<Context>(context)

    private fun cacheDBHelper(): DBHelper {
        return build(
                context.get()!!,
                "MadridShops.sqlite",
                1
        )
    }

    /**
     * SHOPS
     */
    override fun getAllShops(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        // Se crea un hilo de segundo plano
        Thread(Runnable {
            val shops = ShopDAO(cacheDBHelper()).query()
            DispatchOnMainThread(Runnable {
                if (shops.count() > 0) {
                    success(shops)
                } else {
                    error("No shops")
                }
            })
        }).run()
    }

    override fun saveAllShops(shops: List<ShopEntity>, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        // Se crea un hilo de segundo plano
        Thread(Runnable {
            try {
                shops.forEach {
                    ShopDAO(cacheDBHelper()).insert(it)
                }

                DispatchOnMainThread(Runnable { success() })
            } catch (e: Exception) {
                DispatchOnMainThread(Runnable {
                    error("Error inserting shops")
                })
            }
        }).run()

    }

    override fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        // Se crea un hilo de segundo plano
        Thread(Runnable {
            val successDeleting = ShopDAO(cacheDBHelper()).deleteAll()
            DispatchOnMainThread(Runnable {
                if (successDeleting) {
                    success()
                } else {
                    error("Error deleting")
                }
            })
        }).run()
    }

    /**
     * ACTIVITIES
     */

    override fun getAllActivities(success: (activities: List<ActivityEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        // Se crea un hilo de segundo plano
        Thread(Runnable {
            val activities = ActivityDAO(cacheDBHelper()).query()
            DispatchOnMainThread(Runnable {
                if (activities.count() > 0) {
                    success(activities)
                } else {
                    error("No activities")
                }
            })
        }).run()
    }

    override fun saveAllActivities(activities: List<ActivityEntity>, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        // Se crea un hilo de segundo plano
        Thread(Runnable {
            try {
                activities.forEach {
                    ActivityDAO(cacheDBHelper()).insert(it)
                }

                DispatchOnMainThread(Runnable { success() })
            } catch (e: Exception) {
                DispatchOnMainThread(Runnable {
                    error("Error inserting activities")
                })
            }
        }).run()

    }

    override fun deleteAllActivities(success: () -> Unit, error: (errorMessage: String) -> Unit) {

        // Se crea un hilo de segundo plano
        Thread(Runnable {
            val successDeleting = ActivityDAO(cacheDBHelper()).deleteAll()
            DispatchOnMainThread(Runnable {
                if (successDeleting) {
                    success()
                } else {
                    error("Error deleting")
                }
            })
        }).run()
    }

}