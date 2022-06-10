package com.example.psycareapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.ReplyAdapter
import com.example.psycareapp.data.DiscussionItem
import com.example.psycareapp.data.DiscussionsResponse
import com.example.psycareapp.data.ReplyItem
import com.example.psycareapp.data.User
import com.example.psycareapp.databinding.ActivityDetailDiscussionBinding
import com.example.psycareapp.repository.Result
import com.example.psycareapp.viewmodel.DiscussionViewModel
import com.example.psycareapp.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class DetailDiscussionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDiscussionBinding
    private lateinit var fbAuth: FirebaseAuth
    private lateinit var dataDiscussion: DiscussionItem
    private lateinit var adapterReply: ReplyAdapter
    private val discussionsViewModel: DiscussionViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.progressBar2.visibility = View.GONE
        fbAuth = FirebaseAuth.getInstance()

        dataDiscussion = intent.getParcelableExtra("discussionData")!!

        binding.dividerLine.visibility = View.VISIBLE
        binding.usernameDetailDiscussion.text = dataDiscussion.nickname
        binding.descDetailDiscussion.text = dataDiscussion.isi
        binding.timestampDiscussion.setReferenceTime(dataDiscussion.date)

        val currentUser = fbAuth.currentUser
        var username = currentUser?.email!!.split("@")?.get(0)

        discussionsViewModel.getUser(currentUser.uid).observe(this){result->
            when(result){
                is Result.Loading -> {
                    binding.sendButton.isEnabled = false
                }

                is Result.Success -> {
                    binding.sendButton.isEnabled = true
                    if(dataDiscussion.idCreator == currentUser.uid){
                        username = dataDiscussion.nickname
                    }else{
                        username = result.data.dataUser?.username.toString()
                    }
                }

                is Result.Error -> {
                    binding.sendButton.isEnabled = true
                }
            }
        }
        binding.sendButton.setOnClickListener {
            imm.hideSoftInputFromWindow(binding.linearLayout.windowToken, 0)

            if (binding.messageEditText.text.toString().trim().isNotEmpty()) {
                discussionsViewModel.postReply(
                    dataDiscussion.discussionId,
                    currentUser.uid,
                    username,
                    binding.messageEditText.text.toString().trim(),
                    currentUser.email!!
                ).observe(this){ result ->
                    when(result){
                        is Result.Success -> {
                            getReply(dataDiscussion)
                            binding.messageEditText.text.clear()
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dataDiscussion?.let { getReply(it) }
    }


    private fun getReply(discussion: DiscussionItem) {
        discussionsViewModel.getReply(discussion.discussionId).observe(this) { result ->
            when (result) {

                is Result.Loading -> {
                    isLoading(true)
                }

                is Result.Success -> {
                    isLoading(false)
                    val listReplySort = arrayListOf<ReplyItem>()
                    result.data.listReply?.sortedByDescending { it.date }?.toCollection(listReplySort)
                    adapterReply = result.data.listReply?.let { ReplyAdapter(listReplySort, dataDiscussion) }!!
                    binding.rvReply.adapter = adapterReply
                    binding.rvReply.layoutManager = LinearLayoutManager(this)
                }

                is Result.Error -> {
                    isLoading(false)
                    Snackbar.make(binding.root, result.error, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(resources.getColor(R.color.error))
                        .setActionTextColor(resources.getColor(R.color.white))
                        .show()
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }
}