package cn.jetpack.model

import androidx.databinding.BaseObservable
import cn.jetpack.BR
import java.io.Serializable

data class Ugc(
    @set:JvmName("setLikeCount")
    var likeCount: Int,
    val shareCount: Int,
    val commentCount: Int,
    var hasFavorite: Boolean,
    @set:JvmName("setHasLike")
    var hasLike: Boolean,
    val hasDislike: Boolean
):BaseObservable(), Serializable {

    var _shareCount: Int = shareCount
        set(value){
            field = value
            notifyPropertyChanged(BR._all)
        }

    var _hasDislike: Boolean = hasDislike
        set(value){
            if (hasDislike == value){
                return
            }
            if (value){
                hasLike = false
            }
            field = value
            notifyPropertyChanged(BR._all)
        }

    var _hasLike = hasLike
        set(value){
            if(hasLike == value){
                return
            }
            if (value){
                likeCount += 1
                _hasDislike = false
            }else{
                likeCount -= 1
            }
            hasLike = value
            notifyPropertyChanged(BR._all)
        }

    var _hasFavorite = hasFavorite
        set(value){
            hasFavorite = value
            notifyPropertyChanged(BR._all)
        }

    override fun toString(): String {
        return "Ugc(likeCount=$likeCount, shareCount=$shareCount, commentCount=$commentCount, hasFavorite=$hasFavorite, hasLike=$hasLike, hasDislike=$hasDislike, _shareCount=$_shareCount, _hasDislike=$_hasDislike, _hasLike=$_hasLike)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ugc

        if (likeCount != other.likeCount) return false
        if (shareCount != other.shareCount) return false
        if (commentCount != other.commentCount) return false
        if (hasFavorite != other.hasFavorite) return false
        if (hasLike != other.hasLike) return false
        if (hasDislike != other.hasDislike) return false
        if (_shareCount != other._shareCount) return false
        if (_hasDislike != other._hasDislike) return false
        if (_hasLike != other._hasLike) return false

        return true
    }

    override fun hashCode(): Int {
        var result = likeCount
        result = 31 * result + shareCount
        result = 31 * result + commentCount
        result = 31 * result + hasFavorite.hashCode()
        result = 31 * result + hasLike.hashCode()
        result = 31 * result + hasDislike.hashCode()
        result = 31 * result + _shareCount
        result = 31 * result + _hasDislike.hashCode()
        result = 31 * result + _hasLike.hashCode()
        return result
    }


}
