package com.example.VUCourseApp.data.model

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CourseAPI {
    @GET("api/courses")
    suspend fun getCourses(): CourseResponse

    @POST("api/login")
    suspend fun login(@Query("username") username: String, @Query("password") password: String): LoginResponse
}