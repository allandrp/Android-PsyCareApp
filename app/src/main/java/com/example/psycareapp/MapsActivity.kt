package com.example.psycareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.psycareapp.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var lat = -34.0
    var lng = 151.0
    var isList = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.getBooleanExtra("psychologist", false)){
            lat = intent.getDoubleExtra("lat", 0.0)
            lng = intent.getDoubleExtra("lng", 0.0)
        }else{
            isList = true
        }

        binding.mapView.getMapAsync(this)
        binding.mapView.onCreate(savedInstanceState)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if(isList){
            Toast.makeText(this, "MASUK IS LIST", Toast.LENGTH_SHORT).show()
        }else{
            val coordinate = LatLng(lat, lng)
            mMap.addMarker(MarkerOptions().position(coordinate).title(intent.getStringExtra("name")))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                coordinate, 15f))
        }

    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}