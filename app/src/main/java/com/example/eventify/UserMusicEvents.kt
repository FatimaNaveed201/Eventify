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

class UserMusicEvents : AppCompatActivity() {

    //declaring the variables
    private lateinit var back: ImageButton
    private lateinit var taylorswift: ImageButton
    private lateinit var dmf: ImageButton
    private lateinit var edsheeran: ImageButton
    private lateinit var blackpink: ImageButton
    private lateinit var cokestudio: ImageButton

    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_music_events)

        // Retrieve intent extra
        val isCokeStudioButtonVisible = intent.getBooleanExtra("isCokeStudioButtonVisible", true)

        // Update visibility of Opera button
        val cokestudioButton: ImageButton = findViewById(R.id.cokestudioButton)
        val recentlyAdded: ImageView = findViewById(R.id.recentlyadded)

        cokestudioButton.visibility = if (isCokeStudioButtonVisible) View.VISIBLE else View.GONE
        recentlyAdded.visibility = if (isCokeStudioButtonVisible) View.VISIBLE else View.GONE

        // Initialize views
        back = findViewById(R.id.backButton)
        taylorswift = findViewById(R.id.taylorswiftButton)
        dmf = findViewById(R.id.dmfButton)
        edsheeran = findViewById(R.id.edsheeranButton)
        blackpink = findViewById(R.id.blackpinkButton)
        cokestudio = findViewById(R.id.cokestudioButton)

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        // Check if reset action is triggered
        val sharedPreferences = getSharedPreferences("reset_state", Context.MODE_PRIVATE)
        val isResetTriggered = sharedPreferences.getBoolean("is_reset_triggered", true)

        if (isResetTriggered) {
            // Reset visibility
            cokestudio.visibility = View.GONE
            recentlyAdded.visibility = View.GONE

            // Reset the trigger so it doesn't execute again
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_reset_triggered", false)
            editor.apply()
        }

        // Event details map
        val eventDetailsMap = mapOf(
            taylorswift to EventDetails(
                "Taylor Swift Eras Tour",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/2YS8IplgAYE?si=7jVlRp0qqFsHHjPA" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Experience Taylor Swift's journey through her musical eras in a spectacular live performance. Enjoy hit songs, stunning visuals, and unforgettable moments in the UAE!",
                "Price : Aed 4,000",
                "Date : 3rd July 2024",
                "taylorswift",
                "taylorswiftticket"
            ),
            dmf to EventDetails(
                "Dubai Music Festival",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/uFV5-H2nYmo?si=htRbbbKQRBR2TVcd" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Join the Dubai Music Festival for an electrifying experience featuring a diverse lineup of international and local artists. Enjoy great music, food, and a vibrant atmosphere!",
                "Price : Aed 700",
                "Date : 7th July 2024",
                "dmf",
                "dmfticket"
            ),
            edsheeran to EventDetails(
                "Ed Sheeran Concert",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/Qp8zMmpEU-E?si=AHOJ_NJqI0uiRsMH" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Experience Ed Sheeran live in concert, performing his chart-topping hits and fan favorites. Don't miss this intimate and memorable musical event!",
                "Price : Aed 1,500",
                "Date : 20th July 2024",
                "edsheeran",
                "edsheeranticket"
            ),
            blackpink to EventDetails(
                "Black Pink Concert",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/o1KhtHeLpJ0?si=zos4Hxzk0c_JJStO" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Join Black Pink for an unforgettable concert experience featuring their biggest hits and stunning performances. Get ready for a night of K-pop magic in the UAE!",
                "Price : Aed 2,000",
                "Date : 30th July 2024",
                "blackpink",
                "blackpinkticket"
            ),
            cokestudio to EventDetails(
                "Coke Studio Bash",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/SJ4TnEK67uI?si=w2ppeZM0m4qnShYT" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Experience the magic of CokeStudio live! Join us for an unforgettable night of music featuring top artists and electrifying performances. Get your tickets now and be part of this epic concert!",
                "Price : Aed 850",
                "Date : 1st August 2024",
                "cokestudio",
                "cokestudioticket"
            )
        )

        // Apply scaling animations and navigation to image buttons
        applyScaleAnimation(taylorswift, eventDetailsMap[taylorswift])
        applyScaleAnimation(dmf, eventDetailsMap[dmf])
        applyScaleAnimation(edsheeran, eventDetailsMap[edsheeran])
        applyScaleAnimation(blackpink, eventDetailsMap[blackpink])
        applyScaleAnimation(cokestudio, eventDetailsMap[cokestudio])

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
