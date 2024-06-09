package com.example.eventify

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class pfp_popup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pfp_popup)

        val selectedpfp = findViewById<ImageView>(R.id.selectedpfp)

        val selectButtons = listOf<ImageButton>(
            findViewById(R.id.pfp1),
            findViewById(R.id.pfp2),
            findViewById(R.id.pfp3),
            findViewById(R.id.pfp4),
            findViewById(R.id.pfp5),
            findViewById(R.id.pfp6),
            findViewById(R.id.pfp7),
            findViewById(R.id.pfp8),
            findViewById(R.id.pfp9),
            findViewById(R.id.pfp10),
            findViewById(R.id.pfp11),
            findViewById(R.id.pfp12),
            findViewById(R.id.pfp13),
            findViewById(R.id.pfp14),
            findViewById(R.id.pfp15),
            findViewById(R.id.pfp16),
            findViewById(R.id.pfp17),
            findViewById(R.id.pfp18),
            findViewById(R.id.pfp19),
            findViewById(R.id.pfp20),
            findViewById(R.id.pfp21),
            findViewById(R.id.pfp22),
            findViewById(R.id.pfp23),
            findViewById(R.id.pfp24),
            findViewById(R.id.pfp25),
            findViewById(R.id.pfp26),
            findViewById(R.id.pfp27),
            findViewById(R.id.pfp28),
            findViewById(R.id.pfp29),
            findViewById(R.id.pfp30),
            findViewById(R.id.pfp31),
            findViewById(R.id.pfp32)
        )

        var selectedPfpResId: Int? = null

        selectButtons.forEach { button ->
            button.setOnClickListener {
                // Reset the selected state for all buttons
                selectButtons.forEach { it.isSelected = false }
                // Set the selected state for the clicked button
                button.isSelected = true

                // Get the selected image resource id
                selectedPfpResId = resources.getIdentifier(button.tag.toString(), "drawable", packageName)
                // Set the selected image to selectedpfp ImageView
                selectedpfp.setImageResource(selectedPfpResId ?: R.drawable.addpfp)
            }
        }

        val selectButton = findViewById<ImageButton>(R.id.selectButton)
        selectButton.setOnClickListener {
            // Check if any button is selected
            if (selectedPfpResId != null) {
                val resultIntent = Intent()
                resultIntent.putExtra("selectedPfp", selectedPfpResId)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Please select a profile picture first", Toast.LENGTH_SHORT).show()
            }
        }
    }
}