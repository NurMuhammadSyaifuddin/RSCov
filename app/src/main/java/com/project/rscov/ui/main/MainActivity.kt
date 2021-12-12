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
import com.google.firebase.database.*
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
                adapter.filter.filter(it.toString().trim())
                showTvNoData(it.toString())
            }

            edtSearchMain.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    adapter.filter.filter(edtSearchMain.text)
                    showTvNoData(edtSearchMain.text.toString().trim())
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

            adapter.onClickToPopUpImage { popUpImage(this@MainActivity, it) }


            btnReload.setOnClickListener { getHospitalsFromFirebase() }
        }
    }

    private fun getHospitalsFromFirebase() {
        binding.apply {

            progressBar.visible()
            showLoadFailed(false)
            showEmptyData(false)

            viewModel.getHospitals(this@MainActivity, {
                val value = edtSearchMain.text.toString().trim()

                adapter.hospitals = it
                rvHospitals.adapter = adapter

                if (value.isNotBlank()) {
                    adapter.filter.filter(value)
                    showTvNoData(value)
                }
                progressBar.gone()
            }) {
                showEmptyData(true)
            }.observe(this@MainActivity) {
                hospitalsDatabase.addValueEventListener(it)
            }

            Handler(Looper.getMainLooper())
                .postDelayed({

                    if (progressBar.isVisible) {
                        showLoadFailed(true)
                    }

                }, DELAY_CONNECTING)

        }
    }

    private fun showLoadFailed(state: Boolean) {
        binding.apply {
            if (state) {
                rvHospitals.gone()
                imgFailed.visible()
                tvFailed.visible()
                btnReload.visible()
            } else {
                rvHospitals.visible()
                imgFailed.gone()
                tvFailed.gone()
                btnReload.gone()
            }
        }
    }

    private fun showTvNoData(value: String) {
        binding.apply {
            Handler(Looper.getMainLooper())
                .postDelayed({
                    if (adapter.hospitalsFilter.size == 0 && value.isNotBlank()) {
                        tvNoData.visible()
                        tvNoData.text = getString(R.string.no_data, value)
                    } else {
                        tvNoData.gone()
                    }
                }, 400)

        }
    }

    private fun showEmptyData(state: Boolean) {
        binding.apply {
            if (state) {
                imgEmptyData.visible()
                tvEmptyData.visible()
                btnReload.visible()
            } else {
                imgEmptyData.gone()
                tvEmptyData.gone()
                btnReload.gone()
            }
        }
    }

    companion object {
        private const val DELAY_CONNECTING = 15000L
    }
}