package com.example.eventify

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class admin_main : AppCompatActivity() {

    //declaring variables
    private lateinit var interpolator: Any
    private lateinit var showAdminPopup: ImageButton
    private lateinit var adminname: TextView
    private lateinit var adminpfp: ImageView
    private lateinit var info: ImageButton
    private lateinit var menu: ImageButton
    private lateinit var nav_view: NavigationView
    private lateinit var logout: ImageButton
    private lateinit var nav_reset_changes: ImageButton
    private lateinit var alleventsadded: ImageView

    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle


    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_main)

        // Initialize views
        val operaButton: ImageButton = findViewById(R.id.operaButton)
        val publicspeakingButton: ImageButton = findViewById(R.id.publickspeakingButton)
        val taekwondoButton: ImageButton = findViewById(R.id.taekwondoButton)
        val cokestudioButton: ImageButton = findViewById(R.id.cokestudioButton)
        val beachcanteenButton: ImageButton = findViewById(R.id.beachcanteenButton)
        val worldartButton: ImageButton = findViewById(R.id.worldartButton)
        val smartcitiesButton: ImageButton = findViewById(R.id.smartcitiesButton)
        val marmoomButton: ImageButton = findViewById(R.id.marmoomButton)

        showAdminPopup = findViewById(R.id.infoButton)
        adminname = findViewById(R.id.adminname)
        adminpfp = findViewById(R.id.adminpfp)
        info = findViewById(R.id.infoButton)
        menu = findViewById(R.id.menuButton)
        alleventsadded = findViewById(R.id.alleventsadded)

        // Call the function to check the visibility of all buttons
        checkAllButtonsVisibility()

        // Launch info popup
        showAdminPopup.setOnClickListener{
            showPopup()
        }

        // Initialize navigation view
        nav_view = findViewById(R.id.nav_view)

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
        nav_view.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_logout -> {
                    logout()
                    true
                }
                else -> false
            }
        }

        // Load the saved visibility state from SharedPreferences
        val isOperaButtonVisible = getButtonVisibilityState("is_opera_button_visible")
        val isPublicSpeakingButtonVisible = getButtonVisibilityState("is_publicspeaking_button_visible")
        val isTaekwondoButtonVisible = getButtonVisibilityState("is_taekwondo_button_visible")
        val isCokeStudioButtonVisible = getButtonVisibilityState("is_cokestudio_button_visible")
        val isBeachCanteenButtonVisible = getButtonVisibilityState("is_beachcanteen_button_visible")
        val isWorldArtButtonVisible = getButtonVisibilityState("is_worldart_button_visible")
        val isSmartCitiesButtonVisible = getButtonVisibilityState("is_smartcities_button_visible")
        val isMarmoomButtonVisible = getButtonVisibilityState("is_marmoom_button_visible")

        // Update visibility of buttons in admin_main.xml
        operaButton.visibility = if (isOperaButtonVisible) View.VISIBLE else View.GONE
        publicspeakingButton.visibility = if (isPublicSpeakingButtonVisible) View.VISIBLE else View.GONE
        taekwondoButton.visibility = if (isTaekwondoButtonVisible) View.VISIBLE else View.GONE
        cokestudioButton.visibility = if (isCokeStudioButtonVisible) View.VISIBLE else View.GONE
        beachcanteenButton.visibility = if (isBeachCanteenButtonVisible) View.VISIBLE else View.GONE
        worldartButton.visibility = if (isWorldArtButtonVisible) View.VISIBLE else View.GONE
        smartcitiesButton.visibility = if (isSmartCitiesButtonVisible) View.VISIBLE else View.GONE
        marmoomButton.visibility = if (isMarmoomButtonVisible) View.VISIBLE else View.GONE

        // Check if all event image buttons are gone
        if (operaButton.visibility == View.GONE &&
            publicspeakingButton.visibility == View.GONE &&
            taekwondoButton.visibility == View.GONE &&
            cokestudioButton.visibility == View.GONE &&
            beachcanteenButton.visibility == View.GONE &&
            worldartButton.visibility == View.GONE &&
            smartcitiesButton.visibility == View.GONE &&
            marmoomButton.visibility == View.GONE) {
            alleventsadded.visibility = View.VISIBLE
        }

        operaButton.setOnClickListener {
            // Show message box
            Toast.makeText(this, "New Event Added for User Booking!", Toast.LENGTH_SHORT).show()

            // Update visibility of Opera button in admin_main.xml
            operaButton.visibility = View.GONE

            // Save the visibility state in SharedPreferences
            saveButtonVisibilityState("is_opera_button_visible", false)
            checkAllButtonsVisibility()
        }

        publicspeakingButton.setOnClickListener {
            // Show message box
            Toast.makeText(this, "New Event Added for User Booking!", Toast.LENGTH_SHORT).show()

            // Update visibility of Public Speaking button in admin_main.xml
            publicspeakingButton.visibility = View.GONE

            // Save the visibility state in SharedPreferences
            saveButtonVisibilityState("is_publicspeaking_button_visible", false)
            checkAllButtonsVisibility()
        }

        taekwondoButton.setOnClickListener {
            // Show message box
            Toast.makeText(this, "New Event Added for User Booking!", Toast.LENGTH_SHORT).show()

            // Update visibility of Public Speaking button in admin_main.xml
            taekwondoButton.visibility = View.GONE

            // Save the visibility state in SharedPreferences
            saveButtonVisibilityState("is_taekwondo_button_visible", false)
            checkAllButtonsVisibility()
        }

        cokestudioButton.setOnClickListener {
            // Show message box
            Toast.makeText(this, "New Event Added for User Booking!", Toast.LENGTH_SHORT).show()

            // Update visibility of Public Speaking button in admin_main.xml
            cokestudioButton.visibility = View.GONE

            // Save the visibility state in SharedPreferences
            saveButtonVisibilityState("is_cokestudio_button_visible", false)
            checkAllButtonsVisibility()
        }

        beachcanteenButton.setOnClickListener {
            // Show message box
            Toast.makeText(this, "New Event Added for User Booking!", Toast.LENGTH_SHORT).show()

            // Update visibility of Public Speaking button in admin_main.xml
            beachcanteenButton.visibility = View.GONE

            // Save the visibility state in SharedPreferences
            saveButtonVisibilityState("is_beachcanteen_button_visible", false)
            // Update visibility in UserTheatreEvents
            updateUserTheatreEventsButtonVisibility(true)
            //checkAllButtonsVisibility()
        }

        worldartButton.setOnClickListener {
            // Show message box
            Toast.makeText(this, "New Event Added for User Booking!", Toast.LENGTH_SHORT).show()

            // Update visibility of Public Speaking button in admin_main.xml
            worldartButton.visibility = View.GONE

            // Save the visibility state in SharedPreferences
            saveButtonVisibilityState("is_worldart_button_visible", false)
            checkAllButtonsVisibility()
        }

        smartcitiesButton.setOnClickListener {
            // Show message box
            Toast.makeText(this, "New Event Added for User Booking!", Toast.LENGTH_SHORT).show()

            // Update visibility of Public Speaking button in admin_main.xml
            smartcitiesButton.visibility = View.GONE

            // Save the visibility state in SharedPreferences
            saveButtonVisibilityState("is_smartcities_button_visible", false)
            checkAllButtonsVisibility()
        }

        marmoomButton.setOnClickListener {
            // Show message box
            Toast.makeText(this, "New Event Added for User Booking!", Toast.LENGTH_SHORT).show()

            // Update visibility of Public Speaking button in admin_main.xml
            marmoomButton.visibility = View.GONE

            // Save the visibility state in SharedPreferences
            saveButtonVisibilityState("is_marmoom_button_visible", true)
            checkAllButtonsVisibility()
        }

        nav_reset_changes.setOnClickListener {
            // Reset the visibility state of buttons
            operaButton.visibility = View.VISIBLE
            publicspeakingButton.visibility = View.VISIBLE
            taekwondoButton.visibility = View.VISIBLE
            cokestudioButton.visibility = View.VISIBLE
            beachcanteenButton.visibility = View.VISIBLE
            worldartButton.visibility = View.VISIBLE
            smartcitiesButton.visibility = View.VISIBLE
            marmoomButton.visibility = View.VISIBLE

            // Save the reset visibility state in SharedPreferences
            saveButtonVisibilityState("is_opera_button_visible", true)
            saveButtonVisibilityState("is_publicspeaking_button_visible", true)
            saveButtonVisibilityState("is_taekwondo_button_visible", true)
            saveButtonVisibilityState("is_cokestudio_button_visible", true)
            saveButtonVisibilityState("is_beachcanteen_button_visible", true)
            saveButtonVisibilityState("is_worldart_button_visible", true)
            saveButtonVisibilityState("is_smartcities_button_visible", true)
            saveButtonVisibilityState("is_marmoom_button_visible", true)
            checkAllButtonsVisibility()

            // Pass the reset visibility state to UserTheatreEvents and UserWorkshopsEvents
            val intentOpera = Intent(this, UserTheatreEvents::class.java)
            intentOpera.putExtra("isOperaButtonVisible", true)

            val intentPublicSpeaking = Intent(this, UserWorkshopsEvents::class.java)
            intentPublicSpeaking.putExtra("isPublicSpeakingButtonVisible", true)

            val intentTaekwondo = Intent(this, UserSportsEvents::class.java)
            intentTaekwondo.putExtra("isTaekwondoButtonVisible", true)

            val intentCokeStudio = Intent(this, UserMusicEvents::class.java)
            intentCokeStudio.putExtra("isCokeStudioButtonVisible", true)

            val intentBeachCanteen = Intent(this, UserFoodEvents::class.java)
            intentBeachCanteen.putExtra("isBeachCanteenButtonVisible", true)

            val intentWorldArt = Intent(this, UserArtEvents::class.java)
            intentWorldArt.putExtra("isWorldArtButtonVisible", true)

            val intentSmartCities = Intent(this, UserSeminarsEvents::class.java)
            intentSmartCities.putExtra("isSmartCitiesButtonVisible", true)

            val intentMarmoom = Intent(this, UserCulturalEvents::class.java)
            intentMarmoom.putExtra("isMarmoomButtonVisible", true)

            val alleventsadded: ImageView = findViewById(R.id.alleventsadded)
            alleventsadded.visibility=View.GONE

            // Save the reset state in SharedPreferences
            val sharedPreferences = getSharedPreferences("reset_state", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_reset_triggered", true)
            editor.apply()

            // Show message box
            Toast.makeText(this, "Event Additions Have Been Reset!", Toast.LENGTH_LONG).show()
        }



        // Load user data for the navigation header
        loadUserDataForHeader()
    }


    @SuppressLint("MissingInflatedId")
    private fun showPopup() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.activity_admin_info_popup, null)

        val instructWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true)
        instructWindow.showAtLocation(popupView, Gravity.TOP, 0, 0)

        val closeButton = popupView.findViewById<AppCompatImageButton>(R.id.closeButton)
        closeButton.setOnClickListener {
            instructWindow.dismiss()
        }
    }

    private fun checkAllButtonsVisibility() {
        // Get references to all event buttons
        val operaButton: ImageButton = findViewById(R.id.operaButton)
        val publicspeakingButton: ImageButton = findViewById(R.id.publickspeakingButton)
        val taekwondoButton: ImageButton = findViewById(R.id.taekwondoButton)
        val cokestudioButton: ImageButton = findViewById(R.id.cokestudioButton)
        val beachcanteenButton: ImageButton = findViewById(R.id.beachcanteenButton)
        val worldartButton: ImageButton = findViewById(R.id.worldartButton)
        val smartcitiesButton: ImageButton = findViewById(R.id.smartcitiesButton)
        val marmoomButton: ImageButton = findViewById(R.id.marmoomButton)

        // Get reference to the alleventsadded ImageView
        val alleventsadded: ImageView = findViewById(R.id.alleventsadded)

        // Check if all event buttons are gone
        if (operaButton.visibility == View.GONE &&
            publicspeakingButton.visibility == View.GONE &&
            taekwondoButton.visibility == View.GONE &&
            cokestudioButton.visibility == View.GONE &&
            beachcanteenButton.visibility == View.GONE &&
            worldartButton.visibility == View.GONE &&
            smartcitiesButton.visibility == View.GONE &&
            marmoomButton.visibility == View.GONE) {
            alleventsadded.visibility = View.VISIBLE
        } else {
            alleventsadded.visibility = View.GONE
        }
    }

    private fun initializeNavigationHeader() {
        val headerView = nav_view.getHeaderView(0)
        logout = headerView.findViewById(R.id.nav_logout)
        nav_reset_changes = headerView.findViewById(R.id.nav_reset_changes)
    }

    private fun loadUserDataForHeader() {
        val sharedPreferences = getSharedPreferences("admin_data", Context.MODE_PRIVATE)
        val adminName = sharedPreferences.getString("name", "")
        val profilePictureResId = sharedPreferences.getInt("profilePicture", R.drawable.addpfp)

        // Update TextView with user's name in the navigation header
        val headerView = nav_view.getHeaderView(0)
        val navAdminname = headerView.findViewById<TextView>(R.id.nav_admin_name)
        navAdminname.text = adminName

        // Update ImageView with user's profile picture in the navigation header
        val navAdminpfp = headerView.findViewById<ImageView>(R.id.nav_pfp)
        navAdminpfp.setImageResource(profilePictureResId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadUserData() {
        val sharedPreferences = getSharedPreferences("admin_data", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "") ?: return

        val adminName = sharedPreferences.getString("name", "")
        val profilePictureResId = sharedPreferences.getInt("profilePicture", R.drawable.addpfp)

        // Update UI with user data
        adminname.text = adminName
        adminpfp.setImageResource(profilePictureResId)
    }

    private fun setButtonAction(button: ImageButton, activityClass: Class<*>) {
        button.setOnClickListener {
            button.startAnimation(scaleUp)
            button.startAnimation(scaleDown)
            navigateToActivity(activityClass)
        }
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun logout() {
        // Clear session data
        val sharedPreferences = getSharedPreferences("admin_data", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        // Navigate to MainActivity
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }


    // Function to save visibility state of buttons in SharedPreferences
    private fun saveButtonVisibilityState(key: String, isVisible: Boolean) {
        val sharedPreferences = getSharedPreferences("button_visibility", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, isVisible)
        editor.apply()
    }

    // Function to retrieve visibility state of buttons from SharedPreferences
    private fun getButtonVisibilityState(key: String): Boolean {
        val sharedPreferences = getSharedPreferences("button_visibility", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, true) // Default to true if not found
    }

    // Function to update visibility state of beachcanteenButton in UserTheatreEvents
    private fun updateUserTheatreEventsButtonVisibility(isVisible: Boolean) {
        val sharedPreferences = getSharedPreferences("button_visibility", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_beachcanteen_button_visible", isVisible)
        editor.apply()
    }

}

