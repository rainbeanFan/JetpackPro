package cn.commonlibrary.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.WindowManager

class StatusBar {

    companion object {

        @JvmStatic
        fun fitSystemBar(activity: Activity) {
            val window = activity.window
            val decorView = window.decorView

            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT

        }

        @JvmStatic
        fun lightStatusBar(activity: Activity, light: Boolean) {
            val window = activity.window
            val decorView = window.decorView

            var visibility = decorView.systemUiVisibility

            visibility = if (light) {
                visibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                visibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }

            decorView.systemUiVisibility = visibility
        }

    }

}