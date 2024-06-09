package com.example.eventify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashScreen : AppCompatActivity() {

    private lateinit var motionLayout : MotionLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        motionLayout = findViewById(R.id.splashlayout)
        motionLayout.startLayoutAnimation()

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                startActivity(Intent(this@SplashScreen, WelcomeScreen::class.java))
                finish()
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }

        })

        // Reset changes made in admin_main
        resetAdminMainChanges()

        clearAdminPageSharedPreferences()

        // Clear the boolean value that determines the visibility of the Opera button
        clearBooleanValue()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splashlayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun clearAdminPageSharedPreferences() {
        val sharedPreferences = getSharedPreferences("admin_page", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // Exclude admin name and profile picture prefs
        editor.remove("name")
        editor.remove("profilePicture")
        // Clear all other prefs
        editor.clear()
        editor.apply()
    }


    private fun resetAdminMainChanges() {
        val sharedPreferences = getSharedPreferences("button_visibility", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear() // Clear all stored visibility states
        editor.apply()
    }

    private fun clearBooleanValue() {
        // Use SharedPreferences to clear the boolean value
        val sharedPreferences = getSharedPreferences("button_visibility", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("is_opera_button_visible") // Remove the boolean value
        editor.apply()
    }

}