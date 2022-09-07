package cn.libnet

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class UrlCreator {

    companion object {

        @JvmStatic
        fun createUrlFromParams(url: String, params: MutableMap<String, Any>): String {
            val builder = StringBuilder()
                .append(url)
            if ((url.indexOf("?") > 0) or
                (url.indexOf("&") > 0)
            ) {
                builder.append("&")
            } else {
                builder.append("?")
            }

            params.entries.forEach { entry ->
                try {
                    val value = URLEncoder.encode(entry.value.toString(), "UTF-8")
                    builder.append(entry.key).append("=").append(value).append("&")
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }
            }
            builder.deleteCharAt(builder.length - 1)
            return builder.toString()
        }

    }

}