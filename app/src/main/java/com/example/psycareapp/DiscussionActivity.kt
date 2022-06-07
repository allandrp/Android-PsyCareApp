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
import com.google.android.material.snackbar.Snackbar
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

        getAllDiscussion()

        binding.floatingActionButtonDiscussion.setOnClickListener {
            val intentAdd = Intent(this, AddDiscussionActivity::class.java)
            startActivity(intentAdd)
        }
    }

    override fun onResume() {
        super.onResume()
        getAllDiscussion()
    }

    private fun getAllDiscussion(){
        val listDiscussion = arrayListOf<Discussion>()
        val docRef = db.collection("discussions").get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    for (document in result) {
                        Log.d("DOC_ID", document.id)

                        listDiscussion.add(
                            Discussion(
                                idDiscussion = document.id,
                                idCreator = document.data["idCreator"].toString(),
                                writer = document.data["writer"].toString(),
                                description = document.data["description"].toString(),
                                reply = if(document.data["reply"] != null) document.data["reply"] as ArrayList<Discussion> else null,
                                timestamp = document.data["timestamp"] as Long
                            )
                        )
                        Log.d("DATA_DISCUSSION", document.data["description"].toString())
                    }

                    val listDiscussionSort = arrayListOf<Discussion>()
                    listDiscussion.sortedByDescending { it.timestamp }.toCollection(listDiscussionSort)

                    binding.progressBarDiscussion.visibility = View.GONE
                    binding.imageView.visibility = View.GONE
                    binding.textView7.visibility = View.GONE
                    binding.floatingActionButtonDiscussion.visibility = View.VISIBLE
                    adapterDiscussion = DiscussionAdapter(listDiscussionSort, this)
                    binding.rvDiscussion.adapter = adapterDiscussion
                    binding.rvDiscussion.layoutManager = LinearLayoutManager(this)
                    binding.rvDiscussion.visibility = View.VISIBLE

                }else{
                    binding.progressBarDiscussion.visibility = View.GONE
                    binding.floatingActionButtonDiscussion.visibility = View.VISIBLE
                    binding.imageView.visibility = View.VISIBLE
                    binding.textView7.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { exception ->
                binding.progressBarDiscussion.visibility = View.GONE
                binding.floatingActionButtonDiscussion.visibility = View.VISIBLE
                Snackbar.make(binding.root, exception.message.toString(), Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.error))
                    .setActionTextColor(resources.getColor(R.color.white))
                    .show()
            }
    }

    override fun onClickBookmark(position: Int, imgView: ImageView) {
    }

}