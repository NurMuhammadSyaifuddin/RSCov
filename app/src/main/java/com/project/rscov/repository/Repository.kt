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

    fun getHospitals(
        context: Context?,
        value: String,
        setUpAdapter: ((MutableList<Hospital>) -> Unit)
    ): LiveData<ValueEventListener> {

        val result = MediatorLiveData<ValueEventListener>()

        object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {

                    val filteredList = mutableListOf<Hospital>()

                    val json = Gson().toJson(snapshot.value)
                    val type = object : TypeToken<List<Hospital>>() {}.type
                    val hospitals = Gson().fromJson<MutableList<Hospital>>(json, type)


                    if (value.isBlank()){
                        filteredList.addAll(hospitals)
                    }else{
                        for (hospital in hospitals){
                            val title = hospital.name?.trim()?.lowercase()
                            val region = hospital.region?.trim()?.lowercase()
                            val province = hospital.province?.trim()?.lowercase()

                            if (title?.contains(value) as Boolean || region?.contains(value) as Boolean  || province?.contains(value) as Boolean ){
                                filteredList.add(hospital)
                            }
                        }
                    }

                    hospitals?.let {
                        setUpAdapter(filteredList)
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