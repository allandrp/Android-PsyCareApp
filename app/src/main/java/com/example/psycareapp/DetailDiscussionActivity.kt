package com.example.psycareapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.psycareapp.adapter.ReplyAdapter
import com.example.psycareapp.data.User
import com.example.psycareapp.databinding.ActivityDetailDiscussionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class DetailDiscussionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDiscussionBinding
    private lateinit var fbAuth: FirebaseAuth
    private val dbFirestore = Firebase.firestore
    private lateinit var idDiscussion: String
//    private lateinit var dataDiscussion: Discussion
    private lateinit var dataUser: User
    private lateinit var adapterReply: ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        fbAuth = FirebaseAuth.getInstance()

        idDiscussion = intent.getStringExtra("idDiscussion").toString()
        getDiscussion(idDiscussion)

        binding.sendButton.setOnClickListener {
            imm.hideSoftInputFromWindow(binding.linearLayout.windowToken, 0)
            if (binding.messageEditText.text.toString().trim().isNotEmpty()) {
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDiscussion(idDiscussion)
    }


    private fun getDiscussion(idDiscussion: String) {
        binding.avatarDetailDiscussion.visibility = View.GONE
        binding.dividerLine.visibility = View.GONE

    }
}