package com.project.rscov.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.project.rscov.databinding.ActivitySplashBinding
import com.project.rscov.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper())
            .postDelayed({
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }, DELAY_SPLASH)
    }

    companion object{
        private const val DELAY_SPLASH = 800L
    }
}