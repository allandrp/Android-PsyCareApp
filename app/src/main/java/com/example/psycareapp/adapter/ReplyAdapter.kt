package com.example.psycareapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.psycareapp.data.DiscussionItem
import com.example.psycareapp.data.ReplyItem
import com.example.psycareapp.databinding.ReplyItemBinding

class ReplyAdapter(private val discussionList: ArrayList<ReplyItem>, private val discussionClick: DiscussionItem): RecyclerView.Adapter<ReplyAdapter.ViewHolder>() {

    class ViewHolder(val binding: ReplyItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ReplyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val discussion = discussionList[position]

        holder.binding.usernameDiscussion.text = discussion.nickname
        holder.binding.descDiscussion.text = discussion.description
        if (discussion.date != null) {
            holder.binding.timestampDiscussion.setReferenceTime(discussion.date)
        }

        var email = discussion.email
        val first2: String = (email?.get(0).toString()) + (email?.get(1).toString())

        email = email?.substring(2)
        email = email?.replace("[^@]".toRegex(), "*")


        if(discussionClick.idCreator == discussion.idCreator){
            holder.binding.creatorMark.visibility = View.VISIBLE
            holder.binding.textView10.visibility = View.GONE
        }else{
            holder.binding.creatorMark.visibility = View.GONE
            holder.binding.textView10.text = "$first2$email"
        }
    }

    override fun getItemCount(): Int = discussionList.size

}