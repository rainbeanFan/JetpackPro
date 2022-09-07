package cn.libnet

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

class ApiService {

    companion object{
        var sBaseUrl:String? = null
        var sConvert:Convert<*>? = null

        val okhttpClient = OkHttpClient.Builder()
            .readTimeout(5,TimeUnit.SECONDS)
            .writeTimeout(5,TimeUnit.SECONDS)
            .connectTimeout(5,TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        //http 证书问题
        var trustManagers = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })

        @JvmStatic
        fun init(baseUrl: String,convert: Convert<*>){
            sBaseUrl = baseUrl
            sConvert = convert
        }

        @JvmStatic
        fun <T>  get(url: String):GetRequest<T>{
            return GetRequest(sBaseUrl+url)
        }

        @JvmStatic
        fun <T>  post(url: String):PostRequest<T>{
            return PostRequest(sBaseUrl+url)
        }

    }


    init {
        val ssl :SSLContext = SSLContext.getInstance("SSL").apply {
            this.init(null,trustManagers,SecureRandom())
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(ssl.socketFactory)
        HttpsURLConnection.setDefaultHostnameVerifier { hostname, session -> true }

    }

}