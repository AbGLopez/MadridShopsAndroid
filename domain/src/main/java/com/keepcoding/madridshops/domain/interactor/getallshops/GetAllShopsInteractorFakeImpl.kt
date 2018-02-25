package com.keepcoding.madridshops.domain.interactor.getallshops

import com.keepcoding.madridshops.domain.interactor.ErrorClosure
import com.keepcoding.madridshops.domain.interactor.ErrorCompletion
import com.keepcoding.madridshops.domain.interactor.SuccessClosure
import com.keepcoding.madridshops.domain.interactor.SuccessCompletion
import com.keepcoding.madridshops.domain.model.Shop
import com.keepcoding.madridshops.domain.model.Shops
import java.util.*

class GetAllShopsInteractorFakeImpl: GetAllShopsInteractor {

    override fun execute(success: SuccessCompletion<Shops>, error: ErrorCompletion){
        var allOK = false

        // connect to the repository

        if (allOK) {
            val shops = createFakeListOfShops()

            success.successCompletion(shops)
        } else {
            error.errorCompletion("Error while accessing the Repository")
        }
    }

    // Se recibe un Lamda (código) y no devuelve nada
    fun execute(success: SuccessClosure, error: ErrorClosure) {
        var allOK = true

        // connect to the repository

        if (allOK) {
            val shops = createFakeListOfShops()

            success(shops)
        } else {
            error("Error while accessing the Repository")
        }
    }

    fun createFakeListOfShops(): Shops {
        val list = ArrayList<Shop>()

        for (index in 0..100) {
            val shop = Shop(index.toLong(),
                    name = "Shop " + index,
                    description_en = "",
                    description_es = "",
                    latitude = 1.0,
                    longitude = 1.0,
                    imageURL = "image",
                    logoURL = "logo",
                    openingHours_en = "Hours",
                    openingHours_es = "Hours",
                    telephone = "",
                    url = "",
                    address = "Address " + index)
            list.add(shop)
        }

        val shops = Shops(list)
        return shops

    }

}