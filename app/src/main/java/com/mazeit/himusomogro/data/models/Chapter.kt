package com.mazeit.himusomogro.data.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Chapter(
    var id: Int,
    var title: String
) : Parcelable
