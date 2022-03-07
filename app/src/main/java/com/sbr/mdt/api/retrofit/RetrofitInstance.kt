package com.sbr.mdt.api.retrofit

import com.sbr.mdt.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            //For printing API url and body in logcat
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
        val api by lazy {
            retrofit.create(MDTAPI::class.java)
        }
    }
    class AuthTokenInterceptor(val token: String? = null) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {

            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .header(Constants.AUTH_KEY, "$token")
            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    }
}