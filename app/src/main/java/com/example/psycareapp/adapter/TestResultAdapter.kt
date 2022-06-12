package com.example.psycareapp.adapter

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.psycareapp.R
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
        val bulletGap = dp(10, holder.binding).toInt()
        with(holder.binding){
            category.text = result.type.uppercase()
            when(result.severity){
                "normal" -> {
                    statusCondition.setTextColor(holder.binding.root.resources.getColor(R.color.normal_color))
                }

                "moderate" -> {
                    statusCondition.setTextColor(holder.binding.root.resources.getColor(R.color.moderate_color))
                }

                "severe" -> {
                    statusCondition.setTextColor(holder.binding.root.resources.getColor(R.color.severe_color))
                }
            }

            val ssb = SpannableStringBuilder()
            for (i in 0 until result.recommendations.size) {
                val line: String = result.recommendations.get(i)
                val ss = SpannableString(line)
                ss.setSpan(BulletSpan(bulletGap), 0, line.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                ssb.append(ss)

                //avoid last "\n"
                if (i + 1 < result.recommendations.size) ssb.append("\n")
            }

            val severity = result.severity
            statusCondition.text = severity[0].toString().uppercase() + severity.substring(1)
            description.text = result.explanation

            whatToDo.text = ssb

        }


    }

    fun dp(dp: Int, binding: ResultItemBinding): Float {
        return binding.root.resources.getDisplayMetrics().density * dp
    }
    override fun getItemCount(): Int = testResultItem.size
}