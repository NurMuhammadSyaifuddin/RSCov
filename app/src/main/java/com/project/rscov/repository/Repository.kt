package com.project.rscov.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.rscov.model.Hospital
import com.project.rscov.utils.showErrorDialog

object Repository {

    private var result = MediatorLiveData<ValueEventListener>()

    fun getHospitals(context: Context?, setUpAdapter: ((MutableList<Hospital>) -> Unit)) : LiveData<ValueEventListener> {
        object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    val json = Gson().toJson(snapshot.value)
                    val type = object : TypeToken<List<Hospital>>() {}.type
                    val hospitals = Gson().fromJson<List<Hospital>>(json, type)

                    hospitals?.let {
                        setUpAdapter(it as MutableList<Hospital>)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("MainActivity", "onCancelled: ${error.message}")
                showErrorDialog(context, error.message)
            }

        }.also {
            result.postValue(it)
        }

        return result
    }

}