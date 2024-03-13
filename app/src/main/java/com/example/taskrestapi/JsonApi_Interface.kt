package com.example.taskrestapi

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JsonApi_Interface {

       @GET("posts")
       suspend fun getPosts(): Response<List<JsonApiResponse_DataClass>>

       @POST("posts")
       suspend fun postDataToServer(@Body jsonAPiResponse: JsonApiResponse_DataClass): Response<JsonApiResponse_DataClass>

       @PUT("posts/{id}")
       suspend fun putPostRequest(@Path("id") id: Int, @Body jsonAPiResponse: JsonApiResponse_DataClass): Response<JsonApiResponse_DataClass>

       @PATCH("posts/{id}")
       suspend fun patchPostRequest(@Path("id") id: Int, @Body jsonAPiResponse: JsonApiResponse_DataClass): Response<JsonApiResponse_DataClass>

       @DELETE("posts/{id}")
       suspend fun deletePostRequest(@Path("id")id:Int):Response<Unit>


       ///////////////////////////



}