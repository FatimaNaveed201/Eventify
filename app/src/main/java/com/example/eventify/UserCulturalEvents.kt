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

class UserCulturalEvents : AppCompatActivity() {

    private lateinit var back: ImageButton
    private lateinit var camelrace: ImageButton
    private lateinit var heritage: ImageButton
    private lateinit var dhafra: ImageButton
    private lateinit var marmoom: ImageButton

    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_cultural_events)

        val isMarmoomButtonVisible = intent.getBooleanExtra("isMarmoomButtonVisible", true)

        // Update visibility of Opera button
        val marmoomButton: ImageButton = findViewById(R.id.marmoomButton)
        val recentlyAdded: ImageView = findViewById(R.id.recentlyadded)

        marmoomButton.visibility = if (isMarmoomButtonVisible) View.VISIBLE else View.GONE
        recentlyAdded.visibility = if (isMarmoomButtonVisible) View.VISIBLE else View.GONE

        // Initialize views
        back = findViewById(R.id.backButton)
        camelrace = findViewById(R.id.camelraceButton)
        heritage = findViewById(R.id.heritageButton)
        dhafra = findViewById(R.id.dhafraButton)
        marmoom = findViewById(R.id.marmoomButton)

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        // Check if reset action is triggered
        val sharedPreferences = getSharedPreferences("reset_state", Context.MODE_PRIVATE)
        val isResetTriggered = sharedPreferences.getBoolean("is_reset_triggered", true)

        if (isResetTriggered) {
            // Reset visibility
            marmoom.visibility = View.GONE
            recentlyAdded.visibility = View.GONE

            // Reset the trigger so it doesn't execute again
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_reset_triggered", false)
            editor.apply()
        }

        // Event details map
        val eventDetailsMap = mapOf(
            camelrace to EventDetails(
                "Dubai Camel Race",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/6ll4-OiLn4E?si=tEYaI-eeYf6CVot9" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Experience the thrill of the traditional Dubai Camel Race and witness the beauty of these majestic creatures in action.",
                "Price : Aed 45",
                "Date : 2nd June 2024",
                "camelrace",
                "camelraceticket"
            ),
            heritage to EventDetails(
                "Dubai Heritage Village",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/ZAXLaFGCVh8?si=cWevKB0MIhgBOpG0" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Immerse yourself in the rich cultural heritage of Dubai at the Heritage Village, where you can explore traditional crafts, cuisine, and architecture.",
                "Price : Aed 15",
                "Date : 17th June 2024",
                "heritage",
                "heritageticket"
            ),
            dhafra to EventDetails(
                "Al Dhafra Festival",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/7mjQiEulAdo?si=Ch5jXDRD3xTN0Zzu" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Celebrate Emirati culture and heritage at the Al Dhafra Festival, featuring traditional performances, camel races, and cultural exhibitions.",
                "Price : Aed 100",
                "Date : 1st July 2024",
                "dhafra",
                "dhafraticket"
            ),
            marmoom to EventDetails(
                "Marmoom Heritage Festival",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/8U3J1ca6QaY?si=VLUYJQ_7MCR0WUxM" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Experience the essence of Emirati heritage at the Marmoom Heritage Festival, featuring traditional arts, camel racing, and cultural performances.",
                "Price : Aed 50",
                "Date : 30th July 2024",
                "marmoom",
                "marmoomticket"
            )
        )

        // Apply scaling animations and navigation to image buttons
        applyScaleAnimation(camelrace, eventDetailsMap[camelrace])
        applyScaleAnimation(heritage, eventDetailsMap[heritage])
        applyScaleAnimation(dhafra, eventDetailsMap[dhafra])
        applyScaleAnimation(marmoom, eventDetailsMap[marmoom])

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

    //intenting the information into the data class and applying animation
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
}
