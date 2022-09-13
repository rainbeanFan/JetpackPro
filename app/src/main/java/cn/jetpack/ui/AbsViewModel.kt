package cn.jetpack.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagingConfig


abstract class AbsViewModel<T>:ViewModel() {

    protected var mConfig:PagingConfig

    private lateinit var mDataSource: DataSource<Any,Any>

    private lateinit var mPageData:LiveData<PagedList<*>>

    private var mBoundaryPageData = MutableLiveData <Boolean>()

    init {
        mConfig = PagingConfig(
            pageSize = 10,
            initialLoadSize = 12
        )

//        mPageData =
//            LivePagedListBuilder(
//            factory,mConfig
//        ).build()

    }

    fun getPageData() = mPageData

    fun getDataSource() = mDataSource

    fun getBoundaryPageData() = mBoundaryPageData

    val callback = object : PagedList.BoundaryCallback<Any>(){
        override fun onZeroItemsLoaded() {
            mBoundaryPageData.postValue(false)
        }

        override fun onItemAtFrontLoaded(itemAtFront: Any) {
            mBoundaryPageData.postValue(true)
        }

        override fun onItemAtEndLoaded(itemAtEnd: Any) {

        }
    }

    val factory = object : DataSource.Factory<Any,Any>() {
        override fun create(): DataSource<Any, Any> {
            if(mDataSource == null || mDataSource.isInvalid) {
                mDataSource = createDataSource() as DataSource<Any, Any>
            }
            return mDataSource
        }
    }

    abstract fun createDataSource():DataSource<*,*>

    override fun onCleared() {

    }

}