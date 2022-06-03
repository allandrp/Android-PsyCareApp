package com.example.psycareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.DiscussionAdapter
import com.example.psycareapp.data.Discussion
import com.example.psycareapp.databinding.ActivityDiscussionBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DiscussionActivity : AppCompatActivity(), DiscussionAdapter.OnSavedDiscussion{

    private lateinit var binding: ActivityDiscussionBinding
    private val db = Firebase.firestore
    private lateinit var adapterDiscussion: DiscussionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listDiscussion = arrayListOf<Discussion>()
        val docRef = db.collection("discussions").get()
            .addOnSuccessListener { result ->
                if (result != null) {
                    for (document in result) {
                        Log.d("DOC_ID", document.id)

                        listDiscussion.add(
                            Discussion(
                                idCreator = document.data["idCreator"].toString(),
                                description = document.data["description"].toString(),
                                reply = if(document.data["reply"] != null) document.data["reply"] as ArrayList<Discussion> else null,
                                timestamp = document.data["timestamp"] as Long,
                                saved = document.data["saved"] as Boolean
                            )
                        )
                        Log.d("DATA_DISCUSSION", document.data["description"].toString())
                    }
                    adapterDiscussion = DiscussionAdapter(listDiscussion, this)
                    binding.imageView.visibility = View.GONE
                    binding.textView7.visibility = View.GONE
                    binding.rvDiscussion.adapter = adapterDiscussion
                    binding.rvDiscussion.layoutManager = LinearLayoutManager(this)
                    binding.rvDiscussion.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { exception ->
            }

        binding.floatingActionButtonDiscussion.setOnClickListener {
            val intentAdd = Intent(this, AddDiscussionActivity::class.java)
            startActivity(intentAdd)
        }
    }

    override fun onClickBookmark(position: Int, imgView: ImageView) {
    }

}