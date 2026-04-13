package com.joaomarcos.lifemanager.data

import com.joaomarcos.lifemanager.model.Users
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("appUsers/api/v1")
    suspend fun findAll(): Response<List<Users>>

    @GET("appUsers/api/v1/{uid}")
    suspend fun findById(@Path("uid") uid: String): Response<Users>

    @POST("appUsers/api/v1")
    suspend fun insert(@Body users: Users): Response<Users>

    @PUT("appUsers/api/v1/{uid}")
    suspend fun update(@Path("uid") uid: String, @Body users: Users): Response<Users>

    @DELETE("appUsers/api/v1/{uid}")
    suspend fun delete(@Path("uid") uid: String): Response<Unit>
}