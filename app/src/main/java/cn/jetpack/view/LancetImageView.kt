package cn.jetpack.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import cn.commonlibrary.utils.PixUtils
import cn.commonlibrary.utils.ViewHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class LancetImageView : AppCompatImageView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        ViewHelper.setViewOutline(this, attrs!!, defStyleAttr, 0)
    }

    fun setImageUrl(view: LancetImageView, imageUrl: String, isCircle: Boolean) {
        view.setImageUrl(view, imageUrl, isCircle, 0)
    }

    fun setImageUrl(view: LancetImageView, imageUrl: String, isCircle: Boolean, radius: Int) {
        val builder = Glide.with(view).load(imageUrl)
        if (isCircle) {
            builder.transform(CircleCrop())
        } else if (radius > 0) {
            builder.transform(RoundedCornersTransformation(PixUtils.dp2px(radius), 0))
        }
        val layoutParams = view.layoutParams
        if (layoutParams != null && layoutParams.width > 0 && layoutParams.height > 0) {
            builder.override(layoutParams.width, layoutParams.height)
        }
        builder.into(view)


    }

    fun bindData(widthPx: Int, heightPx: Int, marginLeft: Int, imageUrl: String) {
        bindData(
            widthPx,
            heightPx,
            marginLeft,
            PixUtils.getScreenWidth(),
            PixUtils.getScreenWidth(),
            imageUrl
        )
    }

    fun bindData(
        widthPx: Int,
        heightPx: Int,
        marginLeft: Int,
        maxWidth: Int,
        maxHeight: Int,
        imageUrl: String
    ) {
        if (imageUrl.isNullOrBlank()) {
            visibility = GONE
            return
        } else {
            visibility = VISIBLE
        }

        if (widthPx <= 0 || heightPx <= 0) {
            Glide.with(this).load(imageUrl).into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    val height = resource.intrinsicHeight
                    val width = resource.intrinsicWidth
                    setSize(width, height, marginLeft, maxWidth, maxHeight)
                    setImageDrawable(resource)
                }
            })
            return
        }

        setSize(width, height, marginLeft, maxWidth, maxHeight)
        setImageUrl(this, imageUrl, false)
    }


    fun setSize(width: Int, height: Int, marginLeft: Int, maxWidth: Int, maxHeight: Int) {
        var fWidth = 0
        var fHeight = 0
        if (width > height) {
            fWidth = maxWidth
            fHeight = (height / (width * 1.0f / fWidth)).toInt()
        } else {
            fHeight = maxHeight
            fWidth = (width / (height * 1.0f / fHeight)).toInt()
        }
        val params = layoutParams.apply {
            this.width = fWidth
            this.height = fHeight
        }

        if (params is FrameLayout.LayoutParams) {
            params.leftMargin = if (height > width) PixUtils.dp2px(marginLeft) else 0
        } else if (params is LinearLayout.LayoutParams) {
            params.leftMargin = if (height > width) PixUtils.dp2px(marginLeft) else 0
        }
        layoutParams = params
    }

    companion object {

        @JvmStatic
        fun setBlurImageUrl(imageView: ImageView, blurUrl: String, radius: Int) {
            Glide.with(imageView).load(blurUrl).override(radius)
                .transform(BlurTransformation())
                .dontTransform()
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        imageView.background = resource
                    }

                })
        }

    }

}