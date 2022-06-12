package com.example.psycareapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.psycareapp.data.DataTestResultItem
import com.example.psycareapp.databinding.ResultItemBinding

class TestResultAdapter(private val testResultItem: ArrayList<DataTestResultItem>): RecyclerView.Adapter<TestResultAdapter.ViewHolder>() {
    class ViewHolder (val binding: ResultItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = testResultItem[position]

        with(holder.binding){
            category.text = result.type
            statusCondition.text = result.severity
            description.text = result.explanation

            result.recommendations.forEach {
                whatToDo.text = "-" + it + "\n \n" + whatToDo.text
            }
        }
    }

    override fun getItemCount(): Int = testResultItem.size
}