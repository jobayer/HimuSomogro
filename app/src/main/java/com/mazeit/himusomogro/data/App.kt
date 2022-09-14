package com.mazeit.himusomogro.data

import android.app.Application
import com.mazeit.himusomogro.data.Config.INT_AD_SHOW_TIME_MAX_VAL
import com.mazeit.himusomogro.data.db.spf.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        HimuDb.init(this)
        if (intShowTime() >= INT_AD_SHOW_TIME_MAX_VAL) {
            cleanIntShowTime()
        }
        if (!isFirstLaunch()) {
            finishFirstLaunch()
        }
    }

}