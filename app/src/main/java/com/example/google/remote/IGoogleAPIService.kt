package com.example.google.remote

import com.example.google.Model.Myplaces
import com.example.google.Model.PlaceDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import java.net.URL

interface IGoogleAPIService {
    @GET
    fun getNearbyPlaces(@Url url : String) :Call<Myplaces>
    @GET
    fun getDetailPlace(@Url url : String) : Call<PlaceDetail>
    @GET("maps/api/directions/json")
    fun getDirections(@Query("origin") origin:String,@Query("destination") destination:String ) : Call<String>
}