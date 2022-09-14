package com.mazeit.himusomogro.data.models

import android.os.Parcelable
import androidx.annotation.Keep

@Keep
data class IntentData(
    var key: String,
    var data: Parcelable
)