package com.syahrul.danamonfrontendtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.syahrul.danamonfrontendtest.adapter.AdminUserListAdapter
import com.syahrul.danamonfrontendtest.databinding.FragmentAdminBinding
import com.syahrul.danamonfrontendtest.dialog.ConfirmDeleteDialog
import com.syahrul.danamonfrontendtest.dialog.UpdateDialog
import com.syahrul.danamonfrontendtest.model.Result
import com.syahrul.danamonfrontendtest.room.UserRecord
import com.syahrul.danamonfrontendtest.viewmodel.UserViewModel

class AdminFragment : BaseViewBindingFragment<FragmentAdminBinding>() {

    companion object {
        fun instantiate() = AdminFragment()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAdminBinding
        get() = FragmentAdminBinding::inflate

    private lateinit var adminUserListAdapter: AdminUserListAdapter
    private lateinit var userViewModel: UserViewModel

    private lateinit var currentUser: UserRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = getFragmentViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.getLoginSessionResult().observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Success -> {
                    currentUser = it.data

                    userViewModel.getUserAdminList()
                }
                is Result.Error -> { }
                else -> { }
            }
        })

        userViewModel.getUserAdminListResult().observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Success -> {
                    val userList = ArrayList(it.data)

                    adminUserListAdapter = AdminUserListAdapter(currentUser, userList, adapterListener)

                    binding.rcvAdminList.layoutManager = LinearLayoutManager(requireContext())
                    binding.rcvAdminList.adapter = adminUserListAdapter
                }
                is Result.Error -> { }
                else -> { }
            }
        })

        userViewModel.getDeleteUserResult().observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Success -> {
                    if(it.data){
                        refreshData()
                        showToastMessage("Delete Successful !")
                    }
                }
                is Result.Error -> { showToastMessage("${it.exception.message}") }
                else -> { }
            }
        })

        userViewModel.getUpdateUserResult().observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Success -> {
                    if(it.data){
                        refreshData()
                        showToastMessage("Update Successful !")
                    }
                }
                is Result.Error -> { showToastMessage("${it.exception.message}") }
                else -> { }
            }
        })


        refreshData()
    }

    private fun refreshData(){
        userViewModel.getLoginSession()
    }

    private val adapterListener = object : AdminUserListAdapter.AdminUserListAdapterListener {
        override fun onDelete(user: UserRecord) {
            ConfirmDeleteDialog(currentUser, user,
                object : ConfirmDeleteDialog.ConfirmDeleteDialogListener {
                    override fun onDeleteClicked(deletedUser: UserRecord) {
                        userViewModel.deleteUser(deletedUser)
                    }
                }
            ).show(
                childFragmentManager,
                "ConfirmDeleteDialog"
            )
        }

        override fun onEdit(user: UserRecord) {
            UpdateDialog(user,
                object : UpdateDialog.UpdateDialogListener {
                    override fun onUpdateClicked(
                        userId: Long,
                        newUsername: String,
                        newEmail: String,
                        newRole: Int,
                        oldEmail: String
                    ) {
                        userViewModel.updateUser(userId, newUsername, newEmail, newRole, oldEmail)
                    }
                }
            ).show(
                childFragmentManager,
                "UpdateDialog"
            )
        }
    }

}