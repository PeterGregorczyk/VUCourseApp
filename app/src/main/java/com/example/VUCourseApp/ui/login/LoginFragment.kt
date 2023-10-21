package com.example.VUCourseApp.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.VUCourseApp.data.model.CourseAPI
import com.example.shitapp.R
import com.example.shitapp.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class LoginFragment : Fragment() {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://5ceb-106-71-104-5.ngrok-free.app") // Add the correct base url
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val courseApi: CourseAPI by lazy {
        retrofit.create(CourseAPI::class.java)
    }

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val username = root.findViewById<TextInputEditText>(R.id.TextUsername)
        val password = root.findViewById<TextInputEditText>(R.id.TextPassword)

        root.findViewById<Button>(R.id.loginButton).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val login = courseApi.login(username = username.text.toString(), password = password.text.toString())
                    if (login.message == "Failed") {
                        Toast.makeText(context, "this has failed", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        root.findNavController().navigate(R.id.navigation_dashboard)
                    }
                } catch (e: Exception) {
                    Log.e("CoursesFragment", "Error fetching courses: ${e.message}")
                }

            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}