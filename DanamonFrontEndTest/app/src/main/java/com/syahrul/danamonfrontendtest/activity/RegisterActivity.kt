package com.syahrul.danamonfrontendtest.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.syahrul.danamonfrontendtest.databinding.ActivityRegisterBinding
import com.syahrul.danamonfrontendtest.model.Result
import com.syahrul.danamonfrontendtest.utils.Utility.encodePassword
import com.syahrul.danamonfrontendtest.utils.Utility.isPasswordValid
import com.syahrul.danamonfrontendtest.utils.Utility.isValidEmail
import com.syahrul.danamonfrontendtest.viewmodel.UserViewModel

class RegisterActivity : BaseViewBindingActivity<ActivityRegisterBinding>() {

    companion object {
        fun launchIntent(context: Context) = Intent(context, RegisterActivity::class.java)
    }

    override val bindingInflater: (LayoutInflater) -> ActivityRegisterBinding
        get() = ActivityRegisterBinding::inflate

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = getViewModel()
        userViewModel.getRegisterUserResult().observe(this, Observer {
            when(it){
                is Result.Success -> {
                    showToastMessage("Register Successful !\n Please Re-Login !")
                    finish()
                }
                is Result.Error -> {
                    showToastMessage(it.exception.message ?: "Unknown Error")
                }
                else -> { }
            }
        })

        binding.btnSignUp.setOnClickListener {
            register()
        }

        binding.btnBackToLoginPage.setOnClickListener {
            finish()
        }
    }

    private fun register(){
        val userName = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val role = if(binding.checkBoxRegisterAsAdmin.isChecked){
            1
        } else {
            0
        }

        if(userName.isEmpty()){
            showToastMessage("username is empty !")
            return
        }

        if(email.isEmpty() || !isValidEmail(email)){
            showToastMessage("email is invalid !")
            return
        }

        if(password.isEmpty() || !isPasswordValid(password)){
            showToastMessage("password was invalid, please input minimum 8 character !")
            return
        }

        val encodedPassword = password.encodePassword()

        userViewModel.registerNewUser(
            userName,
            email,
            encodedPassword,
            role
        )
    }

}