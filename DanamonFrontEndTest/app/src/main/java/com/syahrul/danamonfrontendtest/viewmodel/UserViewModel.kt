package com.syahrul.danamonfrontendtest.viewmodel

import androidx.lifecycle.MutableLiveData
import com.syahrul.danamonfrontendtest.model.Photos
import com.syahrul.danamonfrontendtest.model.Result
import com.syahrul.danamonfrontendtest.room.UserRecord
import com.syahrul.danamonfrontendtest.utils.asImmutable
import com.syahrul.danamonfrontendtest.repository.UserRepository
import com.syahrul.danamonfrontendtest.utils.Utility.decodePassword
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val loginResult = MutableLiveData<Result<Boolean>>()
    fun getLoginResult() = loginResult.asImmutable()

    private val registerUserResult = MutableLiveData<Result<Boolean>>()
    fun getRegisterUserResult() = registerUserResult.asImmutable()

    private val loginSessionResult = MutableLiveData<Result<UserRecord>>()
    fun getLoginSessionResult() = loginSessionResult.asImmutable()

    private val userAdminList = MutableLiveData<Result<List<UserRecord>>>()
    fun getUserAdminListResult() = userAdminList.asImmutable()

    private val deleteUserResult = MutableLiveData<Result<Boolean>>()
    fun getDeleteUserResult() = deleteUserResult.asImmutable()

    private val updateUserResult = MutableLiveData<Result<Boolean>>()
    fun getUpdateUserResult() = updateUserResult.asImmutable()

    private val photoListResult = MutableLiveData<Result<List<Photos>>>()
    fun getPhotoListResult() = photoListResult.asImmutable()

    fun registerNewUser(
        userName: String,
        email: String,
        password: String,
        role: Int
    ) {
        launch {
            val checkEmailResult = userRepository.getUserByEmails(email)
            if(checkEmailResult.isNotEmpty()){
                registerUserResult.postValue(Result.Error(Throwable("Email already taken !")))
                return@launch
            }

            userRepository.registerNewUser(userName, email, password, role)

            val checkRegisterResult = userRepository.getUserByEmails(email)
            if(checkRegisterResult.isEmpty()){
                registerUserResult.postValue(Result.Error(Throwable("Register Failed !")))
            } else {
                registerUserResult.postValue(Result.Success(true))
            }
        }
    }

    fun login(email: String, password: String) {
        launch {
            val result = userRepository.getUserByEmails(email)

            if(result.isEmpty()){
                loginResult.postValue(Result.Error(Throwable("Email Not Found !")))
            } else {
                val userRecord = result[0]
                val passwordFromDatabase = userRecord.password?.decodePassword()
                if(passwordFromDatabase == password){
                    userRepository.storeUser(userRecord)
                    loginResult.postValue(Result.Success(true))
                } else {
                    loginResult.postValue(Result.Error(Throwable("Incorrect Password !")))
                }
            }
        }
    }

    fun getLoginSession(){
        launch {
            val currentUser = userRepository.getUserData()
            if(currentUser == null){
                loginSessionResult.postValue(Result.Error(Throwable("Failed")))
            } else {
                loginSessionResult.postValue(Result.Success(currentUser))
            }
        }
    }

    fun logout(){
        launch {
            userRepository.clearUserData()
        }
    }

    fun getUserAdminList(){
        launch {
            val result = userRepository.getAllUserData()
            userAdminList.postValue(Result.Success(result))
        }
    }

    fun deleteUser(userRecord: UserRecord){
        launch {
            userRepository.deleteUser(userRecord)

            val checkDeleteResult = userRepository.getUserById(userRecord.id)
            if(checkDeleteResult.isEmpty()){
                deleteUserResult.postValue(Result.Success(true))
            } else {
                deleteUserResult.postValue(Result.Error(Throwable("Deleted user failed !")))
            }
        }
    }

    fun updateUser(userId: Long, newUsername: String, newEmail: String, newRole: Int, oldEmail: String){
        launch {
            if(newEmail != oldEmail){
                val resultCheckEmail = userRepository.getUserByEmails(newEmail)
                if(resultCheckEmail.isNotEmpty()){
                    updateUserResult.postValue(Result.Error(Throwable("Email email taken !")))
                    return@launch
                }
            }

            userRepository.updateUser(userId, newUsername, newEmail, newRole)

            val checkUpdateResult = userRepository.getUserById(userId)
            if(checkUpdateResult.isEmpty()){
                updateUserResult.postValue(Result.Error(Throwable("Update user failed !")))
            } else {
                updateUserResult.postValue(Result.Success(true))
            }
        }
    }

    fun getPhotoList(page: Int, limit: Int){
        launch {
            photoListResult.postValue(Result.Loading())
            delay(1000)

            try {
                val result = userRepository.getPhotosList(page, limit)
                photoListResult.postValue(Result.Success(result))
            } catch (e: Exception){
                e.printStackTrace()
                photoListResult.postValue(Result.Error(Throwable("$e")))
            }

        }
    }
}