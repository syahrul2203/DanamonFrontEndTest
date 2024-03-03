package com.syahrul.danamonfrontendtest.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user")
data class UserRecord (
    @PrimaryKey
    var id: Long,

    var userName: String? = null,
    var email: String? = null,
    var password: String? = null,
    var role: Int? = null

) : Parcelable