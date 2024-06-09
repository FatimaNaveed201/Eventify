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

class WelcomeScreen : AppCompatActivity() {

    //declaring variables
    private lateinit var proceed: ImageButton
    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome_screen)

        proceed = findViewById(R.id.welcomeproceed)

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        // Clear SharedPreferences for button visibility values
        clearButtonVisibilityState()

        // Setting the click listener on the proceed button
        proceed.setOnClickListener {
            // Start scale animation
            proceed.startAnimation(scaleUp)
            proceed.startAnimation(scaleDown)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun clearButtonVisibilityState() {
        val sharedPreferences = getSharedPreferences("button_visibility", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear() // Clears all keys in this SharedPreferences
        editor.apply()
    }

}