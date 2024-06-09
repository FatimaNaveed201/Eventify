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

class UserFoodEvents : AppCompatActivity() {

    // variables
    private lateinit var back: ImageButton
    private lateinit var foodfestival: ImageButton
    private lateinit var streetfood: ImageButton
    private lateinit var gulfood: ImageButton
    private lateinit var tasteofdubai: ImageButton
    private lateinit var beachcanteen: ImageButton
    private lateinit var recentlyaddedd: ImageView

    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_food_events)

        beachcanteen = findViewById(R.id.beachcanteenButton)
        recentlyaddedd = findViewById(R.id.recentlyadded)

        beachcanteen.visibility = View.GONE
        recentlyaddedd.visibility = View.GONE

        // Retrieve intent extra
        val isBeachCanteenButtonVisible = intent.getBooleanExtra("isBeachCanteenButtonVisible", true)

        // Update visibility of Opera button
        val beachcanteenButton: ImageButton = findViewById(R.id.beachcanteenButton)
        val recentlyAdded: ImageView = findViewById(R.id.recentlyadded)

        beachcanteenButton.visibility = if (isBeachCanteenButtonVisible) View.VISIBLE else View.GONE
        recentlyAdded.visibility = if (isBeachCanteenButtonVisible) View.VISIBLE else View.GONE

        // Initialize views
        back = findViewById(R.id.backButton)
        foodfestival = findViewById(R.id.foodfestivalButton)
        streetfood = findViewById(R.id.streetfoodButton)
        gulfood = findViewById(R.id.gulfoodButton)
        tasteofdubai = findViewById(R.id.tasteofdubaiButton)


        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)


        // Event details map
        val eventDetailsMap = mapOf(
            foodfestival to EventDetails(
                "Dubai Food Festival",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/vY2z4KnFLBA?si=82FT41QOeLXCLku9" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Savor the flavors at the Dubai Food Festival, where you can enjoy gourmet cuisine, street food, and culinary workshops. A paradise for food lovers in the heart of Dubai!",
                "Price : Aed 720",
                "Date : 13th June 2024",
                "foodfestival",
                "foodfestivalticket"
            ),
            streetfood to EventDetails(
                "Dubai Street Food Carnival",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/fOGuLW8TdyY?si=QsMyYjYPuf2VsP7v" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Experience a culinary journey at the Dubai Street Food Carnival, featuring a vibrant mix of street food vendors, live music, and entertainment. A must-visit for street food enthusiasts!",
                "Price : Aed 400",
                "Date : 1st July 2024",
                "streetfood",
                "streetfoodticket"
            ),
            gulfood to EventDetails(
                "Gulfood Dubai 2024",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/U6dHKjM52UA?si=z8XxgX2hTZH2bXye" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Join the world's largest annual food and beverage trade show at Gulfood Dubai 2024. Discover new products, network with industry leaders, and explore the latest trends in the food industry.",
                "Price : Aed 200",
                "Date : 27th June 2024",
                "gulfood",
                "gulfoodticket"
            ),
            tasteofdubai to EventDetails(
                "Taste Of Dubai",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/8k7kz6fGQMU?si=9aGgZv8yC_bhRrhe" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Indulge in a culinary adventure at Taste Of Dubai, featuring top chefs, live cooking demonstrations, and delicious food from the city's best restaurants. A gourmet's delight!",
                "Price : Aed 210",
                "Date : 17th June 2024",
                "tasteofdubai",
                "tasteofdubaiticket"
            ),
            beachcanteen to EventDetails(
                "Beach Canteen Dubai",
                """<iframe width="560" height="315" src="https://www.youtube.com/embed/M6d2N7gwKPQ?si=D9p-xGJ25FRyRDFJ" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""",
                "Indulge in a culinary burst of flavors at Beach Canteen Dubai, witness and explore as the beach offers' top chefs, live cooking demonstrations and many more exciting events along with games for both children and adults! A gourmet's delight!",
                "Price : Aed 20",
                "Date : 10th July 2024",
                "beachcanteen",
                "beachcanteenticket"
            )

        )

        // Apply scaling animations and navigation to image buttons
        applyScaleAnimation(foodfestival, eventDetailsMap[foodfestival])
        applyScaleAnimation(streetfood, eventDetailsMap[streetfood])
        applyScaleAnimation(gulfood, eventDetailsMap[gulfood])
        applyScaleAnimation(tasteofdubai, eventDetailsMap[tasteofdubai])
        applyScaleAnimation(beachcanteen, eventDetailsMap[beachcanteen])

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
