package com.example.psycareapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.NewsAdapter
import com.example.psycareapp.databinding.ActivityMainBinding
import com.example.psycareapp.repository.Result
import com.example.psycareapp.viewmodel.HomeViewModel
import com.example.psycareapp.viewmodel.ViewModelFactory
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance()
    }
    private val adapterNews = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, getCurrentLocale(this)?.language.toString(), Toast.LENGTH_SHORT).show()

        val language = if(getCurrentLocale(this)?.language.toString() != "in"){
            "us"
        }else{
            "in"
        }

        homeViewModel.getArticles(language).observe(this){
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

        binding.buttonTest.setOnClickListener {
            val intent = Intent(this@MainActivity, TestActivity::class.java)
            startActivity(intent)
        }

    }

    fun getCurrentLocale(context: Context): Locale? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.map_menu -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }
}