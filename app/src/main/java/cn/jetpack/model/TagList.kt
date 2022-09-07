package cn.jetpack.model

import androidx.databinding.BaseObservable
import cn.jetpack.model.TagList
import android.text.TextUtils
import androidx.databinding.Bindable
import cn.jetpack.BR
import java.io.Serializable

class TagList : BaseObservable(), Serializable {
    /**
     * id : 8
     * icon :
     * background :
     * activityIcon :
     * title : 纹身技术哪家强
     * intro : “你怎么纹了一个电风扇？”
     * “那叫四叶草！”
     * feedNum : 1234
     * tagId : 126137
     * enterNum : 79788
     * followNum : 79151
     * hasFollow : false
     */
    var id = 0
    var icon: String? = null
    var background: String? = null
    var activityIcon: String? = null
    var title: String? = null
    var intro: String? = null
    var feedNum = 0
    var tagId: Long = 0
    var enterNum = 0
    var followNum = 0
    var hasFollow = false
    override fun equals(obj: Any?): Boolean {
        if (obj == null || obj !is TagList) return false
        val newOne = obj
        return (id == newOne.id && TextUtils.equals(icon, newOne.icon)
                && TextUtils.equals(background, newOne.background)
                && TextUtils.equals(activityIcon, newOne.activityIcon)
                && TextUtils.equals(title, newOne.title)
                && TextUtils.equals(intro, newOne.intro)
                && feedNum == newOne.feedNum && tagId == newOne.tagId && enterNum == newOne.enterNum && followNum == newOne.followNum && hasFollow == newOne.hasFollow)
    }

    @Bindable
    fun isHasFollow(): Boolean {
        return hasFollow
    }

    @JvmName("setHasFollowed")
    fun setHasFollow(follow: Boolean) {
        hasFollow = follow
        notifyPropertyChanged(BR._all)
    }
}