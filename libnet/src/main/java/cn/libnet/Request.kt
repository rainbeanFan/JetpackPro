package cn.libnet


import androidx.annotation.IntDef
import java.lang.annotation.RetentionPolicy
import java.lang.reflect.Type


const val CACHE_ONLY = 1
const val CACHE_FIRST = 2
const val NET_ONLY = 3
const val NET_CACHE = 4

abstract class Request<T,R>():Cloneable {

    protected lateinit var mUrl:String

    protected var headers = mutableMapOf<String, String>()
    protected var params = mutableMapOf<String, Any>()

    private var cacheKey:String = ""
    private var mType:Type? = null

    private var mCacheStrategy = NET_ONLY

    @IntDef(CACHE_ONLY, CACHE_FIRST, NET_CACHE, NET_ONLY)
    @Retention(AnnotationRetention.SOURCE)
    annotation class CacheStrategy {

    }

    constructor(url:String) : this(){
        mUrl = url
    }

    fun addHeader(key:String, value:String):R{
        headers[key] = value
        return this as R
    }

    fun addParam(key:String, value:Any?):R{
        if (value ==null) return this as R
        try {
            if (value!!.javaClass == String::class.java){
                params[key] = value
            }else{
                val field = value.javaClass.getField("TYPE")
                val clz:Class<*> = field.get(null) as Class<*>
                if (clz.isPrimitive){
                    params[key] = value
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





}