package cn.libnet

class GetRequest<T>(url: String) : LancetRequest<T,GetRequest<T>>(url) {
    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        val url = UrlCreator.createUrlFromParams(mUrl,mParams)
        return builder.get().url(url).build()
    }
}