package cn.jetpack.exoplayer

import android.view.ViewGroup

interface IPlayTarget {

    fun getOwner():ViewGroup

    fun onActive()

    fun inActive()

    fun isPlaying():Boolean

}