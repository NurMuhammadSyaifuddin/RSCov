package com.project.rscov.ui.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.rscov.R
import com.project.rscov.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.title_action_bar_about)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}