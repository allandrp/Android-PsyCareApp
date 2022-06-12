package com.example.psycareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.TestAdapter
import com.example.psycareapp.data.ApiConfig
import com.example.psycareapp.data.ApiPsyCareService
import com.example.psycareapp.data.Predict
import com.example.psycareapp.data.PredictResponse
import com.example.psycareapp.databinding.ActivityTestBinding
import com.example.psycareapp.utils.Utils
import com.example.psycareapp.viewmodel.HomeViewModel
import com.example.psycareapp.viewmodel.TestViewModel
import com.example.psycareapp.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    private lateinit var adapter:TestAdapter
    private lateinit var fbAuth: FirebaseAuth
    private val testViewModel: TestViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        testViewModel.getTestItems(MainActivity.getCurrentLocale(this)!!.language)

        fbAuth = FirebaseAuth.getInstance()
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
        val currentUser = fbAuth.currentUser
        val coba = listOf<Int>(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)

        val listAnswer = testViewModel.testItem.map {it.score}
//        if(numberNotAnswered == 86){
            if(currentUser != null){
                ApiConfig.getApiPsyCare().predictData(currentUser.uid, Predict(listAnswer as List<Int>)).enqueue(object: Callback<PredictResponse>{
                    override fun onResponse(
                        call: Call<PredictResponse>,
                        response: Response<PredictResponse>
                    ) {
                        if(response.isSuccessful){
//                            Toast.makeText(this@TestActivity, testViewModel.testItem.map {it.score}.toString(), Toast.LENGTH_LONG).show()
                            Log.d("HASIL_PREDICT", response.body()?.data.toString())
                        }else{
                            Toast.makeText(this@TestActivity, "failed", Toast.LENGTH_LONG).show()
                        }

                    }

                    override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                        Toast.makeText(this@TestActivity, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }


//        }else{
//            val y = binding.recyclerViewTest.y + binding.recyclerViewTest.getChildAt(numberNotAnswered).y - 300
//            binding.scrollviewTest.scrollTo(0, y.toInt())
//        }
    }
}