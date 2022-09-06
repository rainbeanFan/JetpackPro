package cn.libnet

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cn.commonlibrary.global.AppGlobal


@Database(entities = [Cache::class], version = 1)
abstract class CacheDatabase:RoomDatabase() {

    companion object {

        @Volatile
        private var database:CacheDatabase? = null

        @JvmStatic
        fun getInstance(): CacheDatabase {
            database = Room.databaseBuilder(AppGlobal.getApplication(),CacheDatabase::class.java,"")
                .allowMainThreadQueries()
                .build()
            return database!!
        }

    }

    abstract fun getCacheDao():CacheDao

}