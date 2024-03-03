package com.syahrul.danamonfrontendtest.repository

import com.syahrul.danamonfrontendtest.dagger.DataModule
import com.syahrul.danamonfrontendtest.retrofit.PhotosApi
import com.syahrul.danamonfrontendtest.room.AppDatabase
import com.syahrul.danamonfrontendtest.room.UserRecord
import com.syahrul.danamonfrontendtest.sharedpref.UserManager
import com.syahrul.danamonfrontendtest.utils.Utility.generateUserId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    @Named(DataModule.DEP_NETWORK_DISPATCHER) private val ioDispatcher: CoroutineDispatcher,
    appDatabase: AppDatabase,
    private val userManager: UserManager,
    private val photosApi: PhotosApi,
): BaseUserRepository {

    private val userDao = appDatabase.userDao()

    override suspend fun getAllUserData() =
        withContext(ioDispatcher) {
            userDao.getAllUserData()
        }

    override suspend fun getUserByEmails(email: String) =
        withContext(ioDispatcher) {
            userDao.getUserDataByEmails(email)
        }

    override suspend fun getUserById(id: Long) =
        withContext(ioDispatcher) {
            userDao.getUserDataById(id)
        }

    override suspend fun registerNewUser(
        userName: String,
        email: String,
        password: String,
        role: Int
    ) =
        withContext(ioDispatcher) {
            val user = UserRecord(
                id = generateUserId(),
                userName = userName,
                email = email,
                password = password,
                role = role
            )
            userDao.insertUser(user)
        }

    override suspend fun storeUser(user: UserRecord) =
        withContext(ioDispatcher) {
            userManager.storeUser(user)
        }

    override suspend fun getUserData() =
        withContext(ioDispatcher) {
            userManager.getUserData()
        }

    override suspend fun clearUserData() =
        withContext(ioDispatcher) {
            userManager.clearUserData()
        }

    override suspend fun deleteUser(user: UserRecord) =
        withContext(ioDispatcher) {
            userDao.deleteOneUser(user.id)
        }

    override suspend fun updateUser(
        userId: Long,
        newUsername: String,
        newEmail: String,
        newRole: Int
    ) {
        withContext(ioDispatcher) {
            userDao.updateUser(userId, newUsername, newEmail, newRole)
        }
    }

    override suspend fun getPhotosList(page: Int, limit: Int) =
        withContext(ioDispatcher) {
            photosApi.getPhotosList(page, limit)
        }
}