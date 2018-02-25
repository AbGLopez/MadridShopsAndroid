package com.keepcoding.madridshops.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.domain.model.Activities
import com.keepcoding.madridshops.domain.model.Activity
import com.squareup.picasso.Picasso

class ActivityRecyclerViewAdapter(val activityList: Activities?) :
        RecyclerView.Adapter<ActivityRecyclerViewAdapter.ActivityListViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    override fun onBindViewHolder(holder: ActivityListViewHolder?, position: Int) {
        activityList?.let {
            holder?.bindActivity(activityList.get(position))
        }
    }

    override fun getItemCount(): Int {
        activityList?.let {
            return it.count()
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ActivityListViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.content_activity_list, parent, false)
        view.setOnClickListener(onClickListener)

        return ActivityListViewHolder(view)
    }


    inner class ActivityListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val activityImage = itemView.findViewById<ImageView>(R.id.activity_logo)
        val activityName = itemView.findViewById<TextView>(R.id.activity_name)
        val activityHours = itemView.findViewById<TextView>(R.id.activity_hours)

        fun bindActivity(activity: Activity) {
            activityName.text = activity.name

            Picasso.with(itemView.context).setIndicatorsEnabled(true)
            Picasso.with(itemView.context).isLoggingEnabled = true

            Picasso
                    .with(itemView.context)
                    .load(activity.logoURL)
                    .placeholder(android.R.drawable.ic_delete)
                    .into(activityImage)

            // TODO mostar formato hora en función idioma
            activityHours.text = activity.openingHours_es
        }
    }
}
