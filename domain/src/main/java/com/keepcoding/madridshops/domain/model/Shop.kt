package com.keepcoding.madridshops.domain.model

import java.io.Serializable

/**
 * Shop: represents one Shops
 */
data class Shop(val id: Long,
                val name: String,
                val description_en: String,
                val description_es: String,
                val latitude: Double?,
                val longitude: Double?,
                val imageURL: String,
                val logoURL: String,
                val openingHours_en: String,
                val openingHours_es: String,
                val address: String,
                val telephone: String,
                val url: String): Serializable {
    init {
        Shops(ArrayList<Shop>())
    }
}

/**
 * Shops Methods
 */
data class Shops(val shops: MutableList<Shop>) : Aggregate<Shop> {
    override fun count(): Int {
        return shops.size
    }

    override fun all(): List<Shop> {
        return shops
    }

    override fun get(position: Int): Shop {
        return shops.get(position)
    }

    override fun add(element: Shop) {
        shops.add(element)
    }

    override fun delete(position: Int) {
        shops.removeAt(position)
    }

    override fun delete(element: Shop) {
        shops.remove(element)
    }


}
