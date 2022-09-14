package com.mazeit.himusomogro.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.mazeit.himusomogro.R
import com.mazeit.himusomogro.data.Config.INT_AD_SHOW_INTERVAL
import com.mazeit.himusomogro.data.adapter.StoryAdapter
import com.mazeit.himusomogro.data.db.content.getAllStories
import com.mazeit.himusomogro.data.db.spf.intShowTime
import com.mazeit.himusomogro.data.db.spf.newIntShow
import com.mazeit.himusomogro.data.listener.StoryOnClickListener
import com.mazeit.himusomogro.data.models.Story
import com.mazeit.himusomogro.data.utils.animate
import com.mazeit.himusomogro.data.utils.loadingDialog
import com.mazeit.himusomogro.databinding.ActivityStoriesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.pow

class StoriesActivity : AppCompatActivity(), StoryOnClickListener {

    private var _binding: ActivityStoriesBinding? = null
    private var dialog: AlertDialog? = null

    private lateinit var bannerContainer: ViewGroup
    private var bannerAd: MaxAdView? = null
    private lateinit var interstitialAd: MaxInterstitialAd
    private var retryAttempt = 0.0
    private lateinit var emptyIntAdListener: MaxAdListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStoriesBinding.inflate(layoutInflater)
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
        initVars()
        initUi()
        registerEvents()
        loadAds()
    }

    private fun initVars() {
        _binding?.let {
            bannerContainer = it.storiesBannerContainer
        }
        dialog = loadingDialog(getString(R.string.loading_ad_msg))
    }

    private fun initUi() {
        _binding?.let {
            with(it) {
                val storyAdapter = StoryAdapter(this@StoriesActivity)
                storiesRecyclerView.adapter = storyAdapter
                lifecycleScope.launch(Dispatchers.IO) {
                    val data = getAllStories()
                    launch(Dispatchers.Main) {
                        storyAdapter.submitList(data)
                    }
                }
            }
        }
    }

    private fun registerEvents() {
        onBackPressEvent()
        _binding?.let {
            with(it) {
                storiesToolbar.setNavigationOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

    private fun loadAds() {
        initBanner()
        initInterstitialAds()
    }

    private fun initBanner() {
        bannerAd = MaxAdView(getString(R.string.applovin_banner), this)
        bannerAd?.setListener(object : MaxAdViewAdListener {
            override fun onAdLoaded(ad: MaxAd?) {
                bannerContainer.visibility = View.VISIBLE
            }

            override fun onAdDisplayed(ad: MaxAd?) {
                bannerContainer.visibility = View.VISIBLE
            }

            override fun onAdHidden(ad: MaxAd?) {
            }

            override fun onAdClicked(ad: MaxAd?) {
            }

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                bannerContainer.visibility = View.GONE
            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
                bannerContainer.visibility = View.GONE
            }

            override fun onAdExpanded(ad: MaxAd?) {}

            override fun onAdCollapsed(ad: MaxAd?) {}
        })

        val width = ViewGroup.LayoutParams.MATCH_PARENT

        val heightPx = resources.getDimensionPixelSize(R.dimen.banner_height)

        bannerAd?.layoutParams = FrameLayout.LayoutParams(width, heightPx)

        bannerAd?.setBackgroundColor(Color.WHITE)

        bannerContainer.removeAllViews()

        bannerContainer.addView(bannerAd)

        bannerAd?.loadAd()
    }

    private fun initInterstitialAds() {
        interstitialAd = MaxInterstitialAd(getString(R.string.applovin_interstitial), this)
        emptyIntAdListener = object : MaxAdListener {
            override fun onAdLoaded(ad: MaxAd?) {
                retryAttempt = 0.0
            }

            override fun onAdDisplayed(ad: MaxAd?) {}

            override fun onAdHidden(ad: MaxAd?) {}

            override fun onAdClicked(ad: MaxAd?) {}

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                retryAttempt++
                val delayMillis =
                    TimeUnit.SECONDS.toMillis(2.0.pow(min(6.0, retryAttempt)).toLong())
                Handler(Looper.getMainLooper()).postDelayed({
                    if (::interstitialAd.isInitialized) {
                        interstitialAd.loadAd()
                    }
                }, delayMillis)
            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {}
        }
        interstitialAd.setListener(emptyIntAdListener)
        interstitialAd.loadAd()
    }

    private fun showInterstitialAds(toDo: () -> Unit) {
        if (interstitialAd.isReady) {
            if ((intShowTime() % INT_AD_SHOW_INTERVAL) == 0L) {
                interstitialAd.setListener(object : MaxAdListener {
                    override fun onAdLoaded(ad: MaxAd?) {}

                    override fun onAdDisplayed(ad: MaxAd?) {
                        newIntShow()
                    }

                    override fun onAdHidden(ad: MaxAd?) {
                        interstitialAd.setListener(emptyIntAdListener)
                        interstitialAd.loadAd()
                        Handler(Looper.getMainLooper()).postDelayed({
                            toDo()
                        }, 500)
                    }

                    override fun onAdClicked(ad: MaxAd?) {}

                    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {}

                    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
                        interstitialAd.setListener(emptyIntAdListener)
                        interstitialAd.loadAd()
                        Handler(Looper.getMainLooper()).postDelayed({
                            toDo()
                        }, 500)
                    }
                })
                dialog?.show()
                Handler(Looper.getMainLooper()).postDelayed({
                    dialog?.dismiss()
                    interstitialAd.showAd()
                }, 1500)
            } else toDo()
        } else {
            interstitialAd.setListener(emptyIntAdListener)
            interstitialAd.loadAd()
            toDo()
        }
    }

    override fun onStoryClick(item: Story) {
        showInterstitialAds {
            val chapterIntent = Intent(this, ChaptersActivity::class.java)
            chapterIntent.putExtra("story", item)
            startActivity(chapterIntent)
            animate()
        }
    }

    private fun onBackPressEvent() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                animate()
            }
        })
    }


}