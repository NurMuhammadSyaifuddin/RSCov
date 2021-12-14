package com.project.rscov.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.rscov.R
import com.project.rscov.adapter.MainAdapter
import com.project.rscov.databinding.ActivityMainBinding
import com.project.rscov.model.Hospital
import com.project.rscov.ui.about.AboutActivity
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

        onAction()

        getDataFromFirebase()
    }

    private fun getDataFromFirebase() {
        binding.apply {

            progressBar.visible()
            showLoadFailed(false)
            showEmptyData(false)

            hospitalsDatabase.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    EspressoIdlingResource.increment()

                    if (snapshot.value != null) {

                        val json = Gson().toJson(snapshot.value)
                        val type = object : TypeToken<List<Hospital>>() {}.type
                        val hospitals = Gson().fromJson<MutableList<Hospital>>(json, type)

                        viewModel.setHospitals(hospitals)
                        viewModel.getHospitals().observe(this@MainActivity){
                            val value = edtSearchMain.text.toString().trim()

                            adapter.hospitals = it
                            rvHospitals.adapter = adapter

                            if (value.isNotBlank()) {
                                getFilter(value)
                            }
                            progressBar.gone()
                        }

                    }else{
                        showEmptyData(true)
                    }

                    EspressoIdlingResource.decrement()

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("MainActivity", "onCancelled: ${error.message}")
                    showErrorDialog(this@MainActivity, error.message)
                }

            })

            Handler(Looper.getMainLooper())
                .postDelayed({
                    if (progressBar.isVisible) {
                        showLoadFailed(true)
                    }
                }, DELAY_CONNECTING)

        }
    }

    private fun getFilter(value: String) {
        binding.apply {
            adapter.filter.filter(value)
            rvHospitals.scrollToPosition(0)
            showTvNoData(value)
        }
    }


    private fun onAction() {
        binding.apply {

            edtSearchMain.addTextChangedListener {
                getFilter(it.toString().trim())
            }

            edtSearchMain.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getFilter(edtSearchMain.text.toString().trim())
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


            btnReload.setOnClickListener { getDataFromFirebase() }
        }
    }

    private fun showLoadFailed(state: Boolean) {
        binding.apply {
            if (state) {
                rvHospitals.gone()
                progressBar.gone()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.menu_about -> {
                Intent(this, AboutActivity::class.java).also { startActivity(it) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    companion object {
        private const val DELAY_CONNECTING = 15000L
    }
}