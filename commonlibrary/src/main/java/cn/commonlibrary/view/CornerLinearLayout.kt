package cn.commonlibrary.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import cn.commonlibrary.utils.ViewHelper

class CornerLinearLayout : LinearLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(
        context,
        attrs, defStyleAttr, defStyleRes
    ) {
        ViewHelper.setViewOutline(this, attrs!!, defStyleAttr, 0)
    }

}