package com.syahrul.danamonfrontendtest.dagger

import com.syahrul.danamonfrontendtest.MainApp
import com.syahrul.danamonfrontendtest.activity.BaseActivity
import com.syahrul.danamonfrontendtest.fragment.BaseFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ViewModelFactoryModule::class,
    ViewModelModule::class,
    DataModule::class,
    SharedPreferencesModule::class,
    NetworkModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun mainApp(application: MainApp): Builder
    }

    fun inject(mainApp: MainApp)

    fun inject(baseFragment: BaseFragment)

    fun inject(baseActivity: BaseActivity)

}