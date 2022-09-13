package cn.jetpack

interface ILancetRequest

abstract class LRequest<T,out R:ILancetRequest>():Cloneable {

    companion object {

        @JvmStatic
        fun build():R{
            return this as R
        }

    }

    override fun clone(): LRequest<T,out R> {
        return super.clone() as LRequest<T, out R>
    }

}