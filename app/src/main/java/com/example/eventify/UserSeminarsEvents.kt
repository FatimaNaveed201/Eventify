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

class UserSeminarsEvents : AppCompatActivity() {

    //declaring the variables
    private lateinit var back: ImageButton
    private lateinit var techsummit: ImageButton
    private lateinit var healthcare: ImageButton
    private lateinit var fintech: ImageButton
    private lateinit var smartcities: ImageButton

    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_seminars_events)

        // Retrieve intent extra
        val isSmartCitiesButtonVisible = intent.getBooleanExtra("isSmartCitiesButtonVisible", true)

        // Update visibility of Opera button
        val smartcitiesButton: ImageButton = findViewById(R.id.smartcitiesButton)
        val recentlyAdded: ImageView = findViewById(R.id.recentlyadded)

        smartcitiesButton.visibility = if (isSmartCitiesButtonVisible) View.VISIBLE else View.GONE
        recentlyAdded.visibility = if (isSmartCitiesButtonVisible) View.VISIBLE else View.GONE

        // Initialize views
        back = findViewById(R.id.backButton)
        techsummit = findViewById(R.id.techsummitButton)
        healthcare = findViewById(R.id.healthcareButton)
        fintech = findViewById(R.id.fintechButton)
        smartcities = findViewById(R.id.smartcitiesButton)

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        // Check if reset action is triggered
        val sharedPreferences = getSharedPreferences("reset_state", Context.MODE_PRIVATE)
        val isResetTriggered = sharedPreferences.getBoolean("is_reset_triggered", true)

        if (isResetTriggered) {
            // Reset visibility
            smartcities.visibility = View.GONE
            recentlyAdded.visibility = View.GONE

            // Reset the trigger so it doesn't execute again
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_reset_triggered", false)
            editor.apply()
        }

        // Event details map
        val eventDetailsMap = mapOf(
            techsummit to EventDetails(
                "Tech Innovation Summit",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/yzb8TCv_z24?si=AYqLUEvePE9kBSXU" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "This one-day event will give industry leaders a full picture of the latest advancements across numerous technology fields, whilst helping you to develop the connections needed to pioneer innovation that works for your business.",
                "Price : Aed 350",
                "Date : 12th June 2024",
                "techsummit",
                "techsummitticket"
            ),
            healthcare to EventDetails(
                "Healthcare Innovation Forum",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/tUCidY5ZFBI?si=XpVU_9gY8JEK7u3l" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "The Digital Health Innovation Ecosystems Forum is an exclusive event that brings together international industry actors such as government representatives, healthcare regulators, selected healthcare providers, leading start-up ecosystem facilitators, and innovation experts.",
                "Price : Aed 100",
                "Date : 1st July 2024",
                "healthcare",
                "healthcareticket"
            ),
            fintech to EventDetails(
                "Fintech Middle East",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/epFst_916sE?si=C1l_TKwiLU77-wed" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Fintech growth in the Middle East has been fuelled by the infrastructure needs to address its large number of financially excluded people. Be a part of the world's largest fintech conference and expand your expertise and knowledge in this growing industry. Book your tickets today!",
                "Price : Aed 650",
                "Date : 18th July 2024",
                "fintech",
                "fintechticket"
            ),
            smartcities to EventDetails(
                "Smart Cities expo",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/ETfVSHhCSHc?si=6JZYVBF9I8QJvbbw" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Dubai Expo 2020 Smart City is an event with the sole purpose of providing cities with a better future. This global initiative includes various contributors from across the world. Such as leaders building smart cities, governmental entities, organisations, and companies with futuristic innovatives.",
                "Price : Aed 1,200",
                "Date : 17th June 2024",
                "smartcities",
                "smartcitiesticket"
            )
        )

        // Apply scaling animations and navigation to image buttons
        applyScaleAnimation(techsummit, eventDetailsMap[techsummit])
        applyScaleAnimation(healthcare, eventDetailsMap[healthcare])
        applyScaleAnimation(fintech, eventDetailsMap[fintech])
        applyScaleAnimation(smartcities, eventDetailsMap[smartcities])

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
