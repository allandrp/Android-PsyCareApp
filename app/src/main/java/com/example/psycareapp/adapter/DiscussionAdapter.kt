package com.example.psycareapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.psycareapp.DetailDiscussionActivity
import com.example.psycareapp.R
import com.example.psycareapp.data.ArticlesItem
import com.example.psycareapp.data.Discussion
import com.example.psycareapp.data.TestItem
import com.example.psycareapp.databinding.DiscussionItemBinding
import com.example.psycareapp.databinding.QuestionItemBinding
import java.util.*
import kotlin.collections.ArrayList

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

        holder.binding.usernameDiscussion.text = discussion.writer
        holder.binding.descDiscussion.text = discussion.description
        if (discussion.timestamp != null) {
            holder.binding.timestampDiscussion.setReferenceTime(discussion.timestamp)
        }

        holder.binding.imageView4.setOnClickListener {
            onSavedDiscussion.onClickBookmark(position, holder.binding.imageView4)
        }

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailDiscussionActivity::class.java)
            intentDetail.putExtra("idDiscussion", discussion.idDiscussion)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = discussionList.size

    interface OnSavedDiscussion{
        fun onClickBookmark(position: Int, imgView: ImageView)
    }
}