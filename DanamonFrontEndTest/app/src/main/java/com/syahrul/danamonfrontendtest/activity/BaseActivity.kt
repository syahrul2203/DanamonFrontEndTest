package com.syahrul.danamonfrontendtest.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.syahrul.danamonfrontendtest.viewmodel.ViewModelFactory
import com.syahrul.danamonfrontendtest.utils.appComponent
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    inline fun <reified VM : ViewModel> getViewModel()
            = ViewModelProvider(this, viewModelFactory).get(VM::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

    }

    fun showToastMessage(message: String){
        Toast.makeText(this@BaseActivity, message, Toast.LENGTH_SHORT).show()
    }


}