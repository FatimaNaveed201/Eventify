package com.example.eventify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var userlogin: ImageButton
    private lateinit var adminlogin: ImageButton
    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences("ticket_data", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        //view variables
        userlogin = findViewById(R.id.userloginButton)
        adminlogin = findViewById(R.id.adminloginButton)

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set click listeners for the buttons
        userlogin.setOnClickListener {

            userlogin.startAnimation(scaleUp)
            userlogin.startAnimation(scaleDown)

            val intent = Intent(this, user_credentials::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        adminlogin.setOnClickListener {

            adminlogin.startAnimation(scaleUp)
            adminlogin.startAnimation(scaleDown)

            val intent = Intent(this, admin_credentials::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }
}