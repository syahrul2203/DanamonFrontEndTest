package com.syahrul.danamonfrontendtest

import android.os.Bundle
import android.view.LayoutInflater
import com.syahrul.danamonfrontendtest.activity.BaseViewBindingActivity
import com.syahrul.danamonfrontendtest.databinding.ActivityMainBinding

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}