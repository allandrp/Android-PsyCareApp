package com.example.psycareapp

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.TestHistoryAdapter
import com.example.psycareapp.data.ApiConfig
import com.example.psycareapp.data.DataItem
import com.example.psycareapp.data.TestHistoryResponse
import com.example.psycareapp.databinding.ActivityTestHistoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestHistoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityTestHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        val userId = intent.getStringExtra("userId")
        Log.d("UserID", userId!!)

        binding.usernameTestHistory.text = username.toString()

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.rvHistory.layoutManager = GridLayoutManager(this, 2)
        } else{
            binding.rvHistory.layoutManager = LinearLayoutManager(this)
        }

        getTestHistory(userId)
    }

    private fun getTestHistory(userId: String){

        val listItem = arrayListOf<List<DataItem>>()
        val historyItem = arrayListOf<DataItem>()

        ApiConfig.getApiPsyCare().getTestHistory(userId).enqueue(object : Callback<TestHistoryResponse>{
            override fun onResponse(
                call: Call<TestHistoryResponse>,
                response: Response<TestHistoryResponse>
            ) {
                Log.d("onResponse", "Masuk Response")
                Log.d("onResponseUid", userId)
                val historyResponse = response.body()

                if (historyResponse != null){
                    listItem.add(historyResponse.data)
                    for(item in listItem){
                        item.forEach {
                            historyItem.add(it)
                        }
                    }
                    Log.d("historyItem", historyItem.toString())
                    val adapter = TestHistoryAdapter(historyItem)
                    binding.rvHistory.adapter = adapter
                }
                else{
                    Log.d("onResponseTAData", response.body().toString())
                }
            }

            override fun onFailure(call: Call<TestHistoryResponse>, t: Throwable) {
                Log.d("FailureHistory", t.message.toString())
                Log.d("onFailure", "Masuk Failure")
            }
        })
    }
}