package com.example.psycareapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.TestAdapter
import com.example.psycareapp.data.ApiConfig
import com.example.psycareapp.data.DataTestResultItem
import com.example.psycareapp.data.PostPredictObject
import com.example.psycareapp.data.TestResponse
import com.example.psycareapp.databinding.ActivityTestBinding
import com.example.psycareapp.utils.Utils
import com.example.psycareapp.viewmodel.TestViewModel
import com.example.psycareapp.viewmodel.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val userId = intent.getStringExtra("userId")

        testViewModel.getTestItems(MainActivity.getCurrentLocale(this)!!.language)

        adapter = TestAdapter(testViewModel.testItem)
        binding.recyclerViewTest.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTest.adapter = adapter

        binding.buttonSubmitTest.setOnClickListener {
            getAnswer(userId!!)
        }
    }

    private fun getAnswer(userId: String){
        val numberNotAnswered = adapter.isAnswerFull()
        Log.d("NOT_ANSWERED", numberNotAnswered.toString())
        if(numberNotAnswered == 86){

            val data: PostPredictObject = testViewModel.testItem.map { it.score }
                .let { PostPredictObject(it as List<Int>) }

            postTest(userId, data)
        }else{
            val y = binding.recyclerViewTest.y + binding.recyclerViewTest.getChildAt(numberNotAnswered).y - 300
            binding.scrollviewTest.scrollTo(0, y.toInt())
        }
    }

    private fun postTest(userId: String, data:PostPredictObject){

        Utils.isLoading(binding.progressBar3, true)
        binding.buttonSubmitTest.isEnabled = false
        val listItem = arrayListOf<List<DataTestResultItem>>()
        val testResultItem = arrayListOf<DataTestResultItem>()

        ApiConfig.getApiPsyCare().postTest(
            userId,
            data
        ).enqueue(object : Callback<TestResponse>{
            override fun onResponse(call: Call<TestResponse>, response: Response<TestResponse>) {
                Log.d("onResponseTA", "Masuk Response")
                Log.d("onResponseTAUid", userId)

                if (response.isSuccessful){
                    Log.d("onResponseSucc", "Response Successful")
                    val testResult = response.body()
                    if (testResult != null){
                        listItem.add(testResult.data)
                        for (item in listItem){
                            item.forEach {
                                testResultItem.add(it)
                            }
                        }
                        Log.d("onResponseTAData",testResultItem.toString())
                        val intent = Intent(this@TestActivity, TestResultActivity::class.java)
                        intent.putParcelableArrayListExtra("testResultItems", testResultItem)
                        startActivity(intent)
                        Utils.isLoading(binding.progressBar3, false)
                        finish()
                    }
                    else{
                        Utils.isLoading(binding.progressBar3, false)
                        binding.buttonSubmitTest.isEnabled = true
                        Log.d("onResponseTAData", response.body().toString())
                    }
                }
                else{
                    Utils.isLoading(binding.progressBar3, false)
                    binding.buttonSubmitTest.isEnabled = true
                    Log.d("onResponseSucc", "Response Unsuccessful")
                }


            }

            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                Utils.isLoading(binding.progressBar3, false)
                binding.buttonSubmitTest.isEnabled = true
                Log.d("FailureHistoryTA", t.message.toString())
            }
        })
    }
}