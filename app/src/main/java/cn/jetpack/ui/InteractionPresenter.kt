package cn.jetpack.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import cn.commonlibrary.global.AppGlobal
import cn.jetpack.model.Feed
import cn.jetpack.model.User
import com.bumptech.glide.module.AppGlideModule

class InteractionPresenter {

    companion object {
        const val DATA_FROM_INTERACTION = "data_from_interaction"

        private const val URL_TOGGLE_FEED_LIK = "/ugc/toggleFeedLike"

        private const val URL_TOGGLE_FEED_DISS = "/ugc/dissFeed"

        private const val URL_SHARE = "/ugc/increaseShareCount"

        private const val URL_TOGGLE_COMMENT_LIKE = "/ugc/toggleCommentLike"

        @JvmStatic
        fun toggleCommentLike(owner: LifecycleOwner,feed: Feed){

        }



        @SuppressLint("RestrictedApi")
        @JvmStatic
        private fun showToast(message: String){
            ArchTaskExecutor.getMainThreadExecutor().execute(Runnable{
                Toast.makeText(AppGlobal.getApplication(),message,
                Toast.LENGTH_SHORT).show()
            })
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