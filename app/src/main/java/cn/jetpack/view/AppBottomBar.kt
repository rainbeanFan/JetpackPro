package cn.jetpack.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import cn.jetpack.R
import cn.jetpack.model.BottomBar
import cn.jetpack.utils.AppConfig
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView


class AppBottomBar : BottomNavigationView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    @SuppressLint("RestrictedApi")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        val bottomBar = AppConfig.getBottomBar(context)
        val tabs = bottomBar.tabs

        val states = arrayOf(intArrayOf(android.R.attr.state_selected), intArrayOf())
        val colors = intArrayOf(
            Color.parseColor(bottomBar.activeColor),
            Color.parseColor(bottomBar.inActiveColor)
        )

        val colorStateList = ColorStateList(states, colors)

        itemIconTintList = colorStateList
        itemTextColor = colorStateList
        labelVisibilityMode = LABEL_VISIBILITY_LABELED

        selectedItemId = bottomBar.selectTab

        tabs.forEach { tabBean ->
            if (!tabBean.enable) return
            val id = getId(tabBean.pageUrl)
            if (id < 0) {
                return
            }
            val item = menu.add(0, 0, tabBean.index, tabBean.title)
            item.setIcon(sIcon[tabBean.index])

        }

        tabs.forEach { tabsBean ->
            val iconSize = dp2Px(tabsBean.size)
            val menuView = getChildAt(0) as BottomNavigationMenuView
            val itemView = menuView.getChildAt(tabsBean.index) as BottomNavigationItemView

            itemView.setIconSize(iconSize)

            if (tabsBean.title.isNullOrBlank()) {
                itemView.setIconTintList(ColorStateList.valueOf(Color.parseColor(tabsBean.tintColor)))
                itemView.setShifting(false)
            }
        }
    }

    private fun dp2Px(size: Int): Int {
        val value = context.resources.displayMetrics.density * size + 0.5f
        return value.toInt()
    }

    private fun getId(pageUrl: String): Int {
        val destination = AppConfig.getDestConfig(context)?.get(pageUrl) ?: return -1
        return destination.destinationId

    }

    companion object {
        val sIcon = intArrayOf(
            R.drawable.icon_tab_home, R.drawable.icon_tab_sofa,
            R.drawable.icon_tab_publish, R.drawable.icon_tab_find, R.drawable.icon_tab_mine
        )
    }

}