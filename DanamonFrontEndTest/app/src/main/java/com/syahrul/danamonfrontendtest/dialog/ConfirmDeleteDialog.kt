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
import com.syahrul.danamonfrontendtest.databinding.DialogConfirmDeleteBinding
import com.syahrul.danamonfrontendtest.room.UserRecord
import com.syahrul.danamonfrontendtest.utils.Utility.decodePassword
import com.syahrul.danamonfrontendtest.utils.Utility.isPasswordValid

class ConfirmDeleteDialog(
    private val currentUser: UserRecord,
    private val deletedUser: UserRecord,
    private val dialogListener: ConfirmDeleteDialogListener
) : DialogFragment() {

    private lateinit var binding: DialogConfirmDeleteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding = DialogConfirmDeleteBinding.inflate(
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

        binding.tvDialogTitle.text = "Delete user with id : ${deletedUser.id}"

        binding.btnDeleteUser.setOnClickListener {
            val password = binding.etPassword.text.toString()
            if(password.isEmpty() || !isPasswordValid(password)){
                showToastMessage("password was invalid, please input minimum 8 character !")
            } else {
                val currentPassword = currentUser.password?.decodePassword()
                if(currentPassword == password){
                    dialogListener.onDeleteClicked(deletedUser)
                    dismiss()
                } else {
                    showToastMessage("Wrong password, Please try again !")
                }
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun showToastMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    interface ConfirmDeleteDialogListener {
        fun onDeleteClicked(deletedUser: UserRecord)
    }


}