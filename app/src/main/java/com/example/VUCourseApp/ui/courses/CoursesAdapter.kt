package com.example.VUCourseApp.ui.courses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.VUCourseApp.data.model.Course
import com.example.shitapp.R

class CourseAdapter( private var data: List<Course>) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    class CourseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(info: Course) {

            //these are the recyclerview xml ID's
//            val title = view.findViewById<TextView>(R.id.courseTitle)
//            val description = view.findViewById<TextView>(R.id.courseDescription)
//
//            //binding courseTitle api to the text in title & description
//            title.text = info.courseTitle
//            description.text = info.courseDescription
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {

        //this is finding which fragment to use as the display
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_courses, parent, false)
        return CourseViewHolder(view)
    }

    //this just gets the amount of objects from the course list
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {

        //this binds the data to the positions designated earlier
        holder.bind(data[position])
    }

    fun addData(newData: List<Course>) {
        data = newData as ArrayList<Course>
        notifyDataSetChanged()
    }
}