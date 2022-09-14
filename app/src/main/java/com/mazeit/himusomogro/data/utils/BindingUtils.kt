package com.mazeit.himusomogro.data.utils

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.mazeit.himusomogro.R

@BindingAdapter("totalEpisode")
fun AppCompatTextView.setTotalEpisode(episodeNum: Int) {
    text = episodeNum.toString().toBangla()
}

@BindingAdapter("publishYear")
fun AppCompatTextView.setPublishYear(publishYear: Int) {
    text = context.getString(R.string.story_publish_date, publishYear.toString().toBangla())
}
