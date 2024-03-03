package com.syahrul.danamonfrontendtest.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserRecord): Long

    @Query("SELECT * FROM user WHERE email=:email")
    fun getUserDataByEmails(email: String): List<UserRecord>

    @Query("SELECT * FROM user WHERE id=:id")
    fun getUserDataById(id: Long): List<UserRecord>

    @Query("SELECT * FROM user")
    fun getAllUserData(): List<UserRecord>

    @Query("DELETE FROM user WHERE id=:userId")
    fun deleteOneUser(userId: Long)

    @Query("UPDATE user SET userName=:newUsername, email=:newEmail, role=:newRole WHERE id=:userId")
    fun updateUser(userId: Long, newUsername: String, newEmail: String, newRole: Int)

}