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
import com.adapter.madridshops.adapter.InfoShopWindowAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.keepcoding.madridshops.R


// import com.keepcoding.madridshops.adapter.InfoShopWindowAdapter
import com.keepcoding.madridshops.domain.interactor.ErrorCompletion
import com.keepcoding.madridshops.domain.interactor.SuccessCompletion
import com.keepcoding.madridshops.domain.interactor.getallshops.GetAllShopsInteractor
import com.keepcoding.madridshops.domain.interactor.getallshops.GetAllShopsInteractorImpl
import com.keepcoding.madridshops.domain.model.Shop
import com.keepcoding.madridshops.domain.model.Shops
import com.keepcoding.madridshops.fragment.ShopsListFragment
import com.keepcoding.madridshops.router.Router
import com.keepcoding.madridshops.utis.getShopText
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.content_shop.*


class ShopActivity : AppCompatActivity(), ShopsListFragment.OnShowShopDetail {

    private var shopsListFragment: ShopsListFragment? = null
    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        setSupportActionBar(toolbar)

        // Configuración del MapFragment
        setupMap()
    }

    private fun setupMap() {

        progress_bar.visibility = View.VISIBLE

        val getAllShopsInteractor: GetAllShopsInteractor = GetAllShopsInteractorImpl(this)

        getAllShopsInteractor.execute(object : SuccessCompletion<Shops> {
            override fun successCompletion(shops: Shops) {
                initializeMap(shops)
                progress_bar.visibility = View.GONE

                setupList(shops)
            }

        }, object : ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                AlertDialog.Builder(this@ShopActivity)
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

    private fun setupList(shops: Shops) {
        // Puntero al fragment del listado
        shopsListFragment = supportFragmentManager.findFragmentById(R.id.activity_shop_list_fragment) as ShopsListFragment
        if (shops != null) {
            shopsListFragment?.setShops(shops)
        }
    }

    private fun initializeMap(shops: Shops) {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.activity_shop_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync({ mapa ->
            Log.d("SUCCESS", "HABEMUS MAPA")

            centerMapInPosition(mapa,40.416775, -3.703790)

            mapa.uiSettings.isRotateGesturesEnabled = false
            mapa.uiSettings.isZoomControlsEnabled = true

            showUserPosition(baseContext, mapa)

            mapa.setInfoWindowAdapter(InfoShopWindowAdapter(this))

            map = mapa

            addAllPins(shops)
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

    fun addAllPins(shops: Shops) {
        for (index in 0 until shops.count()){
            val shop = shops.get(index)

            // Se verifica que no se llama a posicionar un pin si algun valor no está informado
            if (shop.latitude != null && shop.longitude != null) {
                addPin(map!!, shop)
                //addPin(map!!, 40.416775, -3.703790, shop.name)
            }

            map?.setOnInfoWindowClickListener {
                if (it.tag is Shop) {
                    Router().navigateFromShopActivityToShopDetailActivity(this, it.tag as Shop)
                }
            }
        }
    }

    fun addPin(map: GoogleMap, shop: Shop) {
        map.addMarker(MarkerOptions()
                .position(LatLng(shop.latitude!!, shop.longitude!!))
                .title(shop.name)
                .snippet(getShopText(shop, "openingHours")))
                .tag = shop
    }

    override fun showShopDetail(shop: Shop) {
        Router().navigateFromShopActivityToShopDetailActivity(this, shop)
    }
}
