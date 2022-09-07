package cn.libnet

import java.lang.reflect.Type

interface Convert<T> {

    fun convert(response:String,type: Type):T

}