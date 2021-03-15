package com.example.myapplication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.Serializable

class Joke:Serializable {
    @SerializedName("id")
    @Expose
    var id:Int? = null

    @SerializedName("setup")
    @Expose
    var setup:String? = null

    @SerializedName("punchline")
    @Expose
    var punchline:String? = null
}

interface RestJoke{
    @GET("jokes/{type}/ten")
    fun getJokeByType(@Path("type") type:String) : Call<List<Joke>>
}