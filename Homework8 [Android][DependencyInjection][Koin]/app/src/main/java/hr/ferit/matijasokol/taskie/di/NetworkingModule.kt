package hr.ferit.matijasokol.taskie.di

import hr.ferit.matijasokol.taskie.BuildConfig
import hr.ferit.matijasokol.taskie.networking.TaskieApiService
import hr.ferit.matijasokol.taskie.prefs.SharedPrefsHelperImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://authenticationexample.herokuapp.com/"
const val KEY_AUTHORIZATION = "authorization"
const val LOGGING_INTERCEPTOR = "logging"
const val AUTH_INTERCEPTOR = "auth"

val networkingModule = module {

    //GSON
    single { GsonConverterFactory.create() as Converter.Factory }

    //INTERCEPTORS
    single(named(LOGGING_INTERCEPTOR)){
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    factory(named("sharedPrefsToken")) { SharedPrefsHelperImpl().getUserToken() }

    single(named(AUTH_INTERCEPTOR)){
        Interceptor{
            val authentication = it.request().newBuilder().addHeader(KEY_AUTHORIZATION, get(named("sharedPrefsToken"))).build()
            it.proceed(authentication)
        }
    }

    //OKHTTPCLIENT
    single{
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) addInterceptor(get(named(LOGGING_INTERCEPTOR)))
            addInterceptor(get(named(AUTH_INTERCEPTOR)))
            readTimeout(1L, TimeUnit.MINUTES)
            connectTimeout(1L, TimeUnit.MINUTES)
        }.build()
    }

    //RETROFIT
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(get())
            .build()
    }

    single { get<Retrofit>().create(TaskieApiService::class.java) }
}