package cn.libnet


import android.annotation.SuppressLint
import androidx.annotation.IntDef
import androidx.arch.core.executor.ArchTaskExecutor
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


const val CACHE_ONLY = 1
const val CACHE_FIRST = 2
const val NET_ONLY = 3
const val NET_CACHE = 4


abstract class LancetRequest<T,out R:LancetRequest<T,R>>(val mUrl: String):Cloneable {

    protected var headers = mutableMapOf<String, String>()
    protected var mParams = mutableMapOf<String, Any>()

    private var cacheKey:String = ""
    private var mType:Type? = null

    private var mCacheStrategy = NET_ONLY

    @IntDef(CACHE_ONLY, CACHE_FIRST, NET_CACHE, NET_ONLY)
    @Retention(AnnotationRetention.SOURCE)
    annotation class CacheStrategy {

    }

    fun addHeader(key:String, value:String):R{
        headers[key] = value
        return this as R
    }

    fun addParam(key:String, value:Any?):R{
        if (value ==null) return this as R
        try {
            if (value!!.javaClass == String::class.java){
                mParams[key] = value
            }else{
                val field = value.javaClass.getField("TYPE")
                val clz:Class<*> = field.get(null) as Class<*>
                if (clz.isPrimitive){
                    mParams[key] = value
                }
            }
        }catch (e:NoSuchFileException){
            e.printStackTrace()
        }catch (e:IllegalAccessException){
            e.printStackTrace()
        }
        return this as R
    }

    fun cacheStrategy(@CacheStrategy cacheStrategy:Int):R{
        this.mCacheStrategy = cacheStrategy
        return this as R
    }

    fun cacheKey(key: String):R{
        this.cacheKey = key
        return this as R
    }

    fun responseType(type: Type):R{
        this.mType = type
        return this as R
    }

    fun responseType(claz: Class<*>):R{
        this.mType = claz
        return this as R
    }

    private fun getCall(): Call {
        val builder = Request.Builder()
        addHeaders(builder)
        val request = generateRequest(builder)
        return ApiService.okhttpClient.newCall(request)
    }

    protected abstract fun generateRequest(builder: Request.Builder):Request

    private fun addHeaders(builder: Request.Builder){
        headers.entries.forEach {
            builder.addHeader(it.key,it.value)
        }
    }

    fun execute():ApiResponse<T>?{
        if(mType == null) throw RuntimeException("同步方法，response返回值类型不能为null")
        if(mCacheStrategy == CACHE_ONLY) return readCache()
        if(mCacheStrategy != CACHE_ONLY){
            var result:ApiResponse<T>?=null
            try {
                val response = getCall().execute()
                result = parseResponse(response, null)
            }catch (e: IOException){
                e.printStackTrace()
                if(result == null){
                    result = ApiResponse()
                    result.message = e.message
                }
            }
            return result
        }
        return null
    }

    @SuppressLint("RestrictedApi")
    fun execute(callback:JsonCallback<T>){
        if(mCacheStrategy!=NET_ONLY){
            ArchTaskExecutor.getIOThreadExecutor().execute {
                val response = readCache()
                if (callback != null && response.body != null) {
                    callback.onCacheSuccess(response)
                }
            }
        }

        if(mCacheStrategy!=CACHE_ONLY){
            getCall().enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    val result = ApiResponse<T>()
                    result.message = e.message
                    callback?.onError(result)
                }

                override fun onResponse(call: Call, response: Response) {
                    val result = parseResponse(response,callback)
                    if (result.success){
                        callback?.onSuccess(result)
                    }else{
                        callback?.onError(result)
                    }
                }
            })
        }

    }

    private fun readCache():ApiResponse<T>{
        val key = if (cacheKey.isNullOrBlank()) generateCacheKey() else cacheKey
        val cache = CacheManager.getCache(key)
        val result = ApiResponse<T>().apply{
            status = 304
            message = "缓存获取成功"
            body = cache as T
            success = true
        }
        return result
    }

    private fun parseResponse(response: Response,callback:JsonCallback<T>?): ApiResponse<T> {
        var message:String? = null
        var status = response.code
        var success = response.isSuccessful
        var result = ApiResponse<T>()
        val convert = ApiService.sConvert

        try {
            val content = response.body?.string()?:""
            if(success){
                if(callback!=null){
                    val type = callback.javaClass.genericSuperclass as ParameterizedType
                    val argument = type.actualTypeArguments[0]
                    result.body = convert!!.convert(content, argument) as T
                }else if(mType!=null){
                    result.body = convert!!.convert(content, mType!!) as T
                }
            }else{
                message = content
            }
        }catch (e:Exception){
            message = e.message
            success = false
            status = 0
        }

        result.success = success
        result.status = status
        result.message = message

        if(mCacheStrategy!= NET_ONLY && result.success && result.body != null){
            saveCache(result.body!!)
        }

        return result
    }

    private fun saveCache(body:T){
        val key = if (cacheKey.isNullOrBlank()) generateCacheKey() else cacheKey
        CacheManager.insert(key, body)
    }

    private fun generateCacheKey():String{
        cacheKey = UrlCreator.createUrlFromParams(mUrl, mParams)
        return cacheKey
    }

    override fun clone(): LancetRequest<T, R> {
        return super.clone() as LancetRequest<T, R>
    }

}