package cn.commonlibrary.utils

import cn.commonlibrary.global.AppGlobal

class PixUtils {

    companion object {

        @JvmStatic
        fun dp2px(dpValue:Int):Int{
            val metrics = AppGlobal.getApplication().resources.displayMetrics
            return (metrics.density * dpValue + 0.5f).toInt()
        }

        @JvmStatic
        fun getScreenWidth():Int{
            val metrics = AppGlobal.getApplication().resources.displayMetrics
            return metrics.widthPixels
        }

        @JvmStatic
        fun getScreenHeight():Int{
            val metrics = AppGlobal.getApplication().resources.displayMetrics
            return metrics.heightPixels
        }

    }

}