package com.example.VUCourseApp.data.model

data class CourseResponse(
    val courses: List<Course>
)
data class Course(
    val courseTitle: String,
    val courseDescription: String
)
