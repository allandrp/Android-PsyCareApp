package com.example.psycareapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.psycareapp.R
import com.example.psycareapp.data.TestItem
import com.example.psycareapp.databinding.QuestionItemBinding
import com.example.psycareapp.utils.Utils

class TestAdapter(private val testItem: ArrayList<TestItem>): RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    private val numberNotAnswered = arrayListOf<Int>()

    class ViewHolder(val binding: QuestionItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = testItem[position]

        holder.binding.apply {
            questionNumber.text = "${position+1}. "
            questionTest.text = data.question

            when(data.score){
                0 -> choice0.isChecked = true
                1 -> choice1.isChecked = true
                2 -> choice2.isChecked = true
                3 -> choice3.isChecked = true
                else -> numberNotAnswered.add(position)
            }

            groupChoice.setOnCheckedChangeListener { _, id ->
                numberNotAnswered.remove(position)
                var choice = 0
                when(id){
                    choice0.id -> choice = 0
                    choice1.id -> choice = 1
                    choice2.id -> choice = 2
                    choice3.id -> choice = 3
                }

                testItem[position].score = choice
            }
        }
    }

    fun isAnswerFull(): Int{
        return if(numberNotAnswered.isNullOrEmpty()){
            86
        }else{
            numberNotAnswered[0]
        }
    }

    override fun getItemCount(): Int = testItem.size

}