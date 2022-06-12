package com.example.psycareapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.psycareapp.data.DataItem
import com.example.psycareapp.databinding.HistoryItemBinding

class TestHistoryAdapter(private val testHistory: ArrayList<DataItem>): RecyclerView.Adapter<TestHistoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: HistoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val testHistoryItem = testHistory[position]

        with(holder.binding){
            when(testHistoryItem.hasil.anxiety){
                0 -> anxietyResultHistory.text = "Normal"
                1 -> anxietyResultHistory.text = "Moderate"
                2 -> anxietyResultHistory.text = "Severe"
            }

            when(testHistoryItem.hasil.stress){
                0 -> stressResultHistory.text = "Normal"
                1 -> stressResultHistory.text = "Moderate"
                2 -> stressResultHistory.text = "Severe"
            }

            when(testHistoryItem.hasil.depresi){
                0 -> depressionResultHistory.text = "Normal"
                1 -> depressionResultHistory.text = "Moderate"
                2 -> depressionResultHistory.text = "Severe"
            }
        }
    }

    override fun getItemCount(): Int = testHistory.size
}