
package com.example.akash.publisher_a2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

//import com.hivemq.client.mqtt.Mqtt5BlockingClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nextButton: Button = findViewById(R.id.nextButton)
        nextButton.setOnClickListener {
            // Navigate to PermissionActivity
            val intent = Intent(this, PermissionActivity::class.java)
            startActivity(intent)
        }
    }
}
