package cn.jetpack.model

data class Destination(
    val isFragment: Boolean,
    val asStarter: Boolean,
    val needLogin: Boolean,
    val pagUrl: String,
    val className: String,
    val destinationId: Int
)
