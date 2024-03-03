package com.syahrul.danamonfrontendtest.repository

import com.syahrul.danamonfrontendtest.model.Photos
import com.syahrul.danamonfrontendtest.room.UserRecord

interface BaseUserRepository {

    suspend fun getAllUserData(): List<UserRecord>

    suspend fun getUserByEmails(email: String): List<UserRecord>

    suspend fun getUserById(id: Long): List<UserRecord>

    suspend fun registerNewUser(
        userName: String,
        email: String,
        password: String,
        role: Int,
    ) : Long

    suspend fun storeUser(user: UserRecord)

    suspend fun getUserData() : UserRecord?

    suspend fun clearUserData()

    suspend fun deleteUser(user: UserRecord)

    suspend fun updateUser(
        userId: Long,
        newUsername: String,
        newEmail: String,
        newRole: Int
    )

    suspend fun getPhotosList(page: Int, limit: Int): List<Photos>

}