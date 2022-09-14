package com.mazeit.himusomogro.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.sdk.AppLovinSdk
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.mazeit.himusomogro.R
import com.mazeit.himusomogro.data.utils.launch
import com.mazeit.himusomogro.databinding.ActivitySplashBinding
import java.util.*
import kotlin.concurrent.timerTask


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), MaxAdListener {

    private var _binding: ActivitySplashBinding? = null
    private var progressBar: LinearProgressIndicator? = null

    private lateinit var interstitialAd: MaxInterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(_binding?.root)
        val animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.zoom_anim)
        _binding?.splashImg?.startAnimation(animation)
        progressBar = _binding?.progressBar
        progressBar?.hide()
        Timer().schedule(timerTask {
            runOnUiThread {
                progressBar?.show()
                initMax()
            }
        }, 1500)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            launch(
                MainActivity::class.java,
                clearStack = true
            )
        }, 1000)
    }

    private fun initMax() {
        AppLovinSdk.getInstance(this).mediationProvider = "max"
        AppLovinSdk.getInstance(this).initializeSdk {
            initIntAds()
        }
    }

    private fun initIntAds() {
        interstitialAd = MaxInterstitialAd(getString(R.string.applovin_interstitial), this)
        interstitialAd.setListener(this)
        interstitialAd.loadAd()
    }

    override fun onAdLoaded(ad: MaxAd?) {
        interstitialAd.showAd()
    }

    override fun onAdDisplayed(ad: MaxAd?) {}

    override fun onAdHidden(ad: MaxAd?) {
        initMainActivity()
    }

    override fun onAdClicked(ad: MaxAd?) {}

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
        launch(MainActivity::class.java, clearStack = true)
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
        launch(MainActivity::class.java, clearStack = true)
    }
}