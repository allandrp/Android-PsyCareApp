package com.example.psycareapp.adapter

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.psycareapp.R
import com.example.psycareapp.data.ArticlesItem
import com.example.psycareapp.data.Discussion
import com.example.psycareapp.data.TestItem
import com.example.psycareapp.databinding.DiscussionItemBinding
import com.example.psycareapp.databinding.QuestionItemBinding

class DiscussionAdapter(private val discussionList: ArrayList<Discussion>, private val onSavedDiscussion: OnSavedDiscussion): RecyclerView.Adapter<DiscussionAdapter.ViewHolder>() {

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

        holder.binding.usernameDiscussion.text = "Anonymous"
        holder.binding.descDiscussion.text = discussion.description
        if (discussion.timestamp != null) {
            holder.binding.timestampDiscussion.text = DateUtils.getRelativeTimeSpanString(discussion.timestamp)
        }

        if(discussion.saved){
            holder.binding.imageView4.setImageResource(R.drawable.ic_baseline_bookmark_24)
        }

        holder.binding.imageView4.setOnClickListener {
            onSavedDiscussion.onClickBookmark(position, holder.binding.imageView4)
        }
    }

    override fun getItemCount(): Int = discussionList.size

    interface OnSavedDiscussion{
        fun onClickBookmark(position: Int, imgView: ImageView)
    }
}