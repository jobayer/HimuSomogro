package com.mazeit.himusomogro.ui.fragments

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mazeit.himusomogro.data.db.spf.readingLineHeight
import com.mazeit.himusomogro.data.db.spf.readingTextSize
import com.mazeit.himusomogro.data.db.spf.resetReadingLineHeight
import com.mazeit.himusomogro.data.db.spf.resetReadingTextSize
import com.mazeit.himusomogro.data.utils.onclick
import com.mazeit.himusomogro.databinding.FragmentReadingMenuBinding

class ReadingMenuFragment(
    private val textView: AppCompatTextView
) : BottomSheetDialogFragment() {

    private var _binding: FragmentReadingMenuBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReadingMenuBinding.inflate(layoutInflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start()
    }

    private fun start() {
        initViews()
        registerEvents()
    }

    private fun initViews() {
        _binding?.let { it ->
            with(it) {
                fragReadingMenuTextSizeSlider.value = readingTextSize().toFloat()
                fragReadingMenuLineHeightSlider.value = readingLineHeight().toFloat()
            }
        }
    }

    private fun registerEvents() {
        _binding?.let { it ->
            with(it) {
                fragReadingMenuTextSizeSlider.addOnChangeListener { _, value, _ ->
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, value)
                    readingTextSize(value.toInt())
                }
                fragReadingMenuLineHeightSlider.addOnChangeListener { _, value, _ ->
                    textView.lineHeight = value.toInt()
                    readingLineHeight(value.toInt())
                }
                fragReadingMenuCloseBtn.onclick {
                    this@ReadingMenuFragment.dismiss()
                }
                fragReadingMenuResetBtn.onclick {
                    resetReadingTextSize()
                    resetReadingLineHeight()
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, readingTextSize().toFloat())
                    textView.lineHeight = readingLineHeight()
                    this@ReadingMenuFragment.dismiss()
                }
            }
        }
    }

}