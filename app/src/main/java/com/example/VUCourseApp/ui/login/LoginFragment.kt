package com.example.VUCourseApp.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.VUCourseApp.data.model.CourseAPI
import com.example.VUCourseApp.data.model.LoginResponse
import com.example.shitapp.R
import com.example.shitapp.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class LoginFragment : Fragment() {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://0e0a-106-71-104-5.ngrok-free.app") // Add the correct base url
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private var userName = ""
    private var password = ""

    private val courseApi: CourseAPI by lazy {
        retrofit.create(CourseAPI::class.java)
    }

    private val loginResponseLiveData = MutableLiveData<LoginResponse?>(null)

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loginResponseLiveData.observe(viewLifecycleOwner) {
            it?.let { response ->
                Toast.makeText(this.context, "${response.message}", Toast.LENGTH_SHORT).show()
            }
        }

        root.findViewById<TextInputEditText>(R.id.TextUsername).addTextChangedListener {
            userName = it.toString()
        }

        root.findViewById<TextInputEditText>(R.id.TextPassword).addTextChangedListener {
            password = it.toString()
        }

        root.findViewById<Button>(R.id.loginButton).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val response = courseApi.login(username = userName, password = password)
                    if (response.message == "Successful") {
                        loginResponseLiveData.value = response
                        root.findNavController().navigate(R.id.navigation_home)
                    } else {
                        loginResponseLiveData.value = LoginResponse(message = "Login failed. Please check your credentials.")
                    }
                } catch (e: Exception) {
                    loginResponseLiveData.value = LoginResponse(message = "Network call failed $e")
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