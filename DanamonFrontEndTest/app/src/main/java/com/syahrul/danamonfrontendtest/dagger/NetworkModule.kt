package com.syahrul.danamonfrontendtest.dagger

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.syahrul.danamonfrontendtest.retrofit.PhotosApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        const val DEP_COMMON_INTERCEPTOR = "DEP_COMMON_INTERCEPTOR"
        const val DEP_USER_API_RETROFIT = "DEP_USER_API_RETROFIT"
    }

    @Provides
    fun providesBaseRetrofitBuilder(
        gson: Gson
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(EnumConverterFactory())
    }

    @Provides
    fun okhttpBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)

        return builder
    }

    @Provides
    @Named(DEP_COMMON_INTERCEPTOR)
    fun providesCommonInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val reqBuilder = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                return chain.proceed(reqBuilder.build())
            }
        }
    }

    @Provides
    @Singleton
    @Named(DEP_USER_API_RETROFIT)
    fun providesUserApiRetrofit(
        context: Context,
        httpClientBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder,
        @Named(DEP_COMMON_INTERCEPTOR) commonInterceptor: Interceptor
    ): Retrofit {
        httpClientBuilder.apply {
            addInterceptor(commonInterceptor)
        }

        return retrofitBuilder
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(httpClientBuilder.let {
                it.addInterceptor(ChuckerInterceptor(context))
                it.build()
            })
            .build()
    }

    @Provides
    @Singleton
    fun photosApi(@Named(DEP_USER_API_RETROFIT) retrofit: Retrofit): PhotosApi =
        retrofit.create(PhotosApi::class.java)

}

class EnumConverterFactory : Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<Enum<*>, String>? =
        if (type is Class<*> && type.isEnum) {
            Converter { enum ->
                try {
                    enum.javaClass.getField(enum.name)
                        .getAnnotation(SerializedName::class.java)?.value
                } catch (exception: Exception) {
                    null
                } ?: enum.toString()
            }
        } else {
            null
        }
}