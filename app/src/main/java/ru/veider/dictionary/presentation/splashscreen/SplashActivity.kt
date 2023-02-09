package ru.veider.dictionary.presentation.splashscreen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import ru.veider.dictionary.databinding.ActivitySplashScreenBinding
import ru.veider.dictionary.presentation.view.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashImageView = binding.splashImageView

        Glide
            .with(this.applicationContext)
            .asGif()
            .load(Uri.parse("file:///android_asset/hello.gif"))
            .circleCrop()
            .fitCenter()

            .into(splashImageView)

        CoroutineScope(Dispatchers.Main).launch {
            delay(6000)
            val intent = Intent(this@SplashActivity,MainActivity::class.java)
            this@SplashActivity.startActivity(intent)
            finish()
        }

        this.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }
}