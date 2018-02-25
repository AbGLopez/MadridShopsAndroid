package com.keepcoding.madridshops.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.keepcoding.madridshops.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_picasso.*

class PicassoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picasso)

        Picasso.with(this)
                .setIndicatorsEnabled(true)
        Picasso.with(this)
                .isLoggingEnabled = true

        Picasso.with(this)
                .load("https://heroichollywood.b-cdn.net/wp-content/uploads/2017/06/Spider-Man-Game.jpg?x42694")
                .placeholder(android.R.drawable.ic_delete)
                .into(img1)

        Picasso.with(this)
                .load("https://orig00.deviantart.net/6340/f/2010/272/a/e/doctor_strange_by_cinar-d2zq6hr.jpg")
                .placeholder(android.R.drawable.ic_btn_speak_now)
                .into(img2)

        Picasso.with(this)
                .load("https://static.comicvine.com/uploads/screen_kubrick/0/6063/5078196-tokyo.jpg")
                .placeholder(android.R.drawable.ic_dialog_alert)
                .into(img3)
    }
}
