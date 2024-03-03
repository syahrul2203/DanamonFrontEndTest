package com.syahrul.danamonfrontendtest.dagger

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.syahrul.danamonfrontendtest.repository.BaseUserRepository
import com.syahrul.danamonfrontendtest.repository.UserRepository
import com.syahrul.danamonfrontendtest.room.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [DataModule.Bindings::class])
class DataModule {

    companion object {
        const val DEP_NETWORK_DISPATCHER = "DEP_NETWORK_DISPATCHER"
    }

    @Provides
    fun Gson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .create()

    @Provides
    fun provideScope(exceptionHandler: CoroutineExceptionHandler): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main + exceptionHandler)

    @Provides
    fun provideExceptionHandler(): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            Log.d("errors","$exception")
        }

    @Provides
    @Named(DEP_NETWORK_DISPATCHER)
    fun ioDispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun provideAppDatabase(appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Module
    interface Bindings {

        @Binds
        fun provideUserRepository(userRepository: UserRepository): BaseUserRepository

    }
}