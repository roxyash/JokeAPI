package com.example.myapplication

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EthernetJoke {

    interface Result
    data class ResultOK(val listJokes: List<Joke>):Result
    data class ResultFAIL(val message:String):Result

    fun getJokeByType(type:String, f:(Result)->Unit){
        val retrofit = Retrofit.Builder().baseUrl("http://official-joke-api.appspot.com").addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RestJoke::class.java)
        val call = service.getJokeByType(type)

        call.enqueue(object: Callback<List<Joke>>{
            override fun onResponse(call: Call<List<Joke>>, response: Response<List<Joke>>) {
                val list = response.body()!!
                f(ResultOK(list))
            }

            override fun onFailure(call: Call<List<Joke>>, t: Throwable) {
                f(ResultFAIL(t.localizedMessage))
            }

        })
    }
}