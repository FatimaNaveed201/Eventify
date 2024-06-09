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

class UserSportsEvents : AppCompatActivity() {

    // declaring variables
    private lateinit var back: ImageButton
    private lateinit var fitnesschallenge: ImageButton
    private lateinit var dubairun: ImageButton
    private lateinit var football: ImageButton
    private lateinit var cricketleague: ImageButton
    private lateinit var taekwondo: ImageButton

    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_sports_events)

        // Retrieve intent extra
        val isTaekwondoButtonVisible = intent.getBooleanExtra("isTaekwondoButtonVisible", true)

        // Update visibility of Opera button
        val taekwondoButton: ImageButton = findViewById(R.id.taekwondoButton)
        val recentlyAdded: ImageView = findViewById(R.id.recentlyadded)

        taekwondoButton.visibility = if (isTaekwondoButtonVisible) View.VISIBLE else View.GONE
        recentlyAdded.visibility = if (isTaekwondoButtonVisible) View.VISIBLE else View.GONE

        // Initialize views
        back = findViewById(R.id.backButton)
        fitnesschallenge = findViewById(R.id.fitnesschallengeButton)
        dubairun = findViewById(R.id.dubairunButton)
        football = findViewById(R.id.footballButton)
        cricketleague = findViewById(R.id.cricketleagueButton)
        taekwondo = findViewById(R.id.taekwondoButton)

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        // Check if reset action is triggered
        val sharedPreferences = getSharedPreferences("reset_state", Context.MODE_PRIVATE)
        val isResetTriggered = sharedPreferences.getBoolean("is_reset_triggered", true)

        if (isResetTriggered) {
            // Reset visibility
            taekwondo.visibility = View.GONE
            recentlyAdded.visibility = View.GONE

            // Reset the trigger so it doesn't execute again
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_reset_triggered", false)
            editor.apply()
        }

        // Event details map
        val eventDetailsMap = mapOf(
            fitnesschallenge to EventDetails(
                "Dubai Fitness Challenge",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/3jhCChY09iU?si=GxjvylU6oz_To3aD" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Dubai Fitness Challenge, an initiative of His Highness Sheikh Hamdan bin Mohammed bin Rashid Al Maktoum, Crown Prince of Dubai and Chairman of the Executive Council of Dubai, is a month-long celebration of wellness. Dubai Fitness challenge offers a packed schedule of fitness-related events across the city including workouts, exciting running, cycling, etc and wellness-focused entertainment.",
                "Price : Aed 50",
                "Date : 9th June 2024",
                "fitnesschallenge",
                "fitnesschallengeticket"
            ),
            dubairun to EventDetails(
                "Dubai Run",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/A_V397bYoik?si=G59NvQXHO3NI_wj8" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "The fifth edition of Dubai Run, presented by Mai Dubai, our largest yet was led by Sheikh Hamdan bin Mohammed bin Rashid Al Maktoum, Dubai Crown Prince and Chairman of The Executive Council of Dubai. This monumental event brings together 226,000 participants for the world's largest community fun run.",
                "Price : Aed 15",
                "Date : 10th June 2024",
                "dubairun",
                "dubairunticket"
            ),
            football to EventDetails(
                "Dubai Football League",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/cfjh0Mw9_mQ?si=M_N96cZyBy45Cru4" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Are you a sports enthusist? Welcome to Dubai Football League, where we encourage thrilling and competitiveness with our next level energy. Book your tickets today and become a part of one of Dubai's largest and grandest sports league. Watch as 12 teams compete against each other to find the grand winner.",
                "Price : Aed 600",
                "Date : 21st July 2024",
                "football",
                "footballticket"
            ),
            cricketleague to EventDetails(
                "Dubai Cricket League",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/8L3QSt6f3dM?si=W5L1UCEkw5E8v1rl" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Are you a sports enthusist? Welcome to Dubai Cricket League, where we encourage thrilling and competitiveness with our next level energy. Book your tickets today and become a part of one of Dubai's largest and grandest sports league. Watch as 12 teams compete against each other to find the grand winner.",
                "Price : Aed 250",
                "Date : 31st July 2024",
                "cricketleague",
                "cricketleagueticket"
            ),
            taekwondo to EventDetails(
                "Taekwondo Tournament",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/iak4VWsWZ1A?si=fP-4mPOw3ZvMZ8Zx" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Are you a martial arts enthusiast? Welcome to the Dubai Taekwondo Championship, where we showcase thrilling matches and high-level competition. Book your tickets today to witness Dubai's top martial artists compete for the championship title.",
                "Price : Aed 65",
                "Date : 24th July 2024",
                "taekwondo",
                "taekwondoticket"
            )
        )

        // Apply scaling animations and navigation to image buttons
        applyScaleAnimation(fitnesschallenge, eventDetailsMap[fitnesschallenge])
        applyScaleAnimation(dubairun, eventDetailsMap[dubairun])
        applyScaleAnimation(football, eventDetailsMap[football])
        applyScaleAnimation(cricketleague, eventDetailsMap[cricketleague])
        applyScaleAnimation(taekwondo, eventDetailsMap[taekwondo])

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