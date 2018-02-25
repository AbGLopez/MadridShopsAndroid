package com.keepcoding.madridshops.domain.model

import java.io.Serializable

/**
 * Activity: represents one Activities
 */
data class Activity(val id: Long,
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
        Activities(ArrayList<Activity>())
    }
}

/**
 * Métodos de shops
 */
data class Activities(val activities: MutableList<Activity>) : Aggregate<Activity> {
    override fun count(): Int {
        return activities.size
    }

    override fun all(): List<Activity> {
        return activities
    }

    override fun get(position: Int): Activity {
        return activities.get(position)
    }

    override fun add(element: Activity) {
        activities.add(element)
    }

    override fun delete(position: Int) {
        activities.removeAt(position)
    }

    override fun delete(element: Activity) {
        activities.remove(element)
    }


}
