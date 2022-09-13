package cn.jetpack.ui

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import cn.commonlibrary.global.AppGlobal
import cn.jetpack.model.Comment
import cn.jetpack.model.Feed
import cn.jetpack.model.TagList
import cn.jetpack.model.User

class InteractionPresenter {

    companion object {
        const val DATA_FROM_INTERACTION = "data_from_interaction"

        private const val URL_TOGGLE_FEED_LIK = "/ugc/toggleFeedLike"

        private const val URL_TOGGLE_FEED_DISS = "/ugc/dissFeed"

        private const val URL_SHARE = "/ugc/increaseShareCount"

        private const val URL_TOGGLE_COMMENT_LIKE = "/ugc/toggleCommentLike"

        @JvmStatic
        fun toggleFeedLike(owner: LifecycleOwner, feed: Feed){

        }

        @JvmStatic
        private fun toggleFeedLikeInternal(feed: Feed){

        }

        @JvmStatic
        fun toggleFeedDiss(owner: LifecycleOwner, feed: Feed){

        }

        @JvmStatic
        private fun toggleFeedDissInternal(feed: Feed){

        }

        @JvmStatic
        fun openShare(context: Context, feed: Feed){

        }

        @JvmStatic
        fun toggleCommentLike(owner: LifecycleOwner,comment: Comment){

        }

        @JvmStatic
        private fun toggleCommentLikeInternal(comment: Comment){

        }

        @JvmStatic
        fun toggleFeedFavorite(owner: LifecycleOwner, feed: Feed){

        }

        @JvmStatic
        private fun toggleFeedFavorite(feed: Feed){

        }

        @JvmStatic
        fun toggleFollowUser(owner: LifecycleOwner, feed: Feed){

        }

        @JvmStatic
        private fun toggleFollowUser(feed: Feed){

        }

        @JvmStatic
        fun deleteFeed(context: Context,itemId: Long):LiveData<Boolean>{
            val liveData = MutableLiveData <Boolean>()

            return liveData
        }

        @JvmStatic
        private fun deleteFeedInternal(liveData: MutableLiveData<Boolean>,itemId: Long){

        }

        @JvmStatic
        fun deleteFeedComment(context: Context,itemId: Long,commentId: Long):LiveData<Boolean>{
            val liveData = MutableLiveData <Boolean>()

            return liveData
        }

        @JvmStatic
        private fun deleteFeedCommentInternal(liveData: MutableLiveData<Boolean>,itemId: Long,commentId: Long){

        }

        @JvmStatic
        fun toggleTagLike(owner: LifecycleOwner, tagList:TagList){

        }

        @JvmStatic
        private fun toggleTagLikeInternal(tagList: TagList){

        }

        @SuppressLint("RestrictedApi")
        @JvmStatic
        private fun showToast(message: String){
            ArchTaskExecutor.getMainThreadExecutor().execute {
                Toast.makeText(
                    AppGlobal.getApplication(), message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        @JvmStatic
        private fun isLogin(owner: LifecycleOwner,observer: Observer<User>):Boolean{

            return false
        }

        @JvmStatic
        private fun loginObserver(observer: Observer<User>,liveData: LiveData<User>):
                Observer<User>{
            return object:Observer<User>{
                override fun onChanged(user: User) {
                    liveData.removeObserver(this)
                    observer.onChanged(user)
                }
            }
        }

    }

}