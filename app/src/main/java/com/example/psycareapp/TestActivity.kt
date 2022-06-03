package com.example.psycareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.TestAdapter
import com.example.psycareapp.databinding.ActivityTestBinding
import com.example.psycareapp.utils.Utils
import com.example.psycareapp.viewmodel.HomeViewModel
import com.example.psycareapp.viewmodel.TestViewModel
import com.example.psycareapp.viewmodel.ViewModelFactory

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    private lateinit var adapter:TestAdapter
    private val testViewModel: TestViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        testViewModel.getTestItems(MainActivity.getCurrentLocale(this)!!.language)

        adapter = TestAdapter(testViewModel.testItem)
        binding.recyclerViewTest.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTest.adapter = adapter

        binding.buttonSubmitTest.setOnClickListener {
            getAnswer()
        }
    }

    private fun getAnswer(){
        val numberNotAnswered = adapter.isAnswerFull()
        Log.d("NOT_ANSWERED", numberNotAnswered.toString())
        if(numberNotAnswered == 86){
            Toast.makeText(this, testViewModel.testItem.map {it.score}.toString(), Toast.LENGTH_LONG).show()
        }else{
            val y = binding.recyclerViewTest.y + binding.recyclerViewTest.getChildAt(numberNotAnswered).y - 300
            binding.scrollviewTest.scrollTo(0, y.toInt())
        }
    }
}