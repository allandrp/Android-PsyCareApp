package com.example.psycareapp

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.psycareapp.databinding.ActivityAddDiscussionBinding
import com.example.psycareapp.repository.Result
import com.example.psycareapp.viewmodel.DiscussionViewModel
import com.example.psycareapp.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AddDiscussionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDiscussionBinding
    private lateinit var fbAuth: FirebaseAuth
    private val discussionsViewModel: DiscussionViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityAddDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fbAuth = FirebaseAuth.getInstance()

        try {
            supportActionBar!!.hide()
        }
        catch (e: NullPointerException) {
        }

        binding.btnSendPost.setOnClickListener {
            if(binding.usernamePost.text.toString().isEmpty()){
                binding.usernamePostLayout.error = "Can't Empty"
            }

            if(binding.descDiscussion.text.toString().isEmpty()){
                binding.descDiscussionLayout.error = "Can't Empty"
            }

            if(binding.descDiscussion.text.toString().isNotEmpty() && binding.usernamePost.text.toString().isNotEmpty()){
                val currentUser = fbAuth.currentUser
                if(currentUser != null){
                    postDiscussions(currentUser.uid, binding.usernamePost.text.toString(), binding.descDiscussion.text.toString(), currentUser.email.toString())
                }
            }
        }

        binding.exitAddDiscussion.setOnClickListener {
            finish()
        }
    }

    private fun postDiscussions(uid: String, nickname: String, description: String, email: String){
        discussionsViewModel.postDiscussions(uid, nickname, description, email).observe(this){
            when (it){
                is Result.Loading ->{
                    isLoading(true)
                    binding.usernamePost.isEnabled = false
                    binding.descDiscussion.isEnabled = false
                    binding.btnSendPost.isEnabled = false
                }

                is Result.Success -> {
                    isLoading(false)
                    finish()
                }

                is Result.Error -> {
                    isLoading(false)
                    binding.usernamePost.isEnabled = true
                    binding.descDiscussion.isEnabled = true
                    binding.btnSendPost.isEnabled = true
                    Snackbar.make(binding.root, it.error, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(resources.getColor(R.color.error))
                        .setActionTextColor(resources.getColor(R.color.white))
                        .show()
                }
            }
        }
    }

    private fun isLoading(loading: Boolean){
        if(loading){
            binding.progressBarAddDiscussion.visibility = View.VISIBLE
        }else{
            binding.progressBarAddDiscussion.visibility = View.GONE
        }
    }
}