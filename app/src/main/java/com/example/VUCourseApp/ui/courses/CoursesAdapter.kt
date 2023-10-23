package com.example.VUCourseApp.ui.courses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.VUCourseApp.data.model.Course
import com.example.shitapp.R
import java.util.Locale

class CourseAdapter(private var data: List<Course>) :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>(), Filterable {

    var dataFilterList: List<Course> = ArrayList()
    class CourseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(info: Course) {

            //these are the recyclerview xml ID's
            val title = view.findViewById<TextView>(R.id.coursesTitle)
            val description = view.findViewById<TextView>(R.id.coursesDescription)
//
//            //binding courseTitle api to the text in title & description
            title.text = info.courseTitle
            description.text = info.courseDescription
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {

        //this is finding which fragment to use as the display
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.courses_recycler, parent, false)
        return CourseViewHolder(view)
    }

    //this just gets the amount of objects from the course list
    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {

        //this binds the data to the positions designated earlier
        holder.bind(dataFilterList[position])
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data
                } else {
                    val resultList = ArrayList<Course>()
                    for (row in data) {
                        if (row.courseTitle.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    dataFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilterList = results?.values as ArrayList<Course>
                notifyDataSetChanged()
            }
        }
    }

    fun addData(newData: List<Course>) {
        data = newData as ArrayList<Course>
        dataFilterList = data
        notifyDataSetChanged()
    }
}