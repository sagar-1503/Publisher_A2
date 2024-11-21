//package com.example.akash.publisher_a2
//
//
//import android.Manifest
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.hardware.Sensor
//import android.hardware.SensorEvent
//import android.hardware.SensorEventListener
//import android.hardware.SensorManager
//import android.net.Uri
//import android.os.Build.VERSION.SDK_INT
//import android.provider.Settings
//import android.view.View
//import androidx.activity.enableEdgeToEdge
//import androidx.core.app.ActivityCompat
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import android.os.Bundle
//import android.util.Log
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//
//
//
//
//
//class   PublisherActivity : AppCompatActivity(), SensorEventListener {
//
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_publisher)  // Set the layout for PublisherActivity
////    }
//
//
//    // Add the following two lines
//    private lateinit var sensorManager: SensorManager
//    private var proximity: Sensor? = null
//
//    private var isEnabled: Boolean = false
//
//
//    public override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_publisher)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        // Add the lines below
//
//        // Get an instance of the sensor service, and use that to get an instance of
//        // the proximity sensor.
//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
//    }
//
//
//
//    override fun onSensorChanged(event: SensorEvent?) {
//        if (event == null) return
//        if (event.sensor.type != Sensor.TYPE_PROXIMITY) return
//
//        val distanceToNearestObject = event.values[0]
//        val textView = findViewById<TextView>(R.id.tv_sensor)
//
//        Log.e("SENSOR", "DISTANCE = $distanceToNearestObject")
//        if (distanceToNearestObject < event.sensor.maximumRange){
//            textView.text = "The phone is near an object"
//        } else {
//            textView.text = "The phone is not near an object"
//        }
//    }
//
//
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//        Log.e("SENSOR", "Accuracy changed")
//    }
//
//
//
//
//    /// creating functions to enable and disable sensor
//    fun enableSensor(view: View?){
//        if (isEnabled){
//            Toast.makeText(this, "Sensor already enabled", Toast.LENGTH_SHORT).show()
//            return
//        }
//        proximity?.also { proximity ->
//            sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL)
//        }
//    }
//
//    //// function to disable the sensor
//    fun disableSensor(view: View?){
//        if (!isEnabled){
//            Toast.makeText(this, "Sensor already disabled", Toast.LENGTH_SHORT).show()
//            return;
//        }
//        sensorManager.unregisterListener(this)
//        isEnabled = false
//        findViewById<TextView>(R.id.tv_sensor).text = "The phone is not receiving data from the proximity sensor"
//    }
//
//
//
//}
/////********************************************************************************************


/*
package com.example.akash.publisher_a2

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PublisherActivity : AppCompatActivity(), LocationListener {

    private lateinit var locationManager: LocationManager
    private var isLocationEnabled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publisher)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
        if (SDK_INT >= 30) {
            window.setDecorFitsSystemWindows(false)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize LocationManager
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
    }

    // Function to enable location updates
    fun enableLocation(view: View?) {
        if (isLocationEnabled) {
            Toast.makeText(this, "Location already enabled", Toast.LENGTH_SHORT).show()
            return
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
            return
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            2000, // Minimum time interval between updates (2 seconds)
            10f,  // Minimum distance between updates (10 meters)
            this
        )
        isLocationEnabled = true
        Toast.makeText(this, "Location enabled", Toast.LENGTH_SHORT).show()
    }

    // Function to disable location updates
    fun disableLocation(view: View?) {
        if (!isLocationEnabled) {
            Toast.makeText(this, "Location already disabled", Toast.LENGTH_SHORT).show()
            return
        }
        locationManager.removeUpdates(this)
        isLocationEnabled = false
        findViewById<TextView>(R.id.tv_sensor).text = "Location updates are disabled."
    }

    override fun onLocationChanged(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        val textView = findViewById<TextView>(R.id.tv_sensor)
        textView.text = "Location: \nLatitude: $latitude\nLongitude: $longitude"
        Log.d("LOCATION", "Lat: $latitude, Lon: $longitude")
    }

    override fun onProviderEnabled(provider: String) {
        Log.d("LOCATION", "Provider enabled: $provider")
    }

    override fun onProviderDisabled(provider: String) {
        Log.d("LOCATION", "Provider disabled: $provider")
        val textView = findViewById<TextView>(R.id.tv_sensor)
        textView.text = "GPS is disabled. Enable it to receive location updates."
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.d("LOCATION", "Status changed: $provider, Status: $status")
    }
}

*/

/////###########################################

/*4th version

package com.example.akash.publisher_a2

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class PublisherActivity : AppCompatActivity(), LocationListener {

    private lateinit var locationManager: LocationManager
    private var isLocationEnabled: Boolean = false

    private lateinit var liveLocationTextView: TextView // New TextView for live location updates

    private var client: Mqtt5BlockingClient? = null


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publisher)

        // Initialize LocationManager
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        // Find the TextView for live location updates
        liveLocationTextView = findViewById(R.id.tv_live_location


        //
                //Add the lines below
         client = Mqtt5Client.builder()
            .identifier(UUID.randomUUID().toString())
            .serverHost("broker.sundaebytestt.com")
            .serverPort(1883)
            .build()
            .toBlocking()
    }

    fun enableLocation(view: View?) {
        if (isLocationEnabled) {
            Toast.makeText(this, "Location already enabled", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if permissions are granted
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permissions
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        // Start receiving location updates
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            2000, // Minimum time interval between updates (2 seconds)
            10f,  // Minimum distance between updates (10 meters)
            this
        )
        isLocationEnabled = true
        Toast.makeText(this, "Location enabled", Toast.LENGTH_SHORT).show()
    }

    fun disableLocation(view: View?) {
        if (!isLocationEnabled) {
            Toast.makeText(this, "Location already disabled", Toast.LENGTH_SHORT).show()
            return
        }

        // Stop receiving location updates
        locationManager.removeUpdates(this)
        isLocationEnabled = false
        liveLocationTextView.text = "Location updates are disabled."
    }

    override fun onLocationChanged(location: Location) {
        // Update the new TextView with current latitude and longitude
        val latitude = location.latitude
        val longitude = location.longitude
        liveLocationTextView.text = "Live Location:\nLatitude: $latitude\nLongitude: $longitude"

        Log.d("LOCATION", "Lat: $latitude, Lon: $longitude")
    }

    override fun onProviderEnabled(provider: String) {
        Toast.makeText(this, "GPS Enabled", Toast.LENGTH_SHORT).show()
    }

    override fun onProviderDisabled(provider: String) {
        Toast.makeText(this, "GPS Disabled. Enable it to receive location updates.", Toast.LENGTH_LONG).show()
        liveLocationTextView.text = "GPS is disabled."
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        // Handle location provider status change
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Permission granted. Enable location again.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied. Cannot access location.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
*///4th version


//////5th version below $$$$$$----$$$$$$$$$-------$$$$$$_$_$_$_$_$_$_$_$_$_$__

package com.example.akash.publisher_a2

//import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.util.UUID
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client



//private var client: Mqtt5BlockingClient? = null

class PublisherActivity : AppCompatActivity(), LocationListener {

    private lateinit var locationManager: LocationManager
    private var isLocationEnabled: Boolean = false

    private lateinit var liveLocationTextView: TextView // New TextView for live location updates







    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
        private const val MQTT_TOPIC = "assignment/location"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        var client: Mqtt5BlockingClient? = null // MQTT client instance


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publisher)

        // Initialize LocationManager
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        // Find the TextView for live location updates
        liveLocationTextView = findViewById(R.id.tv_live_location)

        // Initialize MQTT client
        client = Mqtt5Client.builder()
            .identifier(UUID.randomUUID().toString())
            .serverHost("broker-816036149.sundaebytestt.com")
            //.serverHost("broker-816036149.sundaebytestt.com")
            .serverPort(1883)
            .build()
            .toBlocking()

    }





    fun enableLocation(view: View?) {
        if (isLocationEnabled) {
            Toast.makeText(this, "Location already enabled", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if permissions are granted
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permissions
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        // Connect to the MQTT broker
        try {
            client?.connect()
        } catch (e: Exception) {
            Toast.makeText(this, "Error connecting to broker: ${e.message}", Toast.LENGTH_SHORT).show()
            return
        }

        // Start receiving location updates
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            2000, // Minimum time interval between updates (2 seconds)
            10f,  // Minimum distance between updates (10 meters)
            this
        )
        isLocationEnabled = true
        Toast.makeText(this, "Location enabled and connected to broker", Toast.LENGTH_SHORT).show()
    }

    fun disableLocation(view: View?) {
        if (!isLocationEnabled) {
            Toast.makeText(this, "Location already disabled", Toast.LENGTH_SHORT).show()
            return
        }

        // Stop receiving location updates
        locationManager.removeUpdates(this)
        isLocationEnabled = false

        // Disconnect from the MQTT broker
        try {
            client?.disconnect()
            Toast.makeText(this, "Disconnected from broker", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error disconnecting from broker: ${e.message}", Toast.LENGTH_SHORT).show()
        }

        liveLocationTextView.text = "Location updates are disabled."
    }

    override fun onLocationChanged(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude

        // Update the TextView with current latitude and longitude
        liveLocationTextView.text = "Live Location:\nLatitude: $latitude\nLongitude: $longitude"

        // Publish the location to the MQTT broker
        try {
            val payload = "Latitude: $latitude, Longitude: $longitude"
            client?.publishWith()
                ?.topic(MQTT_TOPIC)
                ?.payload(payload.toByteArray())
                ?.send()
            Log.d("MQTT", "Published location: $payload")
        } catch (e: Exception) {
            Log.e("MQTT", "Error publishing location: ${e.message}")
            Toast.makeText(this, "Error publishing location: ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onProviderEnabled(provider: String) {
        Toast.makeText(this, "GPS Enabled", Toast.LENGTH_SHORT).show()
    }

    override fun onProviderDisabled(provider: String) {
        Toast.makeText(this, "GPS Disabled. Enable it to receive location updates.", Toast.LENGTH_LONG).show()
        liveLocationTextView.text = "GPS is disabled."
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        // Handle location provider status change
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Permission granted. Enable location again.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied. Cannot access location.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}






