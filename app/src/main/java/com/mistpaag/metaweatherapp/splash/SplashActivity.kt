package com.mistpaag.metaweatherapp.splash


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mistpaag.metaweatherapp.databinding.ActivitySplashBinding
import com.mistpaag.metaweatherapp.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goToMainActivity()
    }

    private fun goToMainActivity() {
        Thread {
            Thread.sleep(2000)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Thread.sleep(1000)
            finish()
        }.start()
    }
}