package com.example.google.Common

import com.example.google.Model.Results
import com.example.google.remote.IGoogleAPIService
import com.example.google.remote.RetrofitClient
import com.example.google.remote.RetrofitScalarsClient

object Common {
    private val GOOGLE_API_URL="https://maps.googleapis.com/"
    var currentResult:Results?=null
    val googleApiService:IGoogleAPIService
        get()=RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService::class.java)
    val googleApiServiceScalars:IGoogleAPIService
        get()=RetrofitScalarsClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService::class.java)
}