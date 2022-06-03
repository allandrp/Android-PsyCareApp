package com.example.psycareapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.psycareapp.data.TestItem
import com.example.psycareapp.utils.Utils

class TestViewModel: ViewModel() {

    var testItem: ArrayList<TestItem> = ArrayList()

    fun getTestItems(language: String){
        testItem = Utils.getTestItems(language)
    }

}