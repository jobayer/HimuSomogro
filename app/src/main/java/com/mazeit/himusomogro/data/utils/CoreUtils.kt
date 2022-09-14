package com.mazeit.himusomogro.data.utils

import android.app.Activity
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import com.mazeit.himusomogro.data.models.IntentData
import org.jetbrains.annotations.NotNull

fun Activity.launch(
    @NotNull className: Class<*>,
    vararg data: IntentData,
    clearStack: Boolean = false
) {
    val intent = Intent(this, className)
    if (data.isNotEmpty()) {
        data.forEach {
            intent.putExtra(it.key, it.data)
        }
    }
    if (clearStack) {
        finishAffinity()
        animate()
    }
    startActivity(intent)
}

fun Context.dpToPixel(dp: Float): Float {
    return dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun String.toBangla(): String {
    return this
        .replace("0", "০")
        .replace("1", "১")
        .replace("2", "২")
        .replace("3", "৩")
        .replace("4", "৪")
        .replace("5", "৫")
        .replace("6", "৬")
        .replace("7", "৭")
        .replace("8", "৮")
        .replace("9", "৯")
}

fun Context.openGooglePlay() {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("https://play.google.com/store/apps/details?id=${packageName}")
    startActivity(intent)
}

@Suppress("DEPRECATION")
fun Context.isNetworkAvailable(): Boolean {
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