package com.example.eventify

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserBookings : AppCompatActivity() {

    //declare variables
    private lateinit var back: ImageButton
    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    @SuppressLint("MissingInflatedId", "DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_bookings)

        val container = findViewById<ViewGroup>(R.id.ticketcontainer)

        back = findViewById(R.id.backButton)

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "") ?: return
        val userTicketsPreferences = getSharedPreferences("ticket_data_$email", Context.MODE_PRIVATE)

        val bookedTickets = userTicketsPreferences.getStringSet("bookedTickets", mutableSetOf()) ?: mutableSetOf()

        // Log the contents of bookedTickets
        Log.d("RetrievedTickets", "Booked Tickets: $bookedTickets")

        val ticketCounts = mutableMapOf<String, Int>()

        // Count the occurrences of each booked ticket
        bookedTickets.forEach { ticket ->
            ticketCounts[ticket] = ticketCounts.getOrDefault(ticket, 0) + 1
        }

        // Add ImageView instances for each booked ticket based on its count
        ticketCounts.forEach { (ticketImageResourceName, count) ->
            repeat(count) {
                if (ticketImageResourceName.isNotEmpty()) {
                    val imageView = ImageView(this).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            resources.getDimensionPixelSize(R.dimen.ticket_image_width),
                            resources.getDimensionPixelSize(R.dimen.ticket_image_height)
                        )
                        setImageResource(resources.getIdentifier(ticketImageResourceName, "drawable", packageName))
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setPadding(
                            resources.getDimensionPixelSize(R.dimen.ticket_image_margin_horizontal),
                            resources.getDimensionPixelSize(R.dimen.ticket_image_margin_vertical),
                            resources.getDimensionPixelSize(R.dimen.ticket_image_margin_horizontal),
                            resources.getDimensionPixelSize(R.dimen.ticket_image_margin_vertical)
                        )
                    }
                    container.addView(imageView)
                }
            }
        }

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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
