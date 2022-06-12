package com.example.psycareapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.NewsAdapter
import com.example.psycareapp.databinding.ActivityMainBinding
import com.example.psycareapp.repository.Result
import com.example.psycareapp.utils.Utils
import com.example.psycareapp.viewmodel.HomeViewModel
import com.example.psycareapp.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance()
    }
    private val adapterNews = NewsAdapter()
    private lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fbAuth = FirebaseAuth.getInstance()

        val currentUser = fbAuth.currentUser
        if(currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
        }else{
            homeViewModel.getUser(currentUser.uid).observe(this){ result ->
                when(result){
                    is Result.Success -> {
                        val username = result.data.dataUser?.username
                        if(username != null){
                            binding.greetingText.text = getString(R.string.greeting_user_text, username)
                        }else{
                            binding.greetingText.text = getString(R.string.greeting_user_text, fbAuth.currentUser!!.email?.split("@")?.get(0))
                        }
                    }

                    is Result.Error -> {
                        binding.greetingText.text = getString(R.string.greeting_user_text, fbAuth.currentUser!!.email?.split("@")?.get(0))
                    }
                }
            }

        }

        val language = if(getCurrentLocale(this)?.language.toString() != "in"){
            "us"
        }else{
            "id"
        }

        homeViewModel.getArticles(language).observe(this){
            when(it){
                is Result.Success -> {
                    Utils.isLoading(binding.progressBar, false)
                    binding.recyclerViewNews.adapter = adapterNews
                    binding.recyclerViewNews.layoutManager = LinearLayoutManager(this)
                    binding.recyclerViewNews.setHasFixedSize(true)
                    binding.recyclerViewNews.isNestedScrollingEnabled = false
                    adapterNews.submitList(it.data.articles)
                }

                is Result.Error -> {
                    Utils.isLoading(binding.progressBar, false)
                }

                is Result.Loading ->{
                    Utils.isLoading(binding.progressBar, true)
                }
            }
        }

        binding.buttonTest.setOnClickListener {
            val intent = Intent(this@MainActivity, TestActivity::class.java)
            startActivity(intent)
        }

        binding.buttonPshycologist.setOnClickListener {
            startActivity(Intent(this, PsychologistActivity::class.java))
        }
        binding.buttonDiscussion.setOnClickListener {
            val intent = Intent(this@MainActivity, DiscussionActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        menu.findItem(R.id.map_menu).isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.map_menu -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.logout_menu -> {
                fbAuth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                true
            }

            else -> true
        }
    }

    companion object{
        fun getCurrentLocale(context: Context): Locale? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.resources.configuration.locales.get(0)
            } else {
                context.resources.configuration.locale
            }
        }
    }
}