package com.example.psycareapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.psycareapp.DetailDiscussionActivity
import com.example.psycareapp.data.DiscussionItem
import com.example.psycareapp.databinding.DiscussionItemBinding
import kotlin.collections.ArrayList

class DiscussionAdapter(private val discussionList: ArrayList<DiscussionItem>, private val onSavedDiscussion: OnSavedDiscussion): RecyclerView.Adapter<DiscussionAdapter.ViewHolder>() {

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
        if (discussion.date != null) {
            holder.binding.timestampDiscussion.setReferenceTime(discussion.date)
        }

        holder.binding.imageView4.setOnClickListener {
            onSavedDiscussion.onClickBookmark(position, holder.binding.imageView4)
        }

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailDiscussionActivity::class.java)
            intentDetail.putExtra("discussionData", discussion)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = discussionList.size

    interface OnSavedDiscussion{
        fun onClickBookmark(position: Int, imgView: ImageView)
    }
}