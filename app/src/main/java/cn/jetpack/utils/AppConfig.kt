package cn.jetpack.utils

import cn.jetpack.model.Destination
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import java.io.*

class AppConfig {

    companion object {

        private var sDestConfig: HashMap<String, Destination>? = null

        @JvmStatic
        fun getDestConfig(): HashMap<String, Destination> {
            sDestConfig ?: synchronized(this) {
                sDestConfig ?: {
                    val content = parseFile("destination.json")

                    sDestConfig = JSON.parseObject(content,
                        object : TypeReference<HashMap<String, Destination>>() {

                        }.type)
                }
            }
            return sDestConfig!!
        }

        fun parseFile(fileName: String): String {
            val assets = AppGlobal.getApplication().resources.assets
            var stream: InputStream? = null
            var reader: BufferedReader? = null
            val builder = StringBuilder()
            try {
                stream = assets.open(fileName)
                reader = BufferedReader(InputStreamReader(stream))
                var line: String? = null
                while ((reader.readLine()) != null) {
                    line = reader.readLine()
                    builder.append(line)
                }
            } catch (e: IOException) {

            } finally {
                try {
                    stream?.close()
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return builder.toString()
        }

    }

}