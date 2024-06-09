package com.example.eventify

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserSelectedEvent : AppCompatActivity() {

    //declaring variables
    private lateinit var back: ImageButton
    private lateinit var eventHeading: TextView
    private lateinit var teaser: WebView
    private lateinit var description: TextView
    private lateinit var price: TextView
    private lateinit var date: TextView
    private lateinit var bookticket: ImageButton

    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("MissingInflatedId", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_selected_event)

        back = findViewById(R.id.backmaroonButton)
        eventHeading = findViewById(R.id.eventHeading)
        teaser = findViewById(R.id.teaser)
        description = findViewById(R.id.description)
        price = findViewById(R.id.price)
        date = findViewById(R.id.date)
        bookticket = findViewById(R.id.bookticket)

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        bookticket.setOnClickListener {
            val ticketImageResourceName = intent.getStringExtra("ticketImageResourceName") ?: ""
            if (isTicketAlreadyBooked(ticketImageResourceName)) {
                showAlreadyBookedDialog()
            } else {
                generateTicket(ticketImageResourceName)
            }
        }

        // Retrieve and display event details
        val heading = intent.getStringExtra("eventHeading")
        val teaserContent = intent.getStringExtra("teaserEmbed")
        val desc = intent.getStringExtra("description")
        val eventPrice = intent.getStringExtra("price")
        val eventDate = intent.getStringExtra("date")
        val eventImageResourceName = intent.getStringExtra("eventImage")

        eventHeading.text = heading

        // Modify teaser content to ensure video fills the WebView
        val teaserContentModified = """
            <html>
            <head>
                <style type="text/css">
                    body, html { margin: 0; padding: 0; width: 100%; height: 100%; }
                    iframe { width: 100%; height: 100%; }
                </style>
            </head>
            <body>
                $teaserContent
            </body>
            </html>
        """

        //enabling required contents for webview
        teaser.settings.javaScriptEnabled = true
        teaser.settings.loadWithOverviewMode = true
        teaser.settings.useWideViewPort = true
        teaser.settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        teaser.loadData(teaserContentModified, "text/html", "utf-8")
        description.text = desc
        price.text = eventPrice
        date.text = eventDate

        //back button animation and functionality
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

    //function to generate required ticket
    private fun generateTicket(ticketImageResourceName: String) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.activity_generating_ticket, null)

        val generatorWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true)
        generatorWindow.showAtLocation(popupView, Gravity.TOP, 0, 0)

        // Delay transition to next activity after 6 seconds
        handler.postDelayed({
            generatorWindow.dismiss() // Dismiss the popup
            bookTicket(ticketImageResourceName)
        }, 7000) // 7 seconds delay
    }

    //checking if the ticket is already booked or not
    private fun isTicketAlreadyBooked(ticketImageResourceName: String): Boolean {
        val sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "") ?: return false
        val userTicketsPreferences = getSharedPreferences("ticket_data_$email", MODE_PRIVATE)
        val bookedTickets = userTicketsPreferences.getStringSet("bookedTickets", mutableSetOf()) ?: mutableSetOf()
        return bookedTickets.contains(ticketImageResourceName)
    }

    //showing an alert dialog if the ticket is already booked
    private fun showAlreadyBookedDialog() {
        val alertDialog = AlertDialog.Builder(this).create()

        alertDialog.setMessage("You have already booked this ticket!")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay") { dialog, _ ->
            dialog.dismiss()
            onBackPressed()
        }

        alertDialog.setCancelable(false)

        // Set background color
        val colorDrawable = ColorDrawable(Color.WHITE)
        alertDialog.window?.setBackgroundDrawable(colorDrawable)

        // Set text color
        val messageTextView = alertDialog.findViewById<TextView>(android.R.id.message)
        messageTextView?.setTextColor(ContextCompat.getColor(this, R.color.dark_maroon))

        // Set button background color
        alertDialog.setOnShowListener {
            val button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button?.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_maroon))
            button?.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        }

        alertDialog.show()
    }


    //storing and getting the already booked ticket from the shared prefs email
    @SuppressLint("MutatingSharedPrefs")
    private fun bookTicket(ticketImageResourceName: String) {
        val sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "") ?: return
        val userTicketsPreferences = getSharedPreferences("ticket_data_$email", MODE_PRIVATE)

        val bookedTickets = userTicketsPreferences.getStringSet("bookedTickets", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        bookedTickets.add(ticketImageResourceName)

        userTicketsPreferences.edit().putStringSet("bookedTickets", bookedTickets).apply()

        // Log the contents of bookedTickets
        Log.d("SavedTickets", "Booked Tickets: $bookedTickets")

        val intent = Intent(this, BookedTicket::class.java).apply {
            putExtra("price", price.text.toString())
            putExtra("date", date.text.toString())
            putExtra("eventImage", intent.getStringExtra("eventImage"))
            putExtra("ticketImageResourceName", ticketImageResourceName)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
