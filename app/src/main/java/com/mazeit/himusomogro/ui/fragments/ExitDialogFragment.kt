package com.mazeit.himusomogro.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mazeit.himusomogro.data.utils.onclick
import com.mazeit.himusomogro.databinding.FragmentExitDialogBinding

class ExitDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentExitDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExitDialogBinding.inflate(layoutInflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start()
    }

    private fun start() {
        registerEvents()
    }

    private fun registerEvents() {
        _binding?.let {
            with(it) {
                fragExitDialogCancelBtn.onclick {
                    this@ExitDialogFragment.dismiss()
                }
                fragExitDialogExitBtn.onclick {
                    this@ExitDialogFragment.dismiss()
                    requireActivity().finishAffinity()
                }
            }
        }
    }

}