package cn.jetpack.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment

class HomeFragment:Fragment() {

    private lateinit var mFeedType:String

    private var mShouldPause = true

    companion object{

        @JvmStatic
        fun newInstance(feedType:String): HomeFragment{

            val args = Bundle().apply {
                putString("FEED_TYPE", feedType)
            }
            return HomeFragment().apply {
                arguments = args
            }

        }

    }

}