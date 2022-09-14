package com.mazeit.himusomogro.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.sdk.AppLovinSdkUtils
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.review.ReviewManagerFactory
import com.mazeit.himusomogro.R
import com.mazeit.himusomogro.data.Config.APP_UPDATE_CHECK_FREQUENCY
import com.mazeit.himusomogro.data.Config.RATING_ASKING_DELAY
import com.mazeit.himusomogro.data.db.spf.*
import com.mazeit.himusomogro.data.utils.*
import com.mazeit.himusomogro.databinding.ActivityMainBinding
import com.mazeit.himusomogro.ui.fragments.ExitDialogFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private var savedInstState: Bundle? = null

    private lateinit var mrecContainer: ViewGroup
    private var mrecAd: MaxAdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstState = savedInstanceState
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)
    }

    override fun onStart() {
        super.onStart()
        start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun start() {
        _binding?.let {
            mrecContainer = it.mrecContainer
            mrecContainer.visibility = View.GONE
        }
        registerEvents()
        initAds()
        checkForUpdates()
    }

    private fun registerEvents() {
        onBackPressEvent()
        _binding?.let {
            with(it) {
                mainLogo.onclick {
                    openGooglePlay()
                }
                mainAllStoriesBtn.onclick {
                    launch(StoriesActivity::class.java)
                    animate()
                }
                mainLastStoryBtn.onclick {
                    var chapterId = lastReadStory()
                    if (chapterId == -1) {
                        chapterId = 1
                    }
                    val readingIntent = Intent(this@MainActivity, ReadingActivity::class.java)
                    readingIntent.putExtra("chapterId", chapterId)
                    startActivity(readingIntent)
                    animate()
                }
                mainNextStoryBtn.onclick {
                    when (val chapterId = lastReadStory()) {
                        213 -> {
                            Toast.makeText(
                                this@MainActivity,
                                "আপনি শেষ গল্পটিই পড়ছেন",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        -1 -> {
                            val readingIntent =
                                Intent(this@MainActivity, ReadingActivity::class.java)
                            readingIntent.putExtra("chapterId", 1)
                            startActivity(readingIntent)
                            animate()
                        }
                        else -> {
                            val readingIntent =
                                Intent(this@MainActivity, ReadingActivity::class.java)
                            readingIntent.putExtra("chapterId", chapterId + 1)
                            startActivity(readingIntent)
                            animate()
                        }
                    }
                }
            }
        }
    }

    private fun checkForUpdates() {
        try {
            if (Date().time - lastUpdateChkTime() >= (APP_UPDATE_CHECK_FREQUENCY * 86400000)) {
                val appUpdateManager = AppUpdateManagerFactory.create(this)
                val appUpdateInfoTask = appUpdateManager.appUpdateInfo
                appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                        runOnUiThread {
                            updateDialog().show()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", "checkForUpdates: ${e.message}")
        }
    }

    private fun askForRating() {
        val manager = ReviewManagerFactory.create(this)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = manager.launchReviewFlow(this, reviewInfo)
                flow.addOnCompleteListener {
                    if (it.isSuccessful) {
                        setAskedForRating()
                    } else ExitDialogFragment().show(supportFragmentManager, "ExitDialogFragment")
                }
            } else {
                ExitDialogFragment().show(supportFragmentManager, "ExitDialogFragment")
            }
        }
    }

    private fun initAds() {
        initMrec()
    }

    private fun initMrec() {
        mrecAd = MaxAdView(getString(R.string.applovin_mrec), MaxAdFormat.MREC, this)
        mrecAd?.setListener(object : MaxAdViewAdListener {
            override fun onAdLoaded(ad: MaxAd?) {
                mrecContainer.visibility = View.VISIBLE
            }

            override fun onAdDisplayed(ad: MaxAd?) {
                mrecContainer.visibility = View.VISIBLE
            }

            override fun onAdHidden(ad: MaxAd?) {
            }

            override fun onAdClicked(ad: MaxAd?) {
            }

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                mrecContainer.visibility = View.GONE
            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
                mrecContainer.visibility = View.GONE
            }

            override fun onAdExpanded(ad: MaxAd?) {}

            override fun onAdCollapsed(ad: MaxAd?) {}
        })

        val widthPx = AppLovinSdkUtils.dpToPx(this, 300)
        val heightPx = AppLovinSdkUtils.dpToPx(this, 250)

        mrecAd?.layoutParams = FrameLayout.LayoutParams(widthPx, heightPx)

        mrecAd?.setBackgroundColor(Color.WHITE)

        mrecContainer.removeAllViews()

        mrecContainer.addView(mrecAd)

        mrecAd?.loadAd()
    }

    private fun onBackPressEvent() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (Date().time - firstLaunchTime() >= (RATING_ASKING_DELAY * 86400000) && !askedForRating()) {
                    askForRating()
                } else ExitDialogFragment().show(supportFragmentManager, "ExitDialogFragment")
            }
        })
    }

}