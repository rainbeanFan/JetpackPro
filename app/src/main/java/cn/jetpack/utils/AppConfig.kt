package cn.jetpack.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import cn.commonlibrary.global.AppGlobal
import cn.jetpack.model.BottomBar
import cn.jetpack.model.BottomBar.Tab
import cn.jetpack.model.Destination
import cn.jetpack.model.SofaTab
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import java.io.*
import java.util.Collections

class AppConfig {

    companion object {

        private var sDestConfig: HashMap<String, Destination?>? = null
        private var sBottomBar: BottomBar? = null
        private var sSoftTab: SofaTab? = null
        private var sFindTabConfig: SofaTab? = null

        @JvmStatic
        fun getDestConfig(context: Context): HashMap<String, Destination?>? {
            if (sDestConfig == null) {
//                val content = parseFile(context,"destination.json")
//                sDestConfig = JSON.parseObject(content,
//                    object : TypeReference<HashMap<String, Destination?>>() {
//                    }.type)
            }

            return sDestConfig
        }

        @JvmStatic
        fun getBottomBar(context: Context): BottomBar {
            if (sBottomBar == null){

                sBottomBar = BottomBar()
                sBottomBar.apply{
                    this!!.activeColor = "#333333"
                    inActiveColor = "#666666"
                    selectTab = 1
                    tabs = arrayListOf()
                }

                for (i in 0 until 5){
                    val tab = Tab().apply {
                        when(i){
                            0 -> {
                                size = 24
                                enable = true
                                index = i
                                pageUrl = "main/tabs/home"
                                title = "Home"
                            }
                            1 -> {
                                size = 24
                                enable = true
                                index = i
                                pageUrl = "main/tabs/sofa"
                                title = "Sofa"
                            }
                            2 -> {
                                size = 40
                                enable = true
                                index = i
                                tintColor = "#FF678F"
                                pageUrl = "main/tabs/publish"
                                title = ""
                            }
                            3 -> {
                                size = 24
                                enable = true
                                index = i
                                pageUrl = "main/tabs/find"
                                title = "Find"
                            }
                            4 -> {
                                size = 24
                                enable = true
                                index = i
                                pageUrl = "main/tabs/my"
                                title = "Mine"
                            }
                        }
                    }
                    sBottomBar.apply {
                        this!!.tabs.add(tab)
                    }
                }

//                val content = parseFile(context,"main_tabs_config.json")
//                sBottomBar = JSON.parseObject(content,BottomBar::class.java)
            }
            return sBottomBar!!
        }

        @JvmStatic
        fun getSoftTabConfig(context: Context):SofaTab{
            if (sSoftTab == null){
                val content = parseFile(context,"sofa_tabs_config.json")
                sSoftTab = JSON.parseObject(content,SofaTab::class.java)
                sSoftTab!!.tabs!!.sortWith { o1, o2 -> if (o1.index < o2.index) -1 else 1 }
            }
            return sSoftTab!!
        }

        @JvmStatic
        fun getFindTabConfig(context: Context):SofaTab{
            if (sFindTabConfig == null){
                val content = parseFile(context,"find_tabs_config.json")
                sFindTabConfig = JSON.parseObject(content,SofaTab::class.java)
                sFindTabConfig!!.tabs!!.sortWith { o1, o2 -> if (o1.index < o2.index) -1 else 1 }
            }
            return sFindTabConfig!!
        }

        private fun parseFile(context: Context,fileName: String): String {
            val assets = context.assets
            var sr = StringBuffer()
            var bufferReader = BufferedReader(InputStreamReader(assets.open(fileName)))
            while (  bufferReader.readLine() != null){
                sr.append(bufferReader.readLine())
            }
            return sr.toString()
        }
    }

}