package com.example.psycareapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.psycareapp.data.TestItem
import com.example.psycareapp.utils.Utils

class TestViewModel: ViewModel() {

    private val testItems = Utils.getTestItems()

    fun getTestItems(): ArrayList<TestItem> = testItems
}