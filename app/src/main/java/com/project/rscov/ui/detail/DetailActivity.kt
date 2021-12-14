package com.project.rscov.ui.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.rscov.R
import com.project.rscov.databinding.ActivityDetailBinding
import com.project.rscov.model.Hospital
import com.project.rscov.utils.loadImage
import com.project.rscov.utils.popUpImage

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.title_action_bar_detail)
        }

        getDataIntent()

        onAction()
    }

    private fun onAction() {
        binding.apply {
            imgCopas.setOnClickListener {
                val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", tvPhone.text)
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(this@DetailActivity, "nomor telepon disalin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDataIntent() {
        binding.apply {
            intent?.let {

                val hospital = it.extras?.getParcelable<Hospital>(EXTRA_DATA)

                viewModel.setHospital(hospital)
                viewModel.getHospital().observe(this@DetailActivity) { data ->

                    imgPictureHeader.loadImage(data?.imageUrl.toString())
                    tvHospitalName.text = data?.name
                    tvAddress.text = data?.address
                    tvRegion.text = data?.region
                    tvProvince.text = data?.province
                    tvPhone.text = data?.phone

                    imgPictureHeader.setOnClickListener {
                        data?.imageUrl?.let { url ->
                            popUpImage(this@DetailActivity, url)
                        }
                    }

                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object{
        const val EXTRA_DATA = "extra_data"
    }
}