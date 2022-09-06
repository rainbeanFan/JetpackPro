package cn.jetpack.model

import androidx.databinding.BaseObservable
import cn.jetpack.BR
import java.io.Serializable

data class User(
    val id:Int,
    val userId:Long,
    val name:String,
    val avatar:String,
    val description:String,
    val likeCount:Int,
    val topCommentCount:Int,
    val followCount:Int,
    val followerCount:Int,
    val qqOpenId:String,
    val expires_time:Long,
    val score:Int,
    val historyCount:Int,
    val commentCount:Int,
    val favoriteCount:Int,
    val feedCount:Int,
    val hasFollow:Boolean
): BaseObservable(), Serializable{

    var _hasFollow:Boolean = false
     set(value) {
         _hasFollow = value
         notifyPropertyChanged(BR._all)
     }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (name != other.name) return false
        if (avatar != other.avatar) return false
        if (description != other.description) return false
        if (likeCount != other.likeCount) return false
        if (topCommentCount != other.topCommentCount) return false
        if (followCount != other.followCount) return false
        if (followerCount != other.followerCount) return false
        if (qqOpenId != other.qqOpenId) return false
        if (expires_time != other.expires_time) return false
        if (score != other.score) return false
        if (historyCount != other.historyCount) return false
        if (commentCount != other.commentCount) return false
        if (favoriteCount != other.favoriteCount) return false
        if (feedCount != other.feedCount) return false
        if (hasFollow != other.hasFollow) return false
        if (_hasFollow != other._hasFollow) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + avatar.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + likeCount
        result = 31 * result + topCommentCount
        result = 31 * result + followCount
        result = 31 * result + followerCount
        result = 31 * result + qqOpenId.hashCode()
        result = 31 * result + expires_time.hashCode()
        result = 31 * result + score
        result = 31 * result + historyCount
        result = 31 * result + commentCount
        result = 31 * result + favoriteCount
        result = 31 * result + feedCount
        result = 31 * result + hasFollow.hashCode()
        result = 31 * result + _hasFollow.hashCode()
        return result
    }

}
