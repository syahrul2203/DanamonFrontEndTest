package com.syahrul.danamonfrontendtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.syahrul.danamonfrontendtest.activity.LoginActivity
import com.syahrul.danamonfrontendtest.databinding.FragmentProfileBinding
import com.syahrul.danamonfrontendtest.model.Result
import com.syahrul.danamonfrontendtest.viewmodel.UserViewModel

class ProfileFragment : BaseViewBindingFragment<FragmentProfileBinding>(){

    companion object {
        fun instantiate() = ProfileFragment()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = getFragmentViewModel()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.getLoginSessionResult().observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Success -> {
                    val currentUser = it.data

                    val username = currentUser.userName
                    val email = currentUser.email
                    val role = if(currentUser.role == 1) "Admin" else "User"

                    binding.tvUsername.text = username
                    binding.tvEmail.text = email
                    binding.tvRole.text = role

                }
                is Result.Error -> { }
                else -> { }
            }
        })

        binding.btnSignOut.setOnClickListener {
            userViewModel.logout()
            showToastMessage("Sign Out Successful !")
            startActivity(LoginActivity.launchIntent(requireContext()))
            requireActivity().finish()
        }
    }

    override fun onResume() {
        super.onResume()

        userViewModel.getLoginSession()
    }

}