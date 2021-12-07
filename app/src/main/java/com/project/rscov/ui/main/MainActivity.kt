package com.project.rscov.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.rscov.adapter.MainAdapter
import com.project.rscov.databinding.ActivityMainBinding
import com.project.rscov.model.Hospital
import com.project.rscov.ui.detail.DetailActivity
import com.project.rscov.utils.gone
import com.project.rscov.utils.hideSoftKeyboard
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
                val value = it.toString().trim()
                adapter.filter.filter(value)
            }

            edtSearchMain.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val value = edtSearchMain.text.toString().trim()
                    adapter.filter.filter(value)
                    hideSoftKeyboard(this@MainActivity, binding.root)
                    return@setOnEditorActionListener true
                }
                hideSoftKeyboard(this@MainActivity, binding.root)
                return@setOnEditorActionListener false
            }

            adapter.onClick {
                Intent(this@MainActivity, DetailActivity::class.java).also { intent ->
                    intent.putExtra(DetailActivity.EXTRA_DATA, it)
                    startActivity(intent)
                }
            }
        }
    }

    private fun getHospitalsFromFirebase() {
        binding.apply {
            progressBar.visible()

            viewModel.getHospitals(
                this@MainActivity
            ) {
                val value = edtSearchMain.text.toString().trim()

                if (value.isNotBlank()) {
                    val filteredList = mutableListOf<Hospital>()

                    for (hospital in it) {
                        val title = hospital.name.trim().lowercase()
                        val region = hospital.region.trim().lowercase()
                        val province = hospital.province.trim().lowercase()

                        if (title.contains(value) || region.contains(value) || province.contains(
                                value
                            )
                        ) {
                            filteredList.add(hospital)
                        }
                    }
                    adapter.hospitals = filteredList
                } else {
                    adapter.hospitals = it
                }

                rvHospitals.adapter = adapter
                progressBar.gone()

            }.observe(this@MainActivity) {
                hospitalsDatabase.addValueEventListener(it)
            }
        }
    }
}