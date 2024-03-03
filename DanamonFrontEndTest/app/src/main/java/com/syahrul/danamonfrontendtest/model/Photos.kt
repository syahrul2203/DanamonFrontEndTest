package com.syahrul.danamonfrontendtest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photos(
    val albumId : Int? = null,
    val id : Int? = null,
    val title : String? = null,
    val url : String? = null,
    val thumbnailUrl : String? = null
) : Parcelable
