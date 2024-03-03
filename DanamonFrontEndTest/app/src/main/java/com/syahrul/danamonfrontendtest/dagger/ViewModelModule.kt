package com.syahrul.danamonfrontendtest.dagger

import androidx.lifecycle.ViewModel
import com.syahrul.danamonfrontendtest.viewmodel.UserViewModel
import com.syahrul.danamonfrontendtest.viewmodel.helper.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    internal abstract fun userViewModel(userViewModel: UserViewModel): ViewModel

}