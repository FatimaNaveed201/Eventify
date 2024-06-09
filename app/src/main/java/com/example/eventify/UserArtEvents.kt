package com.example.eventify

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class UserArtEvents : AppCompatActivity() {
    private lateinit var back: ImageButton
    private lateinit var sikka: ImageButton
    private lateinit var artdubai: ImageButton
    private lateinit var designweek: ImageButton
    private lateinit var artexpo: ImageButton
    private  lateinit var worldart: ImageButton

    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation


    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_art_events)

        // Retrieve intent extra
        val isWorldArtButtonVisible = intent.getBooleanExtra("isWorldArtButtonVisible", true)

        // Update visibility of Opera button
        val worldartButton: ImageButton = findViewById(R.id.worldartButton)
        val recentlyAdded: ImageView = findViewById(R.id.recentlyadded)

        worldartButton.visibility = if (isWorldArtButtonVisible) View.VISIBLE else View.GONE
        recentlyAdded.visibility = if (isWorldArtButtonVisible) View.VISIBLE else View.GONE

        // Initialize views
        back = findViewById(R.id.backButton)
        sikka = findViewById(R.id.sikkaButton)
        artdubai = findViewById(R.id.artdubaiButton)
        designweek = findViewById(R.id.designweekButton)
        artexpo = findViewById(R.id.artexpoButton)
        worldart = findViewById(R.id.worldartButton)

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        // Check if reset action is triggered
        val sharedPreferences = getSharedPreferences("reset_state", Context.MODE_PRIVATE)
        val isResetTriggered = sharedPreferences.getBoolean("is_reset_triggered", false)

        if (isResetTriggered) {
            // Reset visibility of worldart button
            worldart.visibility = View.GONE
            recentlyAdded.visibility = View.GONE

            // Reset the trigger so it doesn't execute again
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_reset_triggered", false)
            editor.apply()
        }

        // Event details map
        val eventDetailsMap = mapOf(
            sikka to EventDetails(
                "Sikka Art Festival",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/WMGrc25zBpw?si=hKfjM8of3RYPS850" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Experience the vibrant Sikka Art Festival, showcasing local and international artists, live performances, and interactive installations. Immerse yourself in the creativity of Dubai's art scene!",
                "Price : Aed 40",
                "Date : 12th June 2024",
                "sikka",
                "sikkaticket"
            ),
            artdubai to EventDetails(
                "Art Dubai",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/Suind9xxYoo?si=joMNV5e2BnSE1oXe" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Explore contemporary art at Art Dubai, featuring works from leading galleries and emerging artists. Engage with diverse perspectives and innovative creations in the heart of the UAE!",
                "Price : Aed 150",
                "Date : 1st July 2024",
                "artdubai",
                "artdubaiticket"
            ),
            designweek to EventDetails(
                "Dubai Design Week",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/Q9URuX0G5FU?si=sonB1H7Otsiu-Gg7" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Experience the future of design at Dubai Design Week, featuring innovative exhibitions, workshops, and discussions. Discover cutting-edge concepts and creativity shaping tomorrow's world!",
                "Price : Aed 1,000",
                "Date : 10th July 2024",
                "designweek",
                "designweekticket"
            ),
            artexpo to EventDetails(
                "Dubai Art Expo",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/JLYjlMVUtZk?si=kHTqxsDO5xWB8L5h" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Immerse yourself in a world of art at the Dubai Art Expo, featuring a diverse collection of contemporary artworks and cultural experiences. Celebrate creativity and expression in a dynamic showcase!",
                "Price : Aed 2,000",
                "Date : 20th July 2024",
                "artexpo",
                "artexpoticket"
            ),
            worldart to EventDetails(
                "World Art Exhibition",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/Suind9xxYoo?si=SelamiQnXSB4DfxG" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Become a part or witness one of the largest and most prestige art exhibition here in the heart of Dubai. World Art Exhibition brings the artworks of artists from all over the world. Experience the world's best and unique art masterpieces all under a single roof!",
                "Price : Aed 50",
                "Date : 30th July 2024",
                "worldart",
                "worldartticket"
            )
        )


        // Apply scaling animations and navigation to image buttons
        applyScaleAnimation(sikka, eventDetailsMap[sikka])
        applyScaleAnimation(artdubai, eventDetailsMap[artdubai])
        applyScaleAnimation(designweek, eventDetailsMap[designweek])
        applyScaleAnimation(artexpo, eventDetailsMap[artexpo])
        applyScaleAnimation(worldart, eventDetailsMap[worldart])

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
