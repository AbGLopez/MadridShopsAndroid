package com.keepcoding.madridshops.domain.interactor.getallactivities

import android.content.Context
import android.util.Log
import com.keepcoding.madridshops.domain.interactor.ErrorCompletion
import com.keepcoding.madridshops.domain.interactor.SuccessCompletion
import com.keepcoding.madridshops.domain.model.Activities
import com.keepcoding.madridshops.domain.model.Activity
import com.keepcoding.madridshops.repository.Repository
import com.keepcoding.madridshops.repository.RepositoryImpl
import com.keepcoding.madridshops.repository.model.ActivityEntity
import java.lang.ref.WeakReference

class GetAllActivitiesInteractorImpl(context: Context) : GetAllActivitiesInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository: Repository = RepositoryImpl(weakContext.get()!!)

    override fun execute(success: SuccessCompletion<Activities>, error: ErrorCompletion) {
        repository.getAllActivities(success = {
            val activities : Activities = entityMapper(it)

            success.successCompletion(activities)

        }, error = {
            error(it)
        })
    }

    private fun entityMapper(list: List<ActivityEntity>): Activities {
        val tempList = ArrayList<Activity>()

        list.forEach {
            val activity = Activity(
                    it.id,
                    it.name,
                    it.description_en,
                    it.description_es,
                    parseStringToDouble(it.latitude),
                    parseStringToDouble(it.longitude),
                    it.img,
                    it.logo,
                    it.openingHours_en,
                    it.openingHours_es,
                    it.address,
                    it.telephone,
                    it.url)

            tempList.add(activity)
        }

        return Activities(tempList)
    }

    private fun parseStringToDouble(value: String): Double? {
        var coordinate: Double? = null

        val parsedString: String = value.replace(",", "").replace(" ", "")

        try {
            coordinate = parsedString.toDouble()
        } catch (e: Exception) {
            Log.d("PARSE ERROR", "💩 Error parsing to float: " + value)
        }

        return coordinate
    }
}