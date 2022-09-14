package com.mazeit.himusomogro.data.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.mazeit.himusomogro.R
import com.mazeit.himusomogro.data.Config.APP_UPDATE_CHECK_FREQUENCY
import com.mazeit.himusomogro.data.db.spf.lastUpdateChkTime
import java.util.*

fun View.onclick(toDo: () -> Unit) {
    setOnClickListener {
        toDo()
    }
}

fun Activity.animate() {
    overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
}

@SuppressLint("InflateParams")
fun Context.loadingDialog(msg: String? = null): AlertDialog {
    val dialogBuilder = AlertDialog.Builder(this, R.style.TransparentDialog)
    val customView = LayoutInflater.from(this).inflate(R.layout.layout_waiting_dialog, null)
    msg?.let {
        val msgView = customView.findViewById<AppCompatTextView>(R.id.loadingText)
        msgView.text = it
    }
    dialogBuilder.setView(customView)
    dialogBuilder.setCancelable(false)
    val dialog = dialogBuilder.create()
    dialog.setCanceledOnTouchOutside(false)
    dialog.setOnShowListener {
        dialog.window?.attributes?.dimAmount = 0.5f
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
    dialog.setOnDismissListener {
        dialog.window?.attributes?.dimAmount = 0f
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
    return dialog
}

@SuppressLint("InflateParams")
fun Activity.updateDialog(): AlertDialog {
    val dialogBuilder = AlertDialog.Builder(this, R.style.UpdateDialogTheme)
    val customView = layoutInflater.inflate(R.layout.layout_dialog_update, null)
    val snoozeChkBox = customView.findViewById<MaterialCheckBox>(R.id.dialogUpdateSnoozeChkBox)
    val cancelBtn = customView.findViewById<MaterialButton>(R.id.dialogUpdateCancelBtn)
    val updateBtn = customView.findViewById<MaterialButton>(R.id.dialogUpdateUpdateBtn)
    snoozeChkBox.text =
        getString(R.string.update_snooze_message, APP_UPDATE_CHECK_FREQUENCY.toString().toBangla())
    dialogBuilder.setView(customView)
    dialogBuilder.setCancelable(true)
    val dialog = dialogBuilder.create()
    dialog.setCanceledOnTouchOutside(false)
    dialog.setOnShowListener {
        dialog.window?.attributes?.dimAmount = 0.5f
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        snoozeChkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                updateBtn.backgroundTintList = ColorStateList.valueOf(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.light_gray,
                        theme
                    )
                )
                updateBtn.setTextColor(ResourcesCompat.getColor(resources, R.color.gray, theme))
                updateBtn.isEnabled = false
            } else {
                updateBtn.backgroundTintList = ColorStateList.valueOf(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.green,
                        theme
                    )
                )
                updateBtn.setTextColor(ResourcesCompat.getColor(resources, R.color.white, theme))
                updateBtn.isEnabled = true
            }
        }
        cancelBtn.onclick {
            lastUpdateChkTime(Date().time)
            dialog.dismiss()
        }
        updateBtn.onclick {
            dialog.dismiss()
            openGooglePlay()
        }
    }
    dialog.setOnDismissListener {
        dialog.window?.attributes?.dimAmount = 0f
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
    return dialog
}