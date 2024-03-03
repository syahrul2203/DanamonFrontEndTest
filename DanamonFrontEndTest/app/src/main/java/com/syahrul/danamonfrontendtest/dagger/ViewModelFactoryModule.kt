package com.syahrul.danamonfrontendtest.dagger

import androidx.lifecycle.ViewModelProvider
import com.syahrul.danamonfrontendtest.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}