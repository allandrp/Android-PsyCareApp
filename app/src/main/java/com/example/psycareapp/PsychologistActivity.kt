package com.example.psycareapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psycareapp.adapter.PsychologistAdapter
import com.example.psycareapp.data.PsikologItem
import com.example.psycareapp.databinding.ActivityPsychologistBinding
import com.example.psycareapp.repository.Result
import com.example.psycareapp.utils.Utils
import com.example.psycareapp.viewmodel.PsychologistViewModel
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
                    Utils.isLoading(binding.progressBarPsychologist, false)
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
                    Utils.isLoading(binding.progressBarPsychologist, true)
                    binding.imageViewEmptyPsychologist.visibility = View.GONE
                    binding.textViewEmptyPsychologist.visibility = View.GONE
                }

                is Result.Error -> {
                    Utils.isLoading(binding.progressBarPsychologist,false)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        menu.findItem(R.id.logout_menu).isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.map_menu -> {
                val intent = Intent(this, MapsActivity::class.java)
                intent.putParcelableArrayListExtra("listPsychologist", listPshycologist)
                startActivity(intent)
                true
            }

            else -> true
        }
    }
}