package com.project.rscov.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.rscov.databinding.ActivityDetailBinding
import com.project.rscov.model.Hospital
import com.project.rscov.utils.loadImage

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataIntent()
    }

    private fun getDataIntent() {
        binding.apply {
            intent?.let {
                val hospital = it.extras?.getParcelable<Hospital>(EXTRA_DATA)

                imgPictureHeader.loadImage(hospital?.imageUrl.toString())
                tvHospitalName.text = hospital?.name
                tvAddress.text = hospital?.address
                tvRegion.text = hospital?.region
                tvProvince.text = hospital?.province
                tvPhone.text = hospital?.phone
            }
        }
    }

    companion object{
        const val EXTRA_DATA = "extra_data"
    }
}