package cn.jetpack.model

import androidx.databinding.BaseObservable
import java.io.Serializable

data class Comment(
    val id: Int,
    val itemId: Long,
    val commentId: Long,
    val userId: Long,
    val commentType:Int,
    val createTime: Long,
    val commentCount: Int,
    val likeCount: Int,
    val commentText: String,
    val imageUrl: String,
    val videoUrl: String,
    val width: Int,
    val height: Int,
    val hasLiked: Boolean,
    val author:User,
    var ugc:Ugc
): BaseObservable(), Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comment

        if (id != other.id) return false
        if (itemId != other.itemId) return false
        if (commentId != other.commentId) return false
        if (userId != other.userId) return false
        if (commentType != other.commentType) return false
        if (createTime != other.createTime) return false
        if (commentCount != other.commentCount) return false
        if (likeCount != other.likeCount) return false
        if (commentText != other.commentText) return false
        if (imageUrl != other.imageUrl) return false
        if (videoUrl != other.videoUrl) return false
        if (width != other.width) return false
        if (height != other.height) return false
        if (hasLiked != other.hasLiked) return false
        if (author != other.author) return false
        if (ugc != other.ugc) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + itemId.hashCode()
        result = 31 * result + commentId.hashCode()
        result = 31 * result + userId.hashCode()
        result = 31 * result + commentType
        result = 31 * result + createTime.hashCode()
        result = 31 * result + commentCount
        result = 31 * result + likeCount
        result = 31 * result + commentText.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + videoUrl.hashCode()
        result = 31 * result + width
        result = 31 * result + height
        result = 31 * result + hasLiked.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + ugc.hashCode()
        return result
    }

}
