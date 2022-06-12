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
import com.example.psycareapp.data.ReplyItem
import com.example.psycareapp.databinding.ActivityDetailDiscussionBinding
import com.example.psycareapp.repository.Result
import com.example.psycareapp.viewmodel.DiscussionViewModel
import com.example.psycareapp.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class DetailDiscussionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDiscussionBinding
    private lateinit var fbAuth: FirebaseAuth
    private lateinit var dataDiscussion: DiscussionItem
    private lateinit var adapterReply: ReplyAdapter
    private val discussionsViewModel: DiscussionViewModel by viewModels {
        ViewModelFactory.getInstance()
    }
    private var isFavourite = false
    private lateinit var currentUser: FirebaseUser

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

        currentUser = fbAuth.currentUser!!
        var username = currentUser.email!!.split("@").get(0)

        discussionsViewModel.getUser(currentUser.uid).observe(this){ result->
            when(result){
                is Result.Loading -> {
                    binding.sendButton.isEnabled = false
                }

                is Result.Success -> {
                    binding.sendButton.isEnabled = true
                    username = if(dataDiscussion.idCreator == currentUser.uid){
                        dataDiscussion.nickname
                    }else{
                        result.data.dataUser?.username.toString()
                    }

                    val listFavourite = result.data.dataUser?.favourite
                    if(listFavourite != null){
                        if(listFavourite.contains(dataDiscussion.discussionId)){
                            isFavourite = true
                            binding.favouriteButton.setImageResource(R.drawable.ic_baseline_bookmark_24)
                        }else{
                            isFavourite = false
                            binding.favouriteButton.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                        }
                    }else{
                        isFavourite = false
                        binding.favouriteButton.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                    }
                }

                is Result.Error -> {
                    binding.sendButton.isEnabled = true
                }
            }
        }

        binding.favouriteButton.setOnClickListener {
            if(isFavourite){
                discussionsViewModel.deleteFavourite(currentUser.uid, dataDiscussion.discussionId).observe(this){ state ->
                    when(state){
                        is Result.Loading -> {
                            isFavourite = true
                        }

                        is Result.Success -> {
                            isFavourite = false
                            binding.favouriteButton.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                        }

                        is Result.Error -> {
                            isFavourite = true
                            Toast.makeText(this, state.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                discussionsViewModel.addFavourite(currentUser.uid, dataDiscussion.discussionId).observe(this){ state ->
                    when(state){
                        is Result.Loading -> {
                            isFavourite = false
                        }

                        is Result.Success -> {
                            isFavourite = true
                            binding.favouriteButton.setImageResource(R.drawable.ic_baseline_bookmark_24)
                        }

                        is Result.Error -> {
                            isFavourite = false
                            Toast.makeText(this, state.error, Toast.LENGTH_SHORT).show()
                        }
                    }
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
        dataDiscussion.let { getReply(it) }
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