package com.project.rscov.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ValueEventListener
import com.project.rscov.model.Hospital
import com.project.rscov.repository.Repository

class MainViewModels : ViewModel() {

    fun getHospitals(
        context: Context,
        hospitalsList: ((MutableList<Hospital>) -> Unit),
        rvAdapter: (() -> Unit)
    ): LiveData<ValueEventListener> = Repository.getHospitals(context, hospitalsList, rvAdapter)

}