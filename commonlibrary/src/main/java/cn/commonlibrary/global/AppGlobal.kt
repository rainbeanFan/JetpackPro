package cn.commonlibrary.global

import android.app.Application

class AppGlobal {

    companion object {

        private var sApp: Application? = null

        @JvmStatic
        fun getApplication():Application{
            sApp ?: synchronized(this){
                getApplicationInvoke()
            }
            return sApp!!
        }

        private fun getApplicationInvoke():Application?{
            try {
                if (sApp == null){
                    val method = Class.forName("android.app.activityThread").getDeclaredMethod("currentApplication")
                    sApp = method.invoke(null, null) as Application?
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
            return sApp!!
        }

    }

}