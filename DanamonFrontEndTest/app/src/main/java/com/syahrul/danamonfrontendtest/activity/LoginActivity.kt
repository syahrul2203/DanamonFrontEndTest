package com.syahrul.danamonfrontendtest.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.syahrul.danamonfrontendtest.databinding.ActivityLoginBinding
import com.syahrul.danamonfrontendtest.viewmodel.UserViewModel
import com.syahrul.danamonfrontendtest.model.Result
import com.syahrul.danamonfrontendtest.utils.Utility

class LoginActivity : BaseViewBindingActivity<ActivityLoginBinding>() {

    companion object {
        fun launchIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate


    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = getViewModel()
        userViewModel.getLoginSessionResult().observe(this, Observer {
            when(it){
                is Result.Success -> {
                    startActivity(HomeActivity.launchIntent(this@LoginActivity))
                    finish()
                }
                is Result.Error -> { }
                else -> { }
            }
        })

        userViewModel.getLoginResult().observe(this, Observer {
            when(it){
                is Result.Success -> {
                    startActivity(HomeActivity.launchIntent(this@LoginActivity))
                    showToastMessage("Login Successful")
                    finish()
                }
                is Result.Error -> {
                    showToastMessage(it.exception.message ?: "Unknown Error")
                }
                else -> { }
            }
        })

        binding.btnSignIn.setOnClickListener {
            login()
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(RegisterActivity.launchIntent(this@LoginActivity))
        }

    }

    private fun login(){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if(email.isEmpty() || !Utility.isValidEmail(email)){
            showToastMessage("email is invalid !")
            return
        }

        if(password.isEmpty() || !Utility.isPasswordValid(password)){
            showToastMessage("password was invalid, please input minimum 8 character !")
            return
        }

        userViewModel.login(email, password)
    }

    override fun onResume() {
        super.onResume()
        userViewModel.getLoginSession()
    }

}