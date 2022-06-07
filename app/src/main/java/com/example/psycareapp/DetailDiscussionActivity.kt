package com.example.psycareapp

import android.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.ReplyAdapter
import com.example.psycareapp.data.Discussion
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
    private lateinit var dataDiscussion: Discussion
    private lateinit var dataUser: User
    private lateinit var adapterReply: ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        fbAuth = FirebaseAuth.getInstance()

        getCurrentUserData()
        idDiscussion = intent.getStringExtra("idDiscussion").toString()
        getDiscussion(idDiscussion)

        binding.sendButton.setOnClickListener {
            imm.hideSoftInputFromWindow(binding.linearLayout.windowToken, 0)
            if (binding.messageEditText.text.toString().trim().isNotEmpty()) {
                dbFirestore.collection("discussions").document(idDiscussion)
                    .update(
                        "reply", FieldValue.arrayUnion(
                            mapOf(
                                "idCreator" to fbAuth.currentUser!!.uid,
                                "writer" to if (dataDiscussion.idCreator.equals(fbAuth.currentUser!!.uid)) dataDiscussion.writer else fbAuth.currentUser!!.email,
                                "description" to binding.messageEditText.text.toString().trim(),
                                "timestamp" to Date().time,
                                "reply" to null
                            )
                        )
                    )

                    .addOnSuccessListener {
                        binding.messageEditText.text.clear()
                        getDiscussion(idDiscussion)
                        Toast.makeText(this, "send success", Toast.LENGTH_SHORT).show()
                    }

                    .addOnFailureListener {
                        getDiscussion(idDiscussion)
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDiscussion(idDiscussion)
    }

    private fun getCurrentUserData() {
        if (fbAuth.currentUser != null) {
            dbFirestore.collection("users").document(fbAuth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        dataUser = User(
                            it.data?.get("id")?.toString() ?: "1",
                            it.data?.get("username").toString()
                        )

                        Log.d("DATA_DETAIL_USER", dataUser.toString())
                    }

                }

                .addOnFailureListener {
                    finish()
                }
        }
    }

    private fun getDiscussion(idDiscussion: String) {
        binding.avatarDetailDiscussion.visibility = View.GONE
        binding.dividerLine.visibility = View.GONE

        dbFirestore.collection("discussions").document(idDiscussion).get()
            .addOnSuccessListener {
                dataDiscussion = it.toObject(Discussion::class.java)!!

                dataDiscussion.timestamp?.let { it1 ->
                    binding.timestampDiscussion.setReferenceTime(
                        it1
                    )
                }

                adapterReply = if (dataDiscussion.reply != null) {
                    ReplyAdapter(dataDiscussion.reply!!)
                } else {
                    ReplyAdapter(arrayListOf())
                }

                binding.rvReply.adapter = adapterReply
                binding.rvReply.layoutManager = LinearLayoutManager(this)

                binding.avatarDetailDiscussion.visibility = View.VISIBLE
                binding.dividerLine.visibility = View.VISIBLE
                binding.rvReply.visibility = View.VISIBLE
                binding.progressBar2.visibility = View.GONE

                Log.d("LIST_REPLY", dataDiscussion.reply.toString())
                binding.usernameDetailDiscussion.text = dataDiscussion.writer
                binding.descDetailDiscussion.text = dataDiscussion.description
            }
    }
}