package hr.ferit.matijasokol.taskie.networking

import hr.ferit.matijasokol.taskie.common.BASE_URL
import hr.ferit.matijasokol.taskie.common.KEY_AUTHORIZATION
import hr.ferit.matijasokol.taskie.networking.interactors.TaskieInteractor
import hr.ferit.matijasokol.taskie.networking.interactors.TaskieInteractorImpl
import hr.ferit.matijasokol.taskie.prefs.SharedPrefsHelper
import hr.ferit.matijasokol.taskie.prefs.provideSharedPrefs
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendFactory {

    private var retrofit: Retrofit? = null
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val prefs = provideSharedPrefs()

    private fun provideAuthenticationInterceptor(preferences: SharedPrefsHelper) = Interceptor {
        val authentication = it.request().newBuilder()
            .addHeader(KEY_AUTHORIZATION, preferences.getUserToken())
            .build()
        it.proceed(authentication)
    }

    private val httpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(provideAuthenticationInterceptor(prefs))
            .build()

    private val client: Retrofit? = if (retrofit == null) createRetrofit() else retrofit

    private fun createRetrofit(): Retrofit? {
        retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    private fun getService(): TaskieApiService = client!!.create(
        TaskieApiService::class.java)

    fun getTaskieInteractor(): TaskieInteractor = TaskieInteractorImpl(getService())
}