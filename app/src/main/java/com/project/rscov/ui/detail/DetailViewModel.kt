package com.project.rscov.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.rscov.model.Hospital

class DetailViewModel: ViewModel() {

    private val hospital = MutableLiveData<Hospital>()

    fun setHospital(data: Hospital?){
        hospital.postValue(data)
    }

    fun getHospital(): LiveData<Hospital> = hospital
}