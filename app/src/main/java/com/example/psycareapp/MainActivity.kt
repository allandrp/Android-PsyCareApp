package com.example.psycareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.NewsAdapter
import com.example.psycareapp.databinding.ActivityMainBinding
import com.example.psycareapp.repository.Result
import com.example.psycareapp.viewmodel.HomeViewModel
import com.example.psycareapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private val adapterNews = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeViewModel.getArticles().observe(this){
            when(it){
                is Result.Success -> {
                    binding.recyclerViewNews.adapter = adapterNews
                    binding.recyclerViewNews.layoutManager = LinearLayoutManager(this)
                    binding.recyclerViewNews.setHasFixedSize(true)
                    binding.recyclerViewNews.isNestedScrollingEnabled = false
                    adapterNews.submitList(it.data.articles)
                }
            }
        }

    }
}