package com.enesaksoy.sportsapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.enesaksoy.sportsapp.R
import com.enesaksoy.sportsapp.databinding.ActivityAnimationBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class AnimationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val anim1 = AnimationUtils.loadAnimation(this, R.anim.animation1)
        val anim2 = AnimationUtils.loadAnimation(this, R.anim.animation2)
        binding.imageView.animation = anim1
        binding.imageView2.animation = anim2
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        },2300)
    }
}