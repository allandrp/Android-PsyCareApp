package com.example.psycareapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.psycareapp.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        val userId = intent.getStringExtra("userId")

        binding.usernameProfile.setText(username.toString())

        binding.historyUserProfileButton.setOnClickListener {
            val intent = Intent(this, TestHistoryActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        binding.favouriteDiscussionButton.setOnClickListener {
            val intent = Intent(this, FavouriteDiscussionActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }
}