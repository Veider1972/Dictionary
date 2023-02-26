package ru.veider.dictionary.presentation.splashscreen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import ru.veider.dictionary.R
import ru.veider.dictionary.databinding.ActivitySplashScreenBinding
import ru.veider.dictionary.presentation.view.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            this.setTheme(R.style.Theme_AppSplash)
            installSplashScreen()
        } else {
            val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val splashImageView = binding.splashImageView.apply {
                visibility = View.VISIBLE
            }

            Glide
                .with(this.applicationContext)
                .asGif()
                .load(Uri.parse("file:///android_asset/hello.gif"))
                .circleCrop()
                .fitCenter()
                .into(splashImageView)
            this.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(6000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}