package cn.libnet

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class CacheManager {

    companion object{

        @JvmStatic
        private fun toObject(date:ByteArray):Any? {
            var bais: ByteArrayInputStream? = null
            var ois: ObjectInputStream? = null
            try {
                bais = ByteArrayInputStream(date)
                ois = ObjectInputStream(bais)
                return ois.readObject()
            }catch(e: IOException){
                e.printStackTrace()
            }finally {
                try {
                    bais?.close()
                    ois?.close()
                }catch(e: IOException){
                    e.printStackTrace()
                }
            }
            return null
        }

        @JvmStatic
        private fun <T> toByteArray(obj:T):ByteArray {
            var baos:ByteArrayOutputStream? = null
            var oos:ObjectOutputStream? = null
            try {
                baos = ByteArrayOutputStream()
                oos = ObjectOutputStream(baos)
                oos.writeObject(obj)
                oos.flush()
                return baos.toByteArray()
            }catch(e: IOException){
                e.printStackTrace()
            }finally {
                try {
                    baos?.close()
                    oos?.close()
                }catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return ByteArray(0)
        }

        @JvmStatic
        fun <T> delete(key:String,body:T){
            val cache = Cache().apply {
                this.key = key
                this.data = toByteArray(body)
            }

            CacheDatabase.getInstance().getCacheDao().delete(cache)
        }

        @JvmStatic
        fun <T> insert(key:String,body:T){
            val cache = Cache().apply {
                this.key = key
                this.data = toByteArray(body)
            }

            CacheDatabase.getInstance().getCacheDao().save(cache)
        }

        @JvmStatic
        fun getCache(key:String):Any?{
            val cache = CacheDatabase.getInstance().getCacheDao().getCache(key)
            if (cache?.data != null){
                return toObject(cache.data!!)
            }
            return null
        }

    }

}