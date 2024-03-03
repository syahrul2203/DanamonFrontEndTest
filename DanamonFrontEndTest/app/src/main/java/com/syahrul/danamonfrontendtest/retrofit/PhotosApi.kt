package com.syahrul.danamonfrontendtest.retrofit

import com.syahrul.danamonfrontendtest.model.Photos
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosApi {

    @GET("photos")
    suspend fun getPhotosList(
        @Query("_page") page: Int? = 1,
        @Query("_limit") limit: Int? = 10
    ): List<Photos>

}