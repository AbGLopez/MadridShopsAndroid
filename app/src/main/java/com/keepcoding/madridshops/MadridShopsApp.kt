package com.keepcoding.madridshops

import android.support.multidex.MultiDexApplication
import android.util.Log
import com.keepcoding.madridshops.domain.interactor.ErrorCompletion
import com.keepcoding.madridshops.domain.interactor.SuccessCompletion
import com.keepcoding.madridshops.domain.interactor.getallshops.GetAllShopsInteractorImpl
import com.keepcoding.madridshops.domain.model.Shops

class MadridShopsApp : MultiDexApplication() {

    /*
    Se ejecuta cuando se lanza la aplicación
    */
    override fun onCreate() {
        super.onCreate()

        // Init code application wide

        Log.d("App", "onCreate")

        Log.d("App", BuildConfig.MADRID_SHOPS_SERVER_URL)

        val allShopsInteractor = GetAllShopsInteractorImpl(this)

        allShopsInteractor.execute(object : SuccessCompletion<Shops> {
            override fun successCompletion(element: Shops) {
                Log.d("Shops", "Count: " + element.count())

                element.shops.forEach{
                    Log.d("Shop", it.name)
                }
            }
        }, object : ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                Log.d("Error", errorMessage)
            }
        })

        /*
        DeleteAllShopsImpl(this).execute(success = {
            Log.d("Success", "Success")
        }, error = {
            Log.d("Error", "Error")
        })
        */
    }

    /*

    */
    override fun onLowMemory() {
        super.onLowMemory()


    }


}