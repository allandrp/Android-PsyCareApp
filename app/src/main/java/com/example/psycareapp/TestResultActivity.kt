package com.example.psycareapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.TestResultAdapter
import com.example.psycareapp.data.DataTestResultItem
import com.example.psycareapp.databinding.ActivityTestResultBinding

class TestResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityTestResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataTestResult = intent.getParcelableArrayListExtra<DataTestResultItem>("testResultItems")
        Log.d("testResultItems", dataTestResult.toString())

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        } else{
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }

        val adapter = dataTestResult?.let { TestResultAdapter(it) }
        binding.recyclerView.adapter = adapter

        binding.doneButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}