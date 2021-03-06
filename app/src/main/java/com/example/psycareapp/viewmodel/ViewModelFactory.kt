package com.example.psycareapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.psycareapp.injection.Injection
import com.example.psycareapp.repository.NewsRepository
import com.example.psycareapp.repository.PsyCareRepository

class ViewModelFactory(private val newsRepository: NewsRepository, private val psyCareRepository: PsyCareRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(newsRepository, psyCareRepository) as T
            }

            modelClass.isAssignableFrom(TestViewModel::class.java) -> {
                TestViewModel() as T
            }

            modelClass.isAssignableFrom(PsychologistViewModel::class.java) -> {
                PsychologistViewModel(psyCareRepository) as T
            }

            modelClass.isAssignableFrom(DiscussionViewModel::class.java) -> {
                DiscussionViewModel(psyCareRepository) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(psyCareRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory{
            return instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepositoryNews(), Injection.provideRepositoryPsyCare())
            }.also {
                instance = it
            }
        }
    }
}