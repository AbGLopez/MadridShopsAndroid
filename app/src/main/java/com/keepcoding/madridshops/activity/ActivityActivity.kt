package com.keepcoding.madridshops.activity

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.adapter.InfoActivityWindowAdapter
import com.keepcoding.madridshops.domain.interactor.ErrorCompletion
import com.keepcoding.madridshops.domain.interactor.SuccessCompletion
import com.keepcoding.madridshops.domain.interactor.getallactivities.GetAllActivitiesInteractor
import com.keepcoding.madridshops.domain.interactor.getallactivities.GetAllActivitiesInteractorImpl
import com.keepcoding.madridshops.domain.model.Activities
import com.keepcoding.madridshops.domain.model.Activity
import com.keepcoding.madridshops.fragment.ActivitiesListFragment
import com.keepcoding.madridshops.router.Router
import com.keepcoding.madridshops.utis.getActivityText
import kotlinx.android.synthetic.main.activity_activity.*
import kotlinx.android.synthetic.main.content_activity.*


class ActivityActivity : AppCompatActivity(), ActivitiesListFragment.OnShowActivityDetail {

    private var activitiesListFragment: ActivitiesListFragment? = null
    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity)
        setSupportActionBar(toolbar)

        // Configuración del MapFragment
        setupMap()
    }

    private fun setupMap() {

        progress_bar.visibility = View.VISIBLE

        val getAllActivitiesInteractor: GetAllActivitiesInteractor = GetAllActivitiesInteractorImpl(this)

        getAllActivitiesInteractor.execute(object : SuccessCompletion<Activities> {
            override fun successCompletion(activities: Activities) {
                initializeMap(activities)
                progress_bar.visibility = View.GONE

                setupList(activities)
            }

        }, object : ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                AlertDialog.Builder(this@ActivityActivity)
                        .setTitle("Error")
                        .setMessage("Conexion Error. Unable connect to server.")
                        .setPositiveButton("Retry?", { dialog, which ->
                            dialog.dismiss()
                            setupMap()
                        })
                        .setNegativeButton("Exit", { dialog, which ->
                            finish()
                        })
                        .show()

                progress_bar.visibility = View.GONE
            }
        })
    }

    private fun setupList(activities: Activities) {
        // Puntero al fragment del listado
        activitiesListFragment = supportFragmentManager.findFragmentById(R.id.activity_activity_list_fragment) as ActivitiesListFragment
        if (activities != null) {
            activitiesListFragment?.setActivities(activities)
        }
    }

    private fun initializeMap(activities: Activities) {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.activity_activity_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync({ mapa ->
            Log.d("SUCCESS", "HABEMUS MAPA")

            centerMapInPosition(mapa,40.416775, -3.703790)

            mapa.uiSettings.isRotateGesturesEnabled = false
            mapa.uiSettings.isZoomControlsEnabled = true

            showUserPosition(baseContext, mapa)

            mapa.setInfoWindowAdapter(InfoActivityWindowAdapter(this))

            map = mapa

            addAllPins(activities)
        })
    }

    fun centerMapInPosition(map: GoogleMap, latitude: Double, longitude: Double) {

        val coordinate = LatLng(latitude, longitude)

        // Genera la posicion de la camara
        val cameraPosition = CameraPosition.Builder()
                .target(coordinate)
                .zoom(14f)
                .build()

        // Coloca la camara a raiz de la posicion
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun showUserPosition(context: Context, map: GoogleMap) {

        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            // No han dado permisos para la localización
            ActivityCompat.requestPermissions(this,
                    arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
                    10)

            return
        } else {
            map.isMyLocationEnabled = true
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 10) {
            try {
                map?.isMyLocationEnabled = true
            } catch (e: SecurityException) {
                Log.d("PERMISSION ERROR", "💩 Error in permissions")
            }
        }
    }

    fun addAllPins(activities: Activities) {
        for (index in 0 until activities.count()){
            val activity = activities.get(index)

            // Se verifica que no se llama a posicionar un pin si algun valor no está informado
            if (activity.latitude != null && activity.longitude != null) {
                addPin(map!!, activity)
                //addPin(map!!, 40.416775, -3.703790, activity.name)
            }

            map?.setOnInfoWindowClickListener {
                if (it.tag is Activity) {
                    Router().navigateFromActivityActivityToActivityDetailActivity(this, it.tag as Activity)
                }
            }
        }
    }

    fun addPin(map: GoogleMap, activity: Activity) {
        map.addMarker(MarkerOptions()
                .position(LatLng(activity.latitude!!, activity.longitude!!))
                .title(activity.name)
                .snippet(getActivityText(activity, "openingHours")))
                .tag = activity
    }

    override fun showActivityDetail(activity: Activity) {
        Router().navigateFromActivityActivityToActivityDetailActivity(this, activity)
    }
}
