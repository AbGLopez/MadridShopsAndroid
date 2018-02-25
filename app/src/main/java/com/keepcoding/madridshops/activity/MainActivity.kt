package com.keepcoding.madridshops.activity

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.domain.interactor.internetstatus.InternetStatusInteractorImpl
import com.keepcoding.madridshops.router.Router
import com.keepcoding.madridshops.tools.getAlertButtonText
import com.keepcoding.madridshops.tools.getButtonText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disableButtons()
        checkInternetStatus()
    }

    private fun checkInternetStatus() {
        InternetStatusInteractorImpl().execute(this, success = {

            enableButtons()
            setupButtons()

        }, error = {
            AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(it)
                    .setPositiveButton(getAlertButtonText("setPositiveButton"), { dialog, which ->
                        dialog.dismiss()
                        checkInternetStatus()
                    })
                    .setNegativeButton(getAlertButtonText("setNegativeButton"), { dialog, which ->
                        finish()
                    })
                    .show()
        })
    }

    private fun disableButtons() {
        shop_button.visibility = View.INVISIBLE
    }

    private fun enableButtons() {
        shop_button.visibility = View.VISIBLE
    }

    private fun setupButtons() {
        shop_button.text = getButtonText("Shop")

        shop_button.setOnClickListener { Router().navigateFromMainActivityToShopActivity(this) }
    }
}
