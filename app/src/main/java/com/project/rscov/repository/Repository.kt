package com.project.rscov.repository

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.rscov.model.Hospital

object Repository {

    fun getHospitals(context: Context?, hospitalsList: ((MutableList<Hospital>) -> Unit)?, rvAdapter: (() -> Unit)): ValueEventListener =
        object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null){
                    val json = Gson().toJson(snapshot.value)
                    val type = object : TypeToken<List<Hospital>>(){}.type
                    val hospitals = Gson().fromJson<List<Hospital>>(json, type)

                    hospitals?.let {
                        if (hospitalsList != null) {
                            hospitalsList(it as MutableList<Hospital>)
                        }
                    }
                    rvAdapter()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("MainActivity", "onCancelled: ${error.message}")
            }

        }

}