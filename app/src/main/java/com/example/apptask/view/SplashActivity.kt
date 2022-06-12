package com.example.apptask.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.apptask.databinding.ActivitySplashBinding
import com.example.apptask.view.auth.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val mIntent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(mIntent)
        },3000)
    }
}