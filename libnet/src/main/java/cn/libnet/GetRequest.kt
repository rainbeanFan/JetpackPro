package cn.libnet

class GetRequest<T>(private val url: String) : LancetRequest<T, ILancetRequest>(url) {
    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        val url = UrlCreator.createUrlFromParams(mUrl,mParams)
        return builder.get().url(url).build()
    }
}