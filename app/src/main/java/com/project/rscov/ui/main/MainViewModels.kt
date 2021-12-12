package com.project.rscov.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.rscov.model.Hospital

class MainViewModels : ViewModel() {

    private val hospitals = MutableLiveData<MutableList<Hospital>>()

    fun setHospitals(data: MutableList<Hospital>?){
        hospitals.postValue(data)
    }

    fun getHospitals(): LiveData<MutableList<Hospital>> = hospitals

}