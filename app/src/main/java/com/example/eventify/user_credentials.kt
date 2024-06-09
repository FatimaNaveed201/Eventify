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

class user_credentials : AppCompatActivity() {

    //declaring variables
    private lateinit var addpfp: ImageButton
    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var proceed: ImageButton

    private var selectedPfpResId: Int? = null

    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    companion object {
        const val REQUEST_PFP = 1 //request code for profile picture
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_credentials)

        addpfp = findViewById(R.id.addpfpButton)
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        proceed = findViewById(R.id.proceedButton)

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        addpfp.setOnClickListener {
            // Start scale animation
            addpfp.startAnimation(scaleUp)
            addpfp.startAnimation(scaleDown)
            showPopup()
        }

        //proceed button listener to check if all fields are filled to move on
        proceed.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || selectedPfpResId == null) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //shared prefs to store user data
            val sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)
            sharedPreferences.edit().apply {
                putString("name", name)
                putString("email", email)
                selectedPfpResId?.let { putInt("profilePicture", it) }
                apply()
            }

            val intent = Intent(this, user_main::class.java)
            startActivity(intent)
            finish()
        }
    }

    //showpopup for instructions
    private fun showPopup() {
        val intent = Intent(this, pfp_popup::class.java)
        startActivityForResult(intent, REQUEST_PFP)
    }

    //finalizing the selected pfp
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PFP && resultCode == Activity.RESULT_OK) {
            selectedPfpResId = data?.getIntExtra("selectedPfp", R.drawable.addpfp) ?: R.drawable.addpfp
            addpfp.setImageResource(selectedPfpResId!!)
        }
    }
}
