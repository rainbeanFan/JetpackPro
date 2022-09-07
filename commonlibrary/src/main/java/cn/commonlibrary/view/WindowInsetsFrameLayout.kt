package cn.commonlibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout

class WindowInsetsFrameLayout : FrameLayout {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    override fun addView(child: View) {
        super.addView(child)
        requestApplyInsets()
    }

    override fun dispatchApplyWindowInsets(insets: WindowInsets): WindowInsets {
        var windowInsets = super.dispatchApplyWindowInsets(insets)
        if (!insets.isConsumed) {
            val childCount = childCount
            for (i in 0 until childCount) {
                windowInsets = getChildAt(i).dispatchApplyWindowInsets(insets)
            }
        }
        return windowInsets
    }
}