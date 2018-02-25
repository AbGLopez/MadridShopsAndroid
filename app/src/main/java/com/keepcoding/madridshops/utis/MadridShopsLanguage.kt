package com.keepcoding.madridshops.utis

import com.keepcoding.madridshops.domain.model.Activity
import com.keepcoding.madridshops.domain.model.Shop
import java.util.*


private fun getLanguage(): String {
    return Locale.getDefault().language
}

fun getAlertButtonText(option: String): String {
    var result = ""

    if (option == "setPositiveButton") {
        when (getLanguage()) {
            "es" -> result = "¿Reintentar?"
            else -> result = "Retry?"
        }
    } else if (option == "setNegativeButton") {
        when (getLanguage()) {
            "es" -> result = "Salir"
            else -> result = "Exit"
        }
    }

    return result
}

fun getButtonText(option: String): String {
    var result = ""

    if (option == "Shop") {
        when (getLanguage()) {
            "es" -> result = "Tiendas"
            else -> result = "Shops"
        }
    } else if (option == "Activity") {
        when (getLanguage()) {
            "es" -> result = "Actividades"
            else -> result = "Activities"
        }
    }
    return result
}

fun getShopText(shop: Shop, option: String): String {
    var result = ""

    if (option == "description") {
        when (getLanguage()) {
            "es" -> result = shop.description_es
            else -> result = shop.description_en
        }
    } else if (option == "openingHours") {
        when (getLanguage()) {
            "es" -> result = shop.openingHours_es
            else -> result = shop.openingHours_en
        }
    }


    return result
}

fun getActivityText(activity: Activity, option: String): String {
    var result = ""

    if (option == "description") {
        when (getLanguage()) {
            "es" -> result = activity.description_es
            else -> result = activity.description_en
        }
    } else if (option == "openingHours") {
        when (getLanguage()) {
            "es" -> result = activity.openingHours_es
            else -> result = activity.openingHours_en
        }
    }


    return result
}

