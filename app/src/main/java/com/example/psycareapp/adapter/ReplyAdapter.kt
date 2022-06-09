package com.example.psycareapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.psycareapp.data.DiscussionItem
import com.example.psycareapp.databinding.DiscussionItemBinding

class ReplyAdapter(private val discussionList: ArrayList<DiscussionItem>): RecyclerView.Adapter<ReplyAdapter.ViewHolder>() {

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

        holder.binding.usernameDiscussion.text = discussion.nickname
        holder.binding.descDiscussion.text = discussion.isi
        if (discussion.date != null) {
            holder.binding.timestampDiscussion.setReferenceTime(discussion.date)
        }

        holder.binding.imageView4.visibility = View.GONE
    }

    override fun getItemCount(): Int = discussionList.size

}