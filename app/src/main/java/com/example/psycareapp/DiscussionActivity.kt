package com.example.psycareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.DiscussionAdapter
import com.example.psycareapp.databinding.ActivityDiscussionBinding
import com.example.psycareapp.repository.Result
import com.example.psycareapp.viewmodel.DiscussionViewModel
import com.example.psycareapp.viewmodel.ViewModelFactory

class DiscussionActivity : AppCompatActivity(), DiscussionAdapter.OnSavedDiscussion{

    private lateinit var binding: ActivityDiscussionBinding
    private lateinit var adapterDiscussion: DiscussionAdapter
    private val discussionsViewModel: DiscussionViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

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
        discussionsViewModel.getDiscussions().observe(this){
            when(it){

                is Result.Loading -> {
                    isLoading(true)
                    binding.imageView.visibility = View.GONE
                    binding.textViewEmptyDiscussion.visibility = View.GONE
                }

                is Result.Success -> {
                    isLoading(false)
                    binding.floatingActionButtonDiscussion.visibility = View.VISIBLE
                    if(it.data.listDiscussions.isNotEmpty()){
                        adapterDiscussion = DiscussionAdapter(it.data.listDiscussions, this)
                        binding.rvDiscussion.adapter = adapterDiscussion
                        binding.rvDiscussion.layoutManager = LinearLayoutManager(this)
                    }else{
                        binding.imageView.visibility = View.VISIBLE
                        binding.textViewEmptyDiscussion.visibility = View.VISIBLE
                    }
                }

                is Result.Error -> {
                    isLoading(false)
                    binding.imageView.visibility = View.VISIBLE
                    binding.textViewEmptyDiscussion.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onClickBookmark(position: Int, imgView: ImageView) {
    }

    private fun isLoading(loading: Boolean){
        if(loading){
            binding.progressBarDiscussion.visibility = View.VISIBLE
        }else{
            binding.progressBarDiscussion.visibility = View.GONE
        }
    }

}