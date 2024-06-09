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

class UserWorkshopsEvents : AppCompatActivity() {

    // declaring variables
    private lateinit var back: ImageButton
    private lateinit var photography: ImageButton
    private lateinit var pottery: ImageButton
    private lateinit var coding: ImageButton
    private lateinit var painting: ImageButton
    private lateinit var publicspeaking: ImageButton

    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_workshops_events)

        // Retrieve intent extra
        val isPublicSpeakingButtonVisible = intent.getBooleanExtra("isPublicSpeakingButtonVisible", true)

        // Update visibility of Opera button
        val publicspeakingButton: ImageButton = findViewById(R.id.publicspeakingButton)
        val recentlyAdded: ImageView = findViewById(R.id.recentlyadded)

        publicspeakingButton.visibility = if (isPublicSpeakingButtonVisible) View.VISIBLE else View.GONE
        recentlyAdded.visibility = if (isPublicSpeakingButtonVisible) View.VISIBLE else View.GONE

        // Initialize views
        back = findViewById(R.id.backButton)
        photography = findViewById(R.id.photographyButton)
        pottery = findViewById(R.id.potteryButton)
        coding = findViewById(R.id.codingButton)
        painting = findViewById(R.id.paintingButton)
        publicspeaking = findViewById(R.id.publicspeakingButton)

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        // Check if reset action is triggered
        val sharedPreferences = getSharedPreferences("reset_state", Context.MODE_PRIVATE)
        val isResetTriggered = sharedPreferences.getBoolean("is_reset_triggered", true)

        if (isResetTriggered) {
            // Reset visibility
            publicspeaking.visibility = View.GONE
            recentlyAdded.visibility = View.GONE

            // Reset the trigger so it doesn't execute again
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_reset_triggered", false)
            editor.apply()
        }

        // Event details map
        val eventDetailsMap = mapOf(
            photography to EventDetails(
                "Cinematic Photography",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/6PgRZp77hC8?si=4RDZjClPiNkmSLeH" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Learn the art of cinematic photography and capture breathtaking moments with this workshop. Explore techniques, composition, and lighting to create stunning visual stories.",
                "Price : Aed 350",
                "Date : 21st June 2024",
                "photography",
                "photographyticket"
            ),
            pottery to EventDetails(
                "Pottery & Ceramics",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/XbgQyoma9cY?si=0gGMCabCe4Xh-JRJ" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Discover the art of pottery and ceramics in this hands-on workshop. Get creative with clay, learn various techniques, and craft your own unique pieces under expert guidance.",
                "Price : Aed 75",
                "Date : 27th June 2024",
                "pottery",
                "potteryticket"
            ),
            coding to EventDetails(
                "Coding Bootcamp",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/uPGJoDXep78?si=wjkI42qKI61Pq3KF" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Join this intensive coding bootcamp and dive into the world of programming. Learn essential coding skills, build projects, and kickstart your journey in software development.",
                "Price : Aed 450",
                "Date : 5th July 2024",
                "coding",
                "codingticket"
            ),
            painting to EventDetails(
                "Painting Workshop",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/-NxOfvgvOPQ?si=XIi38ZZrekI5G1_V" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Unleash your creativity in this painting workshop. Learn various painting techniques, experiment with colors, and create your own masterpiece under expert guidance.",
                "Price : Aed 50",
                "Date : 11th July 2024",
                "painting",
                "paintingticket"
            ),
            publicspeaking to EventDetails(
                "Public Speaking Workshop",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/Vn72qSmNTzM?si=rvRrnM-Acz5HLiJ9" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Join us for an engaging Public Speaking Workshop designed to help you improve your speaking skills, build confidence, and learn effective communication techniques. This workshop will cover various aspects of public speaking, including speech preparation, audience engagement, body language, and overcoming stage fright. Whether you are a beginner or looking to refine your skills, this workshop offers valuable insights and practical tips to become a more effective speaker.",
                "Price : Aed 100",
                "Date : 31st July 2024",
                "publicspeaking",
                "publicspeakingticket"
            )
        )

        // Apply scaling animations and navigation to image buttons
        applyScaleAnimation(photography, eventDetailsMap[photography])
        applyScaleAnimation(pottery, eventDetailsMap[pottery])
        applyScaleAnimation(coding, eventDetailsMap[coding])
        applyScaleAnimation(painting, eventDetailsMap[painting])
        applyScaleAnimation(publicspeaking, eventDetailsMap[publicspeaking])

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
}
