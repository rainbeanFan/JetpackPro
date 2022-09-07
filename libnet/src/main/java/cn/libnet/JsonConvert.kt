package cn.libnet

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import java.lang.reflect.Type

class JsonConvert:Convert<Any?> {
    override fun convert(response: String, type: Type): Any? {
        val data: JSONObject? = JSON.parseObject(response).getJSONObject("data")
        if (data != null) {
            val data1 = data["data"]
            return JSON.parseObject(data1.toString(),type)
        }
        return null
    }
}