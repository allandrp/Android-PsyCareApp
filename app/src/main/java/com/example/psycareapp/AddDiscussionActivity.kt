package com.example.psycareapp

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.psycareapp.databinding.ActivityAddDiscussionBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AddDiscussionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDiscussionBinding
    private lateinit var fbAuth: FirebaseAuth
    private val dbFirestore = Firebase.firestore

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
                binding.progressBarAddDiscussion.visibility = View.VISIBLE
                binding.usernamePost.isEnabled = false
                binding.descDiscussion.isEnabled = false
                binding.btnSendPost.isEnabled = false

                if(fbAuth.currentUser != null){
                    dbFirestore.collection("discussions")
                        .add(
                            mapOf("idCreator" to fbAuth.currentUser!!.uid,
                            "writer" to binding.usernamePost.text.toString().trim(),
                            "description" to binding.descDiscussion.text.toString().trim(),
                            "timestamp" to Date().time,
                            "reply" to null)
                        )

                        .addOnSuccessListener {
                            Toast.makeText(this, "post successful", Toast.LENGTH_SHORT).show()
                            binding.progressBarAddDiscussion.visibility = View.GONE
                            binding.usernamePost.isEnabled = true
                            binding.descDiscussion.isEnabled = true
                            binding.btnSendPost.isEnabled = true
                            finish()
                        }

                        .addOnFailureListener { error ->
                            binding.progressBarAddDiscussion.visibility = View.GONE
                            binding.usernamePost.isEnabled = true
                            binding.descDiscussion.isEnabled = true
                            binding.btnSendPost.isEnabled = true

                            Snackbar.make(binding.root, error.message.toString(), Snackbar.LENGTH_LONG)
                                .setBackgroundTint(resources.getColor(R.color.error))
                                .setActionTextColor(resources.getColor(R.color.white))
                                .show()
                        }
                }
            }
        }

        binding.exitAddDiscussion.setOnClickListener {
            finish()
        }

    }
}