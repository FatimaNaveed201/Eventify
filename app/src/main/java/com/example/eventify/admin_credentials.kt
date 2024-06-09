package com.example.eventify

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class admin_credentials : AppCompatActivity() {

    // Declare UI elements and variables
    private lateinit var addpfp: ImageButton
    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var proceed: ImageButton

    private var selectedPfpResId: Int? = null

    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    companion object {
        const val REQUEST_PFP = 1 //Request code for profile picture selection
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_credentials)

        addpfp = findViewById(R.id.addpfpButton)
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        proceed = findViewById(R.id.proceedButton)

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        // Set click listener for add profile picture button
        addpfp.setOnClickListener {
            // Start scale animation
            addpfp.startAnimation(scaleUp)
            addpfp.startAnimation(scaleDown)
            showPopup()
        }


        proceed.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || selectedPfpResId == null) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save admin data to shared preferences
            val sharedPreferences = getSharedPreferences("admin_data", MODE_PRIVATE)
            sharedPreferences.edit().apply {
                putString("name", name)
                putString("email", email)
                selectedPfpResId?.let { putInt("profilePicture", it) }
                apply()
            }

            val intent = Intent(this, admin_main::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Show popup to select profile picture
    private fun showPopup() {
        val intent = Intent(this, pfp_popup::class.java)
        startActivityForResult(intent, REQUEST_PFP)
    }

    // Handle result from profile picture selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PFP && resultCode == Activity.RESULT_OK) {
            selectedPfpResId = data?.getIntExtra("selectedPfp", R.drawable.addpfp) ?: R.drawable.addpfp
            addpfp.setImageResource(selectedPfpResId!!)
        }
    }
}
