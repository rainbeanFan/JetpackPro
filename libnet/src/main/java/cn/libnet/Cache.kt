package cn.libnet

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "cache")
class Cache: Serializable {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    var key:String = ""

    var data:ByteArray? = null

}