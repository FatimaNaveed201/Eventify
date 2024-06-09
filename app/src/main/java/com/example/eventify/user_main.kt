package com.example.eventify

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class user_main : AppCompatActivity() {

    //declare variables
    private lateinit var interpolator: Any
    private lateinit var showUserPopup: ImageButton
    private lateinit var username: TextView
    private lateinit var userpfp: ImageView
    private lateinit var music: ImageButton
    private lateinit var art: ImageButton
    private lateinit var theatre: ImageButton
    private lateinit var sports: ImageButton
    private lateinit var seminars: ImageButton
    private lateinit var food: ImageButton
    private lateinit var workshops: ImageButton
    private lateinit var cultural: ImageButton
    private lateinit var info: ImageButton
    private lateinit var menu: ImageButton
    private lateinit var nav_view: NavigationView
    private lateinit var mybooking: ImageButton
    private lateinit var logout: ImageButton

    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_main)

        // Initialize views
        showUserPopup = findViewById(R.id.infoButton)
        username = findViewById(R.id.username)
        userpfp = findViewById(R.id.userpfp)
        music = findViewById(R.id.musicButton)
        art = findViewById(R.id.artButton)
        theatre = findViewById(R.id.theatreButton)
        sports = findViewById(R.id.sportsButton)
        seminars = findViewById(R.id.seminarsButton)
        food = findViewById(R.id.foodButton)
        workshops = findViewById(R.id.workshopsButton)
        cultural = findViewById(R.id.culturalButton)
        info = findViewById(R.id.infoButton)
        menu = findViewById(R.id.menuButton)

        // Initialize navigation view
        nav_view = findViewById(R.id.nav_view)

        // Launch info popup
        showUserPopup.setOnClickListener{
            showPopup()
        }

        // Initialize navigation header
        initializeNavigationHeader()

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        // Load user data from SharedPreferences
        loadUserData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set onClickListeners with animations and navigation
        setButtonAction(music, UserMusicEvents::class.java)
        setButtonAction(art, UserArtEvents::class.java)
        setButtonAction(theatre, UserTheatreEvents::class.java)
        setButtonAction(sports, UserSportsEvents::class.java)
        setButtonAction(seminars, UserSeminarsEvents::class.java)
        setButtonAction(food, UserFoodEvents::class.java)
        setButtonAction(workshops, UserWorkshopsEvents::class.java)
        setButtonAction(cultural, UserCulturalEvents::class.java)
        setButtonAction(mybooking, UserBookings::class.java)
        setButtonAction(logout, MainActivity::class.java)

        // Setup drawer layout and toggle
        drawerLayout = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup menu button to open the drawer
        menu.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        // Setup navigation view
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_my_bookings -> {
                    navigateToActivity(UserBookings::class.java)
                    true
                }
                R.id.nav_logout -> {
                    saveTicketsBeforeLogout()  // Save tickets before logging out
                    logout()
                    true
                }
                else -> false
            }
        }

        // Load user data for the navigation header
        loadUserDataForHeader()
    }

    private fun showPopup() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.activity_user_info_popup, null)

        val instructWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true)
        instructWindow.showAtLocation(popupView, Gravity.TOP, 0, 0)

        val closeButton = popupView.findViewById<AppCompatImageButton>(R.id.closeButton)
        closeButton.setOnClickListener {
            instructWindow.dismiss()
        }
    }

    private fun initializeNavigationHeader() {
        val headerView = nav_view.getHeaderView(0)
        mybooking = headerView.findViewById(R.id.nav_my_bookings)
        logout = headerView.findViewById(R.id.nav_logout)
    }

    private fun loadUserDataForHeader() {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("name", "")
        val profilePictureResId = sharedPreferences.getInt("profilePicture", R.drawable.addpfp)
1
        // Update TextView with user's name in the navigation header
        val headerView = nav_view.getHeaderView(0)
        val navUsername = headerView.findViewById<TextView>(R.id.nav_user_name)
        navUsername.text = userName

        // Update ImageView with user's profile picture in the navigation header
        val navUserpfp = headerView.findViewById<ImageView>(R.id.nav_pfp)
        navUserpfp.setImageResource(profilePictureResId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadUserData() {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "") ?: return

        val userName = sharedPreferences.getString("name", "")
        val profilePictureResId = sharedPreferences.getInt("profilePicture", R.drawable.addpfp)

        // Update UI with user data
        username.text = userName
        userpfp.setImageResource(profilePictureResId)
    }

    //button animations
    private fun setButtonAction(button: ImageButton, activityClass: Class<*>) {
        button.setOnClickListener {
            button.startAnimation(scaleUp)
            button.startAnimation(scaleDown)
            navigateToActivity(activityClass)
        }
    }

    //navigate to next activity
    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    //saving tickets in email before logging out
    private fun saveTicketsBeforeLogout() {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "") ?: return
        val userTicketsPreferences = getSharedPreferences("ticket_data_$email", Context.MODE_PRIVATE)

        val bookedTickets = userTicketsPreferences.getStringSet("bookedTickets", mutableSetOf()) ?: mutableSetOf()
        // Log the contents of bookedTickets to verify before logging out
        Log.d("LogoutTickets", "Booked Tickets: $bookedTickets")
        userTicketsPreferences.edit().putStringSet("bookedTickets", bookedTickets).apply()
    }

    private fun logout() {
        // Clear session data
        val sharedPreferences = getSharedPreferences("user_session_data", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        // Navigate to MainActivity
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
}
