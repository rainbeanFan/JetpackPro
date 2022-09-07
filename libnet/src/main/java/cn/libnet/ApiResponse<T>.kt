package cn.libnet

data class ApiResponse<T>(
    var success: Boolean = false,
    var status:Int? = null,
    var message:String? = null,
    var body:T? = null
)
