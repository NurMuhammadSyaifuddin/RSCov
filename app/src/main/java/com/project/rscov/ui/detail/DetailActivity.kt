package com.project.rscov.ui.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.rscov.R
import com.project.rscov.databinding.ActivityDetailBinding
import com.project.rscov.model.Hospital
import com.project.rscov.utils.loadImage
import com.project.rscov.utils.popUpImage

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

                imgPictureHeader.loadImage(hospital?.imageUrl.toString())
                tvHospitalName.text = hospital?.name
                tvAddress.text = hospital?.address
                tvRegion.text = hospital?.region
                tvProvince.text = hospital?.province
                tvPhone.text = hospital?.phone

                imgPictureHeader.setOnClickListener {
                    hospital?.imageUrl?.let { url ->
                        popUpImage(this@DetailActivity, url)
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