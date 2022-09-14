package com.mazeit.himusomogro.data.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Story(
    var id: Int,
    var title: String,
    var episode: Int,
    var publishYear: Int
) : Parcelable
