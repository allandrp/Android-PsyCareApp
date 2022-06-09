package com.example.psycareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.PsychologistAdapter
import com.example.psycareapp.data.PsikologItem
import com.example.psycareapp.data.PsikologResponse
import com.example.psycareapp.databinding.ActivityPsychologistBinding
import com.example.psycareapp.repository.Result
import com.example.psycareapp.viewmodel.PsychologistViewModel
import com.example.psycareapp.viewmodel.TestViewModel
import com.example.psycareapp.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class PsychologistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPsychologistBinding
    private val psychologistViewModel: PsychologistViewModel by viewModels {
        ViewModelFactory.getInstance()
    }
    private var listPshycologist: ArrayList<PsikologItem?> = arrayListOf()
    private var adapterPsychologist = PsychologistAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPsychologistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        psychologistViewModel.getPsychologist().observe(this){
            when(it){
                is Result.Success ->{
                    isLoading(false)
                    if(it.data.listPsikolog?.isNotEmpty() == true){
                        listPshycologist = it.data.listPsikolog
                    }else{
                        binding.imageViewEmptyPsychologist.visibility = View.VISIBLE
                        binding.textViewEmptyPsychologist.visibility = View.VISIBLE
                    }

                    adapterPsychologist = PsychologistAdapter(listPshycologist)
                    binding.rvPsychologist.adapter = adapterPsychologist
                    binding.rvPsychologist.layoutManager = LinearLayoutManager(this)
                    binding.rvPsychologist.setHasFixedSize(true)
                }

                is Result.Loading -> {
                    isLoading(true)
                    binding.imageViewEmptyPsychologist.visibility = View.GONE
                    binding.textViewEmptyPsychologist.visibility = View.GONE
                }

                is Result.Error -> {
                    isLoading(false)
                    binding.imageViewEmptyPsychologist.visibility = View.VISIBLE
                    binding.textViewEmptyPsychologist.visibility = View.VISIBLE
                    Snackbar.make(binding.root, it.error, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(resources.getColor(R.color.error))
                        .setActionTextColor(resources.getColor(R.color.white))
                        .show()
                }
            }
        }
    }

    private fun isLoading(loading: Boolean){
        if(loading){
            binding.progressBarPsychologist.visibility = View.VISIBLE
        }else{
            binding.progressBarPsychologist.visibility = View.GONE
        }
    }
}