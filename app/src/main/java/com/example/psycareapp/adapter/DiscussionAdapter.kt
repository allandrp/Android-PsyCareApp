package com.example.psycareapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.psycareapp.DetailDiscussionActivity
import com.example.psycareapp.data.DiscussionItem
import com.example.psycareapp.databinding.DiscussionItemBinding

class DiscussionAdapter(private val discussionList: ArrayList<DiscussionItem>): RecyclerView.Adapter<DiscussionAdapter.ViewHolder>() {

    class ViewHolder(val binding: DiscussionItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =DiscussionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val discussion = discussionList[position]

        holder.binding.usernameDiscussion.text = discussion.nickname
        holder.binding.descDiscussion.text = discussion.isi
        holder.binding.timestampDiscussion.setReferenceTime(discussion.date)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailDiscussionActivity::class.java)
            intentDetail.putExtra("discussionData", discussion)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = discussionList.size

}