package com.example.google.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitScalarsClient {
    private var retrofit: Retrofit? = null
    fun getClient(baseUrll: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrll)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

        }
        return retrofit!!
    }
}