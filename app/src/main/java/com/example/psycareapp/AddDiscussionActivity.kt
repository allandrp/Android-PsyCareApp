package com.example.psycareapp

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.psycareapp.data.Discussion
import com.example.psycareapp.databinding.ActivityAddDiscussionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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
            if(fbAuth.currentUser != null){
                dbFirestore.collection("discussions")
                    .add(Discussion(
                        idCreator = fbAuth.currentUser!!.uid,
                        description = binding.noBorderEditText.text.toString().trim(),
                        timestamp = Date().time,
                        reply = null,
                        saved = false
                        ))

                    .addOnSuccessListener {
                        Toast.makeText(this, "post successful", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                    .addOnFailureListener { error ->
                        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    }
            }

        }

        binding.exitAddDiscussion.setOnClickListener {
            finish()
        }

    }
}