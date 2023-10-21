package com.example.VUCourseApp.ui.courses

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.VUCourseApp.data.model.CourseAPI
import com.example.shitapp.R
import com.example.shitapp.databinding.FragmentCoursesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CoursesFragment : Fragment() {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://5ceb-106-71-104-5.ngrok-free.app") // Add the correct base url
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val courseApi: CourseAPI by lazy {
        retrofit.create(CourseAPI::class.java)
    }

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = root.findViewById<RecyclerView>(R.id.coursesRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        val adapter = CourseAdapter(emptyList())
        recyclerView.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val courses = courseApi.getCourses().courses
               adapter.addData(courses)
                Log.e("CoursesFragment", "got courses ${courses}")
            } catch (e: Exception) {
                Log.e("CoursesFragment", "Error fetching courses: ${e.message}")
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}