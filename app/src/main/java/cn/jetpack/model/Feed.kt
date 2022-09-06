package cn.jetpack.model

import androidx.databinding.BaseObservable
import java.io.Serializable



class Feed:BaseObservable(), Serializable {

    companion object{
        const val TYPE_IMAGE_TEXT = 1
        const val TYPE_VIDEO = 2
    }

    var id:Int? = null
    var itemId:Long? = null
    var itemType:Int = TYPE_IMAGE_TEXT
    var createTime:Long? = null
    var duration:Double? = null
    var feedsStr:String? = null
    var authorId:Long? = null
    var activityIcon:String? = null
    var activityTitle:String? = null
    var width:Int? = null
    var height:Int? = null
    var url:String? = null
    var cover:String? = null
    var author:User? = null
    var topComment:Comment? = null
    var ugc:Ugc? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Feed

        if (id != other.id) return false
        if (itemId != other.itemId) return false
        if (itemType != other.itemType) return false
        if (createTime != other.createTime) return false
        if (duration != other.duration) return false
        if (feedsStr != other.feedsStr) return false
        if (authorId != other.authorId) return false
        if (activityIcon != other.activityIcon) return false
        if (activityTitle != other.activityTitle) return false
        if (width != other.width) return false
        if (height != other.height) return false
        if (url != other.url) return false
        if (cover != other.cover) return false
        if (author != other.author) return false
        if (topComment != other.topComment) return false
        if (ugc != other.ugc) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (itemId?.hashCode() ?: 0)
        result = 31 * result + itemType
        result = 31 * result + (createTime?.hashCode() ?: 0)
        result = 31 * result + (duration?.hashCode() ?: 0)
        result = 31 * result + (feedsStr?.hashCode() ?: 0)
        result = 31 * result + (authorId?.hashCode() ?: 0)
        result = 31 * result + (activityIcon?.hashCode() ?: 0)
        result = 31 * result + (activityTitle?.hashCode() ?: 0)
        result = 31 * result + (width ?: 0)
        result = 31 * result + (height ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (cover?.hashCode() ?: 0)
        result = 31 * result + (author?.hashCode() ?: 0)
        result = 31 * result + (topComment?.hashCode() ?: 0)
        result = 31 * result + (ugc?.hashCode() ?: 0)
        return result
    }


}
