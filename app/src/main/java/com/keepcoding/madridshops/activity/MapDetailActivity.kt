package com.keepcoding.madridshops.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.domain.model.Activity
import com.keepcoding.madridshops.tools.GOOGLE_MAP_URL
import com.keepcoding.madridshops.tools.INTENT_ACTIVITY_DETAIL
import com.keepcoding.madridshops.tools.getActivityText
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_map_detail.*

class MapDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_detail)

        val activity = intent.getSerializableExtra(INTENT_ACTIVITY_DETAIL) as Activity

        activity_name.text =  activity.name
        activity_description.text = getActivityText(activity, "description")
        activity_address.text = activity.address
        activity_hours.text = getActivityText(activity,"openingHours")

        Picasso
                .with(this)
                .load(activity.imageURL)
                .placeholder(android.R.drawable.ic_delete)
                .into(activity_image)

        val googleMapUrl = GOOGLE_MAP_URL + activity.latitude + "," + activity.longitude

        Picasso
                .with(this)
                .load(googleMapUrl)
                .placeholder(android.R.drawable.ic_delete)
                .into(activity_google_map)
    }
}