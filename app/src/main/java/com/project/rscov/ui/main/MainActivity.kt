package com.project.rscov.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.rscov.adapter.MainAdapter
import com.project.rscov.databinding.ActivityMainBinding
import com.project.rscov.utils.gone
import com.project.rscov.utils.visible

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

        onAction()
    }

    private fun onAction() {
        binding.apply {
            edtSearchMain.addTextChangedListener {
                adapter.filter.filter(it.toString().trim().lowercase())
            }
            edtSearchMain.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    val dataSearch = edtSearchMain.text.toString().trim().lowercase()
                    adapter.filter.filter(dataSearch)

                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    private fun getHospitalsFromFirebase() {
        binding.progressBar.visible()
        viewModel.getHospitals(
            this
        ) {
            adapter.hospitals = it
            binding.rvHospitals.adapter = adapter
            binding.progressBar.gone()
        }.observe(this) {
            hospitalsDatabase.addValueEventListener(it)
        }
    }
}