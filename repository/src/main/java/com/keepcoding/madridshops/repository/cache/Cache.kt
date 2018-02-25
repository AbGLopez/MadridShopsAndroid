package com.keepcoding.madridshops.repository.cache

import com.keepcoding.madridshops.repository.model.ActivityEntity
import com.keepcoding.madridshops.repository.model.ShopEntity

internal interface Cache {
    fun deleteAllShops (success: () -> Unit, error: (errorMessage: String) -> Unit)

    fun getAllShops (success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit)
    fun saveAllShops (shops: List<ShopEntity>, success: () -> Unit, error: (errorMessage: String) -> Unit)


    fun deleteAllActivities (success: () -> Unit, error: (errorMessage: String) -> Unit)

    fun getAllActivities (success: (activities: List<ActivityEntity>) -> Unit, error: (errorMessage: String) -> Unit)
    fun saveAllActivities (activities: List<ActivityEntity>, success: () -> Unit, error: (errorMessage: String) -> Unit)
}