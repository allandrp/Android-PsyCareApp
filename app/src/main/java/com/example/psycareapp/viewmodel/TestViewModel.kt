package com.example.psycareapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.psycareapp.data.TestItem
import com.example.psycareapp.utils.Utils

class TestViewModel: ViewModel() {

    fun getTestItems(language: String): ArrayList<TestItem> = Utils.getTestItems(language)
}