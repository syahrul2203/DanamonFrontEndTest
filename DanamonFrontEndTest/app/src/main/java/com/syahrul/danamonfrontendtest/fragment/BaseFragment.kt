package com.syahrul.danamonfrontendtest.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.syahrul.danamonfrontendtest.utils.appComponent
import com.syahrul.danamonfrontendtest.viewmodel.ViewModelFactory
import javax.inject.Inject

abstract class BaseFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    inline fun <reified VM : ViewModel> getFragmentViewModel() =
        ViewModelProvider(this, viewModelFactory)[VM::class.java]

    inline fun <reified VM : ViewModel> getActivityViewModel()
            = ViewModelProvider(requireActivity(), viewModelFactory)[VM::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    fun showToastMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}