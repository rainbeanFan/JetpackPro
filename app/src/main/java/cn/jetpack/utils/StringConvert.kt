package cn.jetpack.utils

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

class StringConvert {

    companion object{

        @JvmStatic
        fun convertFeedUgc(count: Int): String{
            return when {
                count < 10000 ->{
                    "$count"
                }
                else -> {
                    "${count/10000}万"
                }
            }
        }

        @JvmStatic
        fun convertTagFeeds(num: Int): String{
            return when {
                num < 10000 ->{
                    "${num}人观看"
                }
                else -> {
                    "${num/10000}万人观看"
                }
            }
        }

        @JvmStatic
        fun convertSpannable(count: Int, intro: String): CharSequence {
            val countStr = "$count"
            return SpannableString(countStr + intro).apply {
                    setSpan(ForegroundColorSpan(Color.BLACK),0,countStr.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    setSpan(AbsoluteSizeSpan(16,true),0,countStr.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    setSpan(StyleSpan(Typeface.BOLD),0,countStr.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

    }

}