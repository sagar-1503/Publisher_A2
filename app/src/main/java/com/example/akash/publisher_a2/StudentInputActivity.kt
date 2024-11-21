package com.example.akash.publisher_a2


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StudentInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_input)

        val studentIdEditText = findViewById<EditText>(R.id.StudentInputEditText)
        val nextButton = findViewById<Button>(R.id.button)

        nextButton.setOnClickListener {
            val studentId = studentIdEditText.text.toString()

            if (validateStudentId(studentId)) {
                // Proceed to PublisherActivity if validation is successful
                val intent = Intent(this, PublisherActivity::class.java)
                startActivity(intent)
            } else {
                // Show a toast message if validation fails
                Toast.makeText(this, "Invalid Student ID. Please enter a valid ID.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateStudentId(studentId: String): Boolean {
        return try {
            val id = studentId.toLong()
            id in 810000000..819999999
        } catch (e: NumberFormatException) {
            false
        }
    }
}