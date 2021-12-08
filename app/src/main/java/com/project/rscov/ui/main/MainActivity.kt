package com.project.rscov.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.rscov.R
import com.project.rscov.adapter.MainAdapter
import com.project.rscov.databinding.ActivityMainBinding
import com.project.rscov.ui.detail.DetailActivity
import com.project.rscov.utils.*

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
                getHospitalsFromFirebase()
                showTvNoData(value)

            }

            edtSearchMain.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val value = edtSearchMain.text.toString().trim()
                    getHospitalsFromFirebase()
                    showTvNoData(value)
                    hideSoftKeyboard(this@MainActivity, binding.root)
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

            adapter.onClick {
                Intent(this@MainActivity, DetailActivity::class.java).also { intent ->
                    intent.putExtra(DetailActivity.EXTRA_DATA, it)
                    startActivity(intent)
                }
            }

            btnReload.setOnClickListener { getHospitalsFromFirebase() }
        }
    }

    private fun getHospitalsFromFirebase() {
        binding.apply {

            showLoadFailed(false)

            val value = edtSearchMain.text.toString().trim()

            if (isNetworkAvailable(this@MainActivity)) {

                viewModel.getHospitals(
                    this@MainActivity,
                    value
                ) {
                    adapter.hospitals = it
                    rvHospitals.adapter = adapter
                    progressBar.gone()

                }.observe(this@MainActivity) {
                    hospitalsDatabase.addValueEventListener(it)
                }

                Handler(Looper.getMainLooper())
                    .postDelayed({

                        if (progressBar.isVisible) {
                            showLoadFailed(true)
                        }

                    }, DELAY_CONNETING)

            } else {
                showLoadFailed(true)
            }

        }
    }

    private fun showLoadFailed(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.gone()
                rvHospitals.gone()
                imgFailed.visible()
                tvFailed.visible()
                btnReload.visible()
                edtSearchMain.disabled()
            } else {
                progressBar.visible()
                rvHospitals.visible()
                imgFailed.gone()
                tvFailed.gone()
                btnReload.gone()
                edtSearchMain.enabled()
            }
        }
    }

    private fun showTvNoData(value: String) {
        binding.apply {
            Handler(Looper.getMainLooper())
                .postDelayed({
                    if (adapter.hospitals.size == 0) {
                        tvNoData.visible()
                        tvNoData.text = getString(R.string.no_data, value)
                    } else {
                        tvNoData.gone()
                    }
                }, 100)

        }
    }

    companion object {
        private const val DELAY_CONNETING = 15000L
    }
}