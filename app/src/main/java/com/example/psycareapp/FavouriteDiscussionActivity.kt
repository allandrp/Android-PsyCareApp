package com.example.psycareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.DiscussionAdapter
import com.example.psycareapp.data.DiscussionItem
import com.example.psycareapp.databinding.ActivityDiscussionBinding
import com.example.psycareapp.databinding.ActivityFavouriteDiscussionBinding
import com.example.psycareapp.repository.Result
import com.example.psycareapp.utils.Utils
import com.example.psycareapp.viewmodel.DiscussionViewModel
import com.example.psycareapp.viewmodel.ProfileViewModel
import com.example.psycareapp.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class FavouriteDiscussionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteDiscussionBinding
    private lateinit var adapterDiscussion: DiscussionAdapter
    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance()
    }
    private lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fbAuth = FirebaseAuth.getInstance()
        getAllDiscussion()

    }

    override fun onResume() {
        super.onResume()
        getAllDiscussion()
    }

    private fun getAllDiscussion(){
        val currentUser = fbAuth.currentUser
        if(currentUser != null){
            profileViewModel.getFavourites(currentUser.uid).observe(this){ result ->
                when(result){
                    is Result.Loading -> {
                        Utils.isLoading(binding.progressBarFavourite, true)
                        binding.imageViewEmptyFavourite.visibility = View.GONE
                        binding.textViewEmptyDiscussion.visibility = View.GONE
                    }

                    is Result.Success -> {
                        Utils.isLoading(binding.progressBarFavourite, false)
                        if(result.data.listDiscussions.isNotEmpty()){
                            val listDiscussionSort = arrayListOf<DiscussionItem>()
                            result.data.listDiscussions.sortedByDescending { it.date }.toCollection(listDiscussionSort)
                            adapterDiscussion = DiscussionAdapter(listDiscussionSort)
                            binding.rvFavourite.adapter = adapterDiscussion
                            binding.rvFavourite.layoutManager = LinearLayoutManager(this)
                        }else{
                            binding.imageViewEmptyFavourite.visibility = View.VISIBLE
                            binding.textViewEmptyDiscussion.visibility = View.VISIBLE
                        }
                    }

                    is Result.Error -> {
                        Utils.isLoading(binding.progressBarFavourite, false)
                        binding.imageViewEmptyFavourite.visibility = View.VISIBLE
                        binding.textViewEmptyDiscussion.visibility = View.VISIBLE
                    }
                }
            }
        }else{
            Toast.makeText(this, "user null", Toast.LENGTH_SHORT).show()
        }
    }
}