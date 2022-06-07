package com.example.psycareapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.psycareapp.DetailDiscussionActivity
import com.example.psycareapp.data.Discussion
import com.example.psycareapp.databinding.DiscussionItemBinding

class ReplyAdapter(private val discussionList: ArrayList<Discussion>): RecyclerView.Adapter<ReplyAdapter.ViewHolder>() {

    class ViewHolder(val binding: DiscussionItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            DiscussionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val discussion = discussionList[position]

        holder.binding.usernameDiscussion.text = discussion.writer
        holder.binding.descDiscussion.text = discussion.description
        if (discussion.timestamp != null) {
            holder.binding.timestampDiscussion.setReferenceTime(discussion.timestamp)
        }

        holder.binding.imageView4.visibility = View.GONE
    }

    override fun getItemCount(): Int = discussionList.size

}