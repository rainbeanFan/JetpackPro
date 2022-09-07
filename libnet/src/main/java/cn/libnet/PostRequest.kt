package cn.libnet

import okhttp3.FormBody
import okhttp3.Request

class PostRequest<T>(url: String) : LancetRequest<T, ILancetRequest>(
    url
) {
    override fun generateRequest(builder: Request.Builder): Request {
        //post请求表单提交
        val bodyBuilder = FormBody.Builder()
        mParams.entries.forEach { entry ->
            bodyBuilder.add(entry.key, entry.value.toString())
        }
        return builder.url(mUrl).post(bodyBuilder.build()).build()
    }


}