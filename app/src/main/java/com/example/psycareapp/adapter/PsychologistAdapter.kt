package com.example.psycareapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.psycareapp.MapsActivity
import com.example.psycareapp.data.Discussion
import com.example.psycareapp.data.PsikologResponse
import com.example.psycareapp.databinding.DiscussionItemBinding
import com.example.psycareapp.databinding.PsychologistItemBinding
import java.util.ArrayList

class PsychologistAdapter(private val psychologistList: ArrayList<PsikologResponse>): RecyclerView.Adapter<PsychologistAdapter.ViewHolder>() {

    class ViewHolder(val binding: PsychologistItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            PsychologistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val psychologist = psychologistList[position]

        holder.binding.namePsycholog.text = psychologist.name
        var expertise = ""
        psychologist.expertise?.forEach {
            expertise = "$expertise | $it"
        }

        holder.binding.expertisePsycholog.text = expertise
        holder.binding.locationPsycholog.setOnClickListener {
            val intentMap = Intent(holder.itemView.context, MapsActivity::class.java)
            intentMap.putExtra("psychologist", true)
            intentMap.putExtra("name", psychologist?.name)
            intentMap.putExtra("lat", psychologist.lat?.toDouble())
            intentMap.putExtra("lng", psychologist.lng?.toDouble())
            holder.itemView.context.startActivity(intentMap)
        }
    }

    override fun getItemCount(): Int = psychologistList.size

}