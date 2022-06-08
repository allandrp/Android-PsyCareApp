package com.example.psycareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.PsychologistAdapter
import com.example.psycareapp.data.PsikologResponse
import com.example.psycareapp.databinding.ActivityPsychologistBinding
import com.example.psycareapp.repository.Result
import com.example.psycareapp.viewmodel.PsychologistViewModel
import com.example.psycareapp.viewmodel.TestViewModel
import com.example.psycareapp.viewmodel.ViewModelFactory

class PsychologistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPsychologistBinding
    private val psychologistViewModel: PsychologistViewModel by viewModels {
        ViewModelFactory.getInstance()
    }
    private var listPshycologist: ArrayList<PsikologResponse> = arrayListOf()
    private var adapterPsychologist = PsychologistAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPsychologistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        psychologistViewModel.getPsychologist().observe(this){
            when(it){
                is Result.Success ->{
                    listPshycologist = it.data
                    Log.d("LIST_PSY", listPshycologist.toString())
                    adapterPsychologist = PsychologistAdapter(listPshycologist)
                    binding.rvPsychologist.adapter = adapterPsychologist
                    binding.rvPsychologist.layoutManager = LinearLayoutManager(this)
                    binding.rvPsychologist.setHasFixedSize(true)
                }

                is Result.Loading -> {
                    Log.d("LIST_PSY", "loading")
                }

                is Result.Error -> {
                    Log.d("LIST_PSY", it.error)
                }
            }
        }
    }
}