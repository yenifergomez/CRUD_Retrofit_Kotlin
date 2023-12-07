package com.xcheko51x.crud_retrofit_kotlin

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

import retrofit2.http.*

object AppConstantes{
    const val BASE_URL = "http://192.168.1.14:8000"
}

interface WebService {



    interface WebService {

        @GET("/api/comments")
        suspend fun obtenerComments(): Response<List<Comment>>

        @GET("/api/comments/{id}")
        suspend fun obtenerComment(@Path("id") id: Int): Response<Comment>

        @POST("/api/comments")
        suspend fun agregarComment(@Body comment: Comment): Response<Comment>

        @PUT("/api/comments/{id}")
        suspend fun actualizarComment(@Path("id") id: Int, @Body comment: Comment): Response<Comment>

        @DELETE("/api/comments/{id}")
        suspend fun borrarComment(@Path("id") id: Int): Response<Void>
    }

    object RetrofitClient {
        val webService: WebService by lazy {
            Retrofit.Builder()
                .baseUrl(AppConstantes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(WebService::class.java)
        }


    }

}


