package com.example.eventify

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserTheatreEvents : AppCompatActivity() {

    //declare variables
    private lateinit var back: ImageButton
    private lateinit var laperle: ImageButton
    private lateinit var fontana: ImageButton
    private lateinit var pluma: ImageButton
    private lateinit var opera: ImageButton
    private lateinit var recentlyadded: ImageView

    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_theatre_events)

        // Retrieve intent extra
        val isOperaButtonVisible = intent.getBooleanExtra("isOperaButtonVisible", true)

        // Update visibility of Opera button
        val operaButton: ImageButton = findViewById(R.id.operaButton)
        val recentlyAdded: ImageView = findViewById(R.id.recentlyadded)

        operaButton.visibility = if (isOperaButtonVisible) View.VISIBLE else View.GONE
        recentlyAdded.visibility = if (isOperaButtonVisible) View.VISIBLE else View.GONE

        // Initialize views
        back = findViewById(R.id.backButton)
        laperle = findViewById(R.id.laperleButton)
        fontana = findViewById(R.id.fontanaButton)
        pluma = findViewById(R.id.plumaButton)
        opera = findViewById(R.id.operaButton)
        recentlyadded = findViewById(R.id.recentlyadded)

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        // Check if reset action is triggered
        val sharedPreferences = getSharedPreferences("reset_state", Context.MODE_PRIVATE)
        val isResetTriggered = sharedPreferences.getBoolean("is_reset_triggered", true)

        if (isResetTriggered) {
            // Reset visibility
            opera.visibility = View.GONE
            recentlyAdded.visibility = View.GONE
        }

        // Event details map
        val eventDetailsMap = mapOf(
            laperle to EventDetails(
                "La Perle Dubai",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/N800uababas?si=fy19Nxknna_Lr74L" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "La Perle is a mesmerizing live performance at Al Habtoor City, combining acrobatics, water effects, and stunning visuals. It’s a unique experience for you and your family that you won’t want to miss! Come for the experience, leave with the memories!",
                "Price : Aed 900",
                "Date : 11th June 2024",
                "laperle",
                "laperleticket"
            ),
            fontana to EventDetails(
                "Fontana Dubai",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/XZK-rvvw_VQ?si=gaULp-lZdeDi4hTk" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "The dynamic, immersive laser, light and water show, IMAGINE, is a popular attraction in Dubai Festival City Mall. And now there’s yet another reason to visit this fantastic venue: Fontana – the first-ever travelling water circus in the Middle East!",
                "Price : Aed 1,200",
                "Date : 8th July 2024",
                "fontana",
                "fontanaticket"
            ),
            pluma to EventDetails(
                "Pluma Dubai",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/u4liuOuOeQo?si=hhblzFG4Dd_eTNL4" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Pluma is not just a circus show. It’s a mesmerizing experience that combines acrobatics, 3D projection mapping, and stunning visual effects. The show introduces you to the captivating story of Pluma, a young girl with dreams of flight.",
                "Price : Aed 2,000",
                "Date : 25th July 2024",
                "pluma",
                "plumaticket"
            ),
            opera to EventDetails(
                "Dubai Opera",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/gO_4J3gQCbc?si=TyjP2eNTM7tdu3lV" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "The Dubai Opera is an annual theatric event which takes place on the 31st July each year. Indulge yourself into the dramatic and exciting world of theatrics with our out of the world performances and brilliant cast and workers. Bring your friends, or your family or come alone! Entertainment is guaranteed!",
                "Price : Aed 2,600",
                "Date : 31st July 2024",
                "opera",
                "operaticket"

            )
        )

        // Apply scaling animations and navigation to image buttons
        applyScaleAnimation(laperle, eventDetailsMap[laperle])
        applyScaleAnimation(fontana, eventDetailsMap[fontana])
        applyScaleAnimation(pluma, eventDetailsMap[pluma])
        applyScaleAnimation(opera, eventDetailsMap[opera])

        // Set back button functionality with animation
        back.setOnClickListener {
            it.startAnimation(scaleUp)
            it.startAnimation(scaleDown)
            onBackPressed()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun applyScaleAnimation(button: ImageButton, eventDetails: EventDetails?) {
        button.setOnClickListener {
            it.startAnimation(scaleUp)
            it.startAnimation(scaleDown)
            it.postDelayed({
                val intent = Intent(this, UserSelectedEvent::class.java).apply {
                    putExtra("eventHeading", eventDetails?.eventHeading)
                    putExtra("teaserEmbed", eventDetails?.teaser)
                    putExtra("description", eventDetails?.description)
                    putExtra("price", eventDetails?.price)
                    putExtra("date", eventDetails?.date)
                    putExtra("eventImage", eventDetails?.imageResourceName)
                    putExtra("ticketImageResourceName", eventDetails?.ticketImageResourceName) // Updated key
                }
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }, 100) // Delay to allow the animation to complete
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }


    // Function to save visibility state of Opera button in SharedPreferences
    private fun saveButtonVisibilityState(isVisible: Boolean) {
        val sharedPreferences = getSharedPreferences("button_visibility", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_opera_button_visible", isVisible)
        editor.apply()
    }

    // Function to retrieve visibility state of Opera button from SharedPreferences
    private fun getButtonVisibilityState(): Boolean {
        val sharedPreferences = getSharedPreferences("button_visibility", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("is_opera_button_visible", true) // Default to true if not found
    }
}
