package com.mazeit.himusomogro.ui.activities

import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.github.ksoichiro.android.observablescrollview.ScrollState
import com.google.android.material.snackbar.Snackbar
import com.mazeit.himusomogro.R
import com.mazeit.himusomogro.data.Config
import com.mazeit.himusomogro.data.Config.APP_DOWNLOAD_URL
import com.mazeit.himusomogro.data.Config.READING_INT_AD_SHOW_TIME_INTERVAL
import com.mazeit.himusomogro.data.db.content.getContent
import com.mazeit.himusomogro.data.db.room.database.ReadingRecordDatabase
import com.mazeit.himusomogro.data.db.spf.*
import com.mazeit.himusomogro.data.models.Record
import com.mazeit.himusomogro.data.utils.animate
import com.mazeit.himusomogro.data.utils.dpToPixel
import com.mazeit.himusomogro.data.utils.loadingDialog
import com.mazeit.himusomogro.data.utils.onclick
import com.mazeit.himusomogro.databinding.ActivityReadingBinding
import com.mazeit.himusomogro.ui.fragments.ReadingMenuFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.fixedRateTimer
import kotlin.math.min
import kotlin.math.pow

class ReadingActivity : AppCompatActivity(), ObservableScrollViewCallbacks {

    private var _binding: ActivityReadingBinding? = null
    private lateinit var readingRecordDb: ReadingRecordDatabase
    private var chapterId: Int = -1
    private var lastProgress = 0
    private var enteringTime = 0L
    private var resumeMsg: Snackbar? = null
    private var content: String? = null
    private var timer: Timer? = null
    private var scrollPosition = 0
    private var dialog: AlertDialog? = null

    private lateinit var bannerContainer: ViewGroup
    private lateinit var bannerContainer2: ViewGroup
    private var bannerAd: MaxAdView? = null
    private var bannerAd2: MaxAdView? = null
    private lateinit var interstitialAd: MaxInterstitialAd
    private var retryAttempt = 0.0
    private lateinit var emptyIntAdListener: MaxAdListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(_binding?.root)
        hideSystemUI()
    }

    override fun onResume() {
        super.onResume()
        startAdsTimer()
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
        timer = null
    }

    override fun onStart() {
        super.onStart()
        start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        timer?.cancel()
        timer = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.reading_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
            R.id.miReadingSettings -> {
                _binding?.let {
                    with(it) {
                        ReadingMenuFragment(readingContent).show(
                            supportFragmentManager,
                            "ReadingMenu"
                        )
                    }
                }
            }
            R.id.miReadingShare -> shareContent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun start() {
        initVars()
        initUi()
        registerEvents()
        loadAds()
    }

    private fun initVars() {
        _binding?.let {
            bannerContainer = it.readingTopBannerContainer
            bannerContainer2 = it.readingBottomBannerContainer
        }
        dialog = loadingDialog(getString(R.string.loading_ad_msg))
        readingRecordDb = ReadingRecordDatabase(this)
        enteringTime = Date().time
    }

    private fun initUi() {
        _binding?.let {
            with(it) {
                setSupportActionBar(readingToolbar)
                readingContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, readingTextSize().toFloat())
                readingContent.lineHeight = readingLineHeight()
                resumeMsg = Snackbar.make(
                    findViewById(android.R.id.content),
                    "সর্বশেষ স্থান থেকে পড়া আরম্ভ করুন",
                    5000
                )
                    .setAction("পড়ুন") {
                        _binding?.readingContentContainer?.scrollVerticallyTo(scrollPosition)
                    }
                intent.getIntExtra("chapterId", -1).let { chapterId ->
                    intent.getStringExtra("chapterTitle")?.let { title ->
                        supportActionBar?.title = title
                    }
                    this@ReadingActivity.chapterId = chapterId
                    lastReadStory(chapterId)
                    if (chapterId == 1) {
                        readingNavigatePrevious.hide()
                    }
                    if (chapterId == 213) {
                        readingNavigateNext.hide()
                    }
                    lifecycleScope.launch(Dispatchers.IO) {
                        getContent(chapterId)?.let { c ->
                            launch(Dispatchers.Main) {
                                content = c.data
                                readingContent.text = content
                            }
                        }
                        readingRecordDb.readingRecordDao().getReadingRecord(chapterId)
                            ?.let { record ->
                                launch(Dispatchers.Main) {
                                    if (record.scrollPosition != 0 && record.scrollPosition != _binding!!.readingContentContainer.scrollY) {
                                        scrollPosition = record.scrollPosition
                                        showResumeMsg()
                                    } else {
                                        _binding?.readingContentContainer?.scrollVerticallyTo(0)
                                    }
                                }
                            }
                    }
                }
            }
        }
    }

    private fun startAdsTimer() {
        try {
            if (timer == null) {
                timer = fixedRateTimer(
                    null,
                    startAt = Date(Date().time + (READING_INT_AD_SHOW_TIME_INTERVAL * 60000).toLong()),
                    period = (READING_INT_AD_SHOW_TIME_INTERVAL * 60000).toLong()
                ) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        showInterstitialAds {}
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", "startAdsTimer: ${e.message}")
        }
    }

    private fun showResumeMsg() {
        resumeMsg?.let {
            if (isNetworkAvailable()) {
                val snackView = it.view as FrameLayout
                snackView.translationY = -dpToPixel(48f)
            }
            it.show()
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



        bannerAd2 = MaxAdView(getString(R.string.applovin_banner), this)
        bannerAd2?.setListener(object : MaxAdViewAdListener {
            override fun onAdLoaded(ad: MaxAd?) {
                bannerContainer2.visibility = View.VISIBLE
            }

            override fun onAdDisplayed(ad: MaxAd?) {
                bannerContainer2.visibility = View.VISIBLE
            }

            override fun onAdHidden(ad: MaxAd?) {
            }

            override fun onAdClicked(ad: MaxAd?) {
            }

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                bannerContainer2.visibility = View.GONE
            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
                bannerContainer2.visibility = View.GONE
            }

            override fun onAdExpanded(ad: MaxAd?) {}

            override fun onAdCollapsed(ad: MaxAd?) {}
        })

        bannerAd2?.layoutParams = FrameLayout.LayoutParams(width, heightPx)

        bannerAd2?.setBackgroundColor(Color.WHITE)

        bannerContainer2.removeAllViews()

        bannerContainer2.addView(bannerAd2)

        bannerAd?.loadAd()
        bannerAd2?.loadAd()
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
                val delayMillis = TimeUnit.SECONDS.toMillis(2.0.pow(min(6.0, retryAttempt)).toLong())
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
            interstitialAd.setListener(object : MaxAdListener {
                override fun onAdLoaded(ad: MaxAd?) {}

                override fun onAdDisplayed(ad: MaxAd?) {}

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
        } else {
            interstitialAd.setListener(emptyIntAdListener)
            interstitialAd.loadAd()
            toDo()
        }
    }

    private fun registerEvents() {
        onBackPressEvent()
        _binding?.let {
            with(it) {
                readingContentContainer.setScrollViewCallbacks(this@ReadingActivity)
                readingNavigatePrevious.onclick {
                    intent.putExtra("chapterId", chapterId - 1)
                    start()
                }
                readingNavigateNext.onclick {
                    intent.putExtra("chapterId", chapterId + 1)
                    start()
                }
            }
        }
    }

    override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
        chapterId.let {
            lifecycleScope.launch(Dispatchers.IO) {
                readingRecordDb.readingRecordDao().addReadingRecord(Record(chapterId, scrollY))
            }
        }
        try {
            _binding?.let {
                with(it) {
                    val verticalScrollableHeight =
                        readingContentContainer.getChildAt(0).measuredHeight - readingContentContainer.measuredHeight
                    val scrollPercentage = (scrollY.toFloat()) / verticalScrollableHeight
                    val scrollPercentageRounded = (scrollPercentage * 100).toInt()
                    if (scrollPercentageRounded != lastProgress) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            readingProgressBar.setProgress(scrollPercentageRounded, true)
                        } else readingProgressBar.progress = scrollPercentageRounded
                        lastProgress = scrollPercentageRounded
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", "onScrollChanged: ${e.message}")
        }
    }

    override fun onDownMotionEvent() {}

    override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
        scrollState?.let {
            if (it == ScrollState.DOWN) {
                _binding?.let { binding ->
                    with(binding) {
                        if (chapterId != 1) {
                            readingNavigatePrevious.show()
                        }
                        if (chapterId != 213) {
                            readingNavigateNext.show()
                        }
                    }
                }
            } else {
                _binding?.let { binding ->
                    with(binding) {
                        if (chapterId != 1) {
                            readingNavigatePrevious.hide()
                        }
                        if (chapterId != 213) {
                            readingNavigateNext.hide()
                        }
                    }
                }
            }
        }
    }

    private fun hideSystemUI() {
        _binding?.let {
            with(it) {
                WindowCompat.setDecorFitsSystemWindows(window, true)
                WindowInsetsControllerCompat(window, readingRoot).let { controller ->
//                    controller.hide(WindowInsetsCompat.Type.systemBars())
                    controller.hide(WindowInsetsCompat.Type.navigationBars())
                    controller.systemBarsBehavior =
                        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    private fun shareContent() {
        var ex: String? = null
        try {
            content?.let {
                ex = if (it.length > 250) {
                    it.substring(0, 250)
                } else it
            }
        } catch (e: Exception) {
            Log.e("TAG", "shareContent: ${e.message}")
        }
        val msg: String = if (ex != null) {
            "\"$ex\"...\n\nহুমায়ুন আহমেদের হিমু গল্পের বাকি অংশটুকু পড়তে চাইলে এখনই এই লিংক থেকে হিমু সমগ্র অ্যাপটি ডাউনলোড করুন।\n\n${APP_DOWNLOAD_URL}\n\nএকইসাথে আপনার বন্ধুবান্ধব সহ সকল পরিচিত লোকজনের মাঝে এই অসাধারন অ্যাপটি ছড়িয়ে দিন যাতে করে তারাও হিমুর সকল গল্প শুধুমাত্র একটি অ্যাপ এর মাধ্যমেই পড়তে পারে। ধন্যবাদ।"
        } else {
            "হুমায়ুন আহমেদের হিমুর সকল গল্প পড়তে চাইলে এখনই এই লিংক থেকে হিমু সমগ্র অ্যাপটি ডাউনলোড করুন।\n\n${APP_DOWNLOAD_URL}\n\nএকইসাথে আপনার বন্ধুবান্ধব সহ সকল পরিচিত লোকজনের মাঝে এই অসাধারন অ্যাপটি ছড়িয়ে দিন যাতে করে তারাও হিমুর সকল গল্প শুধুমাত্র একটি অ্যাপ এর মাধ্যমেই পড়তে পারে। ধন্যবাদ।"
        }
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, msg)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun onBackPressEvent() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                animate()
            }
        })
    }

}