package cn.libnet

data class ApiResponse<T>(
    @JvmField
    var success: Boolean = false,
    @JvmField
    var status:Int? = null,
    @JvmField
    var message:String? = null,
    @JvmField
    var body:T? = null
)
