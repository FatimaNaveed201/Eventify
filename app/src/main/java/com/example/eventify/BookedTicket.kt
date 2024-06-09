package com.example.eventify

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BookedTicket : AppCompatActivity() {

    private lateinit var backToHomeButton: ImageButton
    private lateinit var eventImage: ImageView
    private lateinit var price: TextView
    private lateinit var date: TextView

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booked_ticket)

        backToHomeButton = findViewById(R.id.backToHomeButton)
        eventImage = findViewById(R.id.eventImage)
        price = findViewById(R.id.price)
        date = findViewById(R.id.Date)

        val eventPrice = intent.getStringExtra("price")
        val eventDate = intent.getStringExtra("date")
        val eventImageResourceName = intent.getStringExtra("eventImage") ?: "default_image"

        price.text = eventPrice
        date.text = eventDate

        val resId = resources.getIdentifier(eventImageResourceName, "drawable", packageName)
        eventImage.setImageResource(resId)

        val ticketImageResourceName = when (eventImageResourceName) {
            "taylorswift" -> "taylorswiftticket"
            "edsheeran" -> "edsheeranticket"
            "dmf" -> "dmfticket"
            "blackpink" -> "blackpinkticket"
            "sikka" -> "sikkaticket"
            "artdubai" -> "artdubaiticket"
            "designweek" -> "designweekticket"
            "artexpo" -> "artexpoticket"
            "laperle" -> "laperleticket"
            "fontana" -> "fontanaticket"
            "pluma" -> "plumaticket"
            "opera" -> "operaticket"
            "fitnesschallenge" -> "fitnesschallengeticket"
            "dubairun" -> "dubairunticket"
            "football" -> "footballticket"
            "cricketleague" -> "cricketleagueticket"
            "techsummit" -> "techsummitticket"
            "healthcare" -> "healthcareticket"
            "fintech" -> "fintechticket"
            "smartcities" -> "smartcitiesticket"
            "foodfestival" -> "foodfestivalticket"
            "streetfood" -> "streetfoodticket"
            "gulfood" -> "gulfoodticket"
            "tasteofdubai" -> "tasteofdubaiticket"
            "photography" -> "photographyticket"
            "pottery" -> "potteryticket"
            "coding" -> "codingticket"
            "painting" -> "paintingticket"
            "camelrace" -> "camelraceticket"
            "heritage" -> "heritageticket"
            "dhafra" -> "dhafraticket"
            "marmoom" -> "marmoomticket"
            "publicspeaking" -> "publicspeakingticket"
            "taekwondo" -> "taekwondoticket"
            "cokestudio" -> "cokestudioticket"
            "beachcanteen" -> "beachcanteenticket"
            "worldart" -> "worldartticket"
            else -> "default_ticket"
        }

        backToHomeButton.setOnClickListener {
            backToHome(ticketImageResourceName)
        }
    }

    @SuppressLint("MutatingSharedPrefs")
    private fun backToHome(ticketImageResourceName: String) {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "") ?: return
        val userTicketsPreferences = getSharedPreferences("ticket_data_$email", Context.MODE_PRIVATE)

        // Get the existing booked tickets set or create a new one
        val bookedTickets = userTicketsPreferences.getStringSet("bookedTickets", mutableSetOf()) ?: mutableSetOf()

        // Add the new ticket to the set
        bookedTickets.add(ticketImageResourceName)

        // Save the updated set back to SharedPreferences
        userTicketsPreferences.edit().putStringSet("bookedTickets", bookedTickets).apply()

        // Log the contents of bookedTickets
        Log.d("SavedTickets", "Booked Tickets: $bookedTickets")

        startActivity(Intent(this, user_main::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }


}
