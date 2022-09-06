package cn.jetpack.model

data class BottomBar(
    val activeColor: String,
    val inActiveColor: String,
    val selectedTab:Int,
    val tabs: MutableList<TabsBean>
)

data class TabsBean(
    val size: Int,
    val enable: Boolean,
    val index: Int,
    val pageUrl: String,
    val title: String = "",
    val tintColor: String? = null,
)
