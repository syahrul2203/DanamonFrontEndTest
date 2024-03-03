package com.syahrul.danamonfrontendtest.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.syahrul.danamonfrontendtest.R

abstract class BaseViewBindingActivity<VB: ViewBinding>: BaseActivity() {

    private lateinit var _binding: VB
    val binding get() = _binding

    abstract val bindingInflater: (LayoutInflater) -> VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(_binding.root)
    }

}