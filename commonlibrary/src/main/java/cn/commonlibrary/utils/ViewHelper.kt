package cn.commonlibrary.utils

import android.content.res.TypedArray
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import cn.commonlibrary.R


const val RADIUS_ALL = 0;
const val RADIUS_LEFT = 1;
const val RADIUS_TOP = 2;
const val RADIUS_RIGHT = 3;
const val RADIUS_BOTTOM = 4;

class ViewHelper {

    companion object {

        @JvmStatic
        fun setViewOutline(view: View, attr: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
            val ta: TypedArray = view.context.obtainStyledAttributes(
                attr,
                R.styleable.viewOutLineStrategy,
                defStyleAttr,
                defStyleRes
            )
            val radius = ta.getDimensionPixelSize(R.styleable.viewOutLineStrategy_clip_radius, 0)
            val hideSide = ta.getInt(R.styleable.viewOutLineStrategy_clip_side, 0)
            ta.recycle()
            setViewOutline(view, radius, hideSide)
        }

        fun setViewOutline(owner: View, radius: Int, radiusSide: Int) {
            owner.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    val w = view.width
                    val h = view.height
                    if (w == 0 || h == 0) {
                        return
                    }

                    if (radiusSide !== RADIUS_ALL) {
                        var left = 0
                        var top = 0
                        var right = w
                        var bottom = h
                        if (radiusSide === RADIUS_LEFT) {
                            right += radius
                        } else if (radiusSide === RADIUS_TOP) {
                            bottom += radius
                        } else if (radiusSide === RADIUS_RIGHT) {
                            left -= radius
                        } else if (radiusSide === RADIUS_BOTTOM) {
                            top -= radius
                        }
                        outline.setRoundRect(left, top, right, bottom, radius.toFloat())
                        return
                    }

                    val top = 0
                    val left = 0
                    if (radius <= 0) {
                        outline.setRect(left, top, w, h)
                    } else {
                        outline.setRoundRect(left, top, w, h, radius.toFloat())
                    }
                }
            }

            owner.clipToOutline = radius > 0
            owner.invalidate()

        }

    }

}