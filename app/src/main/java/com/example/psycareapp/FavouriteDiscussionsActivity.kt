package com.example.psycareapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.psycareapp.databinding.ActivityFavouriteDiscussionsBinding

class FavouriteDiscussionsActivity : AppCompatActivity() {

    lateinit var binding: ActivityFavouriteDiscussionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteDiscussionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")

        binding.usernameFavouriteDiscussion.text = username.toString()
    }
}