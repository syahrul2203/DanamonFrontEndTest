package com.syahrul.danamonfrontendtest.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.syahrul.danamonfrontendtest.R
import com.syahrul.danamonfrontendtest.databinding.DialogEditUserBinding
import com.syahrul.danamonfrontendtest.room.UserRecord
import com.syahrul.danamonfrontendtest.utils.Utility

class UpdateDialog(
    private val updatingUser: UserRecord,
    private val dialogListener: UpdateDialogListener
) : DialogFragment() {

    private lateinit var binding: DialogEditUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding = DialogEditUserBinding.inflate(
            LayoutInflater.from(context),
            container,
            false
        )
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_FRAME, R.style.BottomSheetDialog)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomDialogTheme)
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etId.setText("${updatingUser.id}")
        binding.etUsername.setText("${updatingUser.userName}")
        binding.etEmail.setText("${updatingUser.email}")
        binding.checkBoxSetAsAdmin.isChecked = updatingUser.role == 1

        binding.btnUpdateUser.setOnClickListener {
            updateUser()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun updateUser(){
        val id = binding.etId.text.toString()
        val newUserName = binding.etUsername.text.toString()
        val newEmail = binding.etEmail.text.toString()
        val newRole = if(binding.checkBoxSetAsAdmin.isChecked){
            1
        } else {
            0
        }

        if(newUserName.isEmpty()){
            showToastMessage("username is empty !")
            return
        }

        if(newEmail.isEmpty() || !Utility.isValidEmail(newEmail)){
            showToastMessage("email is invalid !")
            return
        }

        dialogListener.onUpdateClicked(
            id.toLong(),
            newUserName,
            newEmail,
            newRole,
            updatingUser.email.toString()
        )
        dismiss()
    }

    private fun showToastMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    interface UpdateDialogListener {
        fun onUpdateClicked(userId: Long, newUsername: String, newEmail: String, newRole: Int, oldEmail: String)
    }


}