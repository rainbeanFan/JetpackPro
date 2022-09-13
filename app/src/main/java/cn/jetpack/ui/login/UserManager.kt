package cn.jetpack.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.commonlibrary.global.AppGlobal
import cn.commonlibrary.global.AppGlobal.Companion.getApplication
import cn.jetpack.model.User
import cn.libnet.ApiResponse
import cn.libnet.ApiService
import cn.libnet.CacheManager
import cn.libnet.JsonCallback

class UserManager private constructor() {
    private var userLiveData: MutableLiveData<User?>? = null
    private var mUser: User? = null

    init {
        val cache = CacheManager.getCache(KEY_CACHE_USER) as User
        if (cache != null) { //&& cache.expires_time > System.currentTimeMillis()
            mUser = cache
        }
    }

    fun save(user: User?) {
        mUser = user
        CacheManager.insert(KEY_CACHE_USER, user)
        if (getUserLiveData().hasObservers()) {
            getUserLiveData().postValue(user)
        }
    }

    fun login(context: Context?): LiveData<User?> {
        val intent = Intent()
//        安全跳转
        if (intent.resolveActivity(context!!.packageManager)!=null){
            context.startActivity(intent)
        }
//        Intent intent = new Intent(context, LoginActivity.class);
//        if (!(context instanceof Activity)) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        context.startActivity(intent);
        return getUserLiveData()
    }

    //解决登录卡死的问题，expires_time时间戳有问题，会一直小于 System.currentTimeMillis();
    //? false : mUser.expires_time > System.currentTimeMillis();
    val isLogin: Boolean
        get() =//解决登录卡死的问题，expires_time时间戳有问题，会一直小于 System.currentTimeMillis();
            mUser != null //? false : mUser.expires_time > System.currentTimeMillis();
    val user: User?
        get() = if (isLogin) mUser else null
    val userId: Long
        get() = if (isLogin) mUser!!.userId else 0

    fun refresh(): LiveData<User?> {
        if (!isLogin) {
            return login(getApplication())
        }
        val liveData = MutableLiveData<User?>()
        ApiService.get<User>("/user/query")
            .addParam("userId", userId)
            .execute(object : JsonCallback<User>() {
                override fun onSuccess(response: ApiResponse<User>) {
                    save(response.body)
                    liveData.postValue(user)
                }

                @SuppressLint("RestrictedApi")
                override fun onError(response: ApiResponse<User>) {
                    ArchTaskExecutor.getMainThreadExecutor().execute {
                        Toast.makeText(
                            AppGlobal.getApplication(),
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    liveData.postValue(null)
                }
            })
        return liveData
    }

    /**
     * bugfix:  liveData默认情况下是支持黏性事件的，即之前已经发送了一条消息，当有新的observer注册进来的时候，也会把先前的消息发送给他，
     *
     *
     * 就造成了[cn.jetpack.MainActivity.onNavigationItemSelected]死循环
     *
     *
     * 那有两种解决方法
     * 1.我们在退出登录的时候，把livedata置为空，或者将其内的数据置为null
     * 2.利用我们改造的stickyLiveData来发送这个登录成功的事件
     *
     *
     * 我们选择第一种,把livedata置为空
     */
    fun logout() {
        CacheManager.delete(KEY_CACHE_USER, mUser)
        mUser = null
        userLiveData = null
    }

    private fun getUserLiveData(): MutableLiveData<User?> {
        if (userLiveData == null) {
            userLiveData = MutableLiveData()
        }
        return userLiveData!!
    }

    companion object {
        private const val KEY_CACHE_USER = "cache_user"
        private val mUserManager = UserManager()
        fun get(): UserManager {
            return mUserManager
        }
    }
}