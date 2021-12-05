package com.project.rscov.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.rscov.adapter.MainAdapter
import com.project.rscov.databinding.ActivityMainBinding
import com.project.rscov.utils.showLoading

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: MainViewModels
    private lateinit var hospitalsDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        adapter = MainAdapter()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModels::class.java]
        hospitalsDatabase = FirebaseDatabase.getInstance().getReference("hospitals")

        getHospitalsFromFirebase()
    }

    private fun getHospitalsFromFirebase() {
        binding.progressBar.showLoading(true)
        viewModel.getHospitals(this,
            {
                adapter.hospitals = it
            },
            {
                binding.rvRumahSakit.adapter = adapter
            }).observe(this) {
            hospitalsDatabase.addValueEventListener(it)
            binding.progressBar.showLoading(false)
        }
    }
}