package com.example.google

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.google.Model.PlaceDetail
import com.example.google.remote.IGoogleAPIService
import com.google.android.gms.common.internal.service.Common
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_place.*
import retrofit2.Call
import retrofit2.Response
import java.lang.StringBuilder

class ViewPlace : AppCompatActivity() {
    internal lateinit var mService:IGoogleAPIService
    var mPlace:PlaceDetail?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_place)

        mService = com.example.google.Common.Common.googleApiService

        place_name.text = ""
        place_address.text = ""
        place_open_hour.text = ""
        btn_show_map.setOnClickListener{
            val mapIntent = Intent(Intent.ACTION_VIEW,Uri.parse(mPlace!!.result!!.url))
            startActivity(mapIntent)
        }
        if(com.example.google.Common.Common.currentResult!!.photos !=null && com.example.google.Common.Common.currentResult!!.photos!!.size > 0){
            Picasso.with(this@ViewPlace)
                .load(getPhotoOfPlace(com.example.google.Common.Common.currentResult!!.photos!![0].photo_reference!!,1000))
                .into(photo)
        }
        if(com.example.google.Common.Common.currentResult!!.rating !=null){
            rating_bar.rating = com.example.google.Common.Common.currentResult!!.rating.toFloat()
        }
        else{
            rating_bar.visibility = View.GONE
        }
        if(com.example.google.Common.Common.currentResult!!.opening_hours !=null){
            place_open_hour.text = "Open now : " + com.example.google.Common.Common.currentResult!!.opening_hours!!.open_now
        }
        else{
            place_open_hour.visibility = View.GONE
        }
        mService.getDetailPlace(getPlaceDetailUrl(com.example.google.Common.Common.currentResult!!.place_id))
            .enqueue(object : retrofit2.Callback<PlaceDetail>{
                override fun onFailure(call: Call<PlaceDetail>, t: Throwable) {
                    Toast.makeText(baseContext ,""+ t!!.message,Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<PlaceDetail>, response: Response<PlaceDetail>) {
                   mPlace = response!!.body()
                    place_address.text = mPlace!!.result!!.formatted_address
                    place_name.text = mPlace!!.result!!.name
                }

            })
    }

    private fun getPlaceDetailUrl(placeId: String?): String {
        val url = StringBuilder("https://maps.googleapis.com/maps/api/place/details/json")
        url.append("?place_id=$placeId")
        url.append("&key=AIzaSyCs90L0_FyaqStFp4vSmLpHXaJoYBcyBKc")
        return url.toString()
    }

    private fun getPhotoOfPlace(photoReference: String, maxwidth: Int): String {
        val url = StringBuilder("https://maps.googleapis.com/maps/api/place/photo")
        url.append("?maxwidth=$maxwidth")
        url.append("&photoreference=$photoReference")
        url.append("&key=AIzaSyCs90L0_FyaqStFp4vSmLpHXaJoYBcyBKc")
        return url.toString()
    }
}