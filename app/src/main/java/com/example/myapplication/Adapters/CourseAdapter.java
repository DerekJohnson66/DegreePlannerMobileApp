package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Course;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private List<Course> courses = new ArrayList<>();
    private CourseAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public CourseAdapter.CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View courseView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new CourseHolder(courseView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseHolder holder, int position) {
        Course currentCourse = courses.get(position);
        holder.textViewTitle.setText(currentCourse.getTitle());
        holder.textViewStartDate.setText(String.valueOf(currentCourse.getStartDateMonth() + "/" + currentCourse.getStartDateDay()
                + "/" + currentCourse.getStartDateYear()));

        holder.textViewEndDate.setText(String.valueOf(currentCourse.getEndDateMonth() + "/" + currentCourse.getEndDateDay()
                + "/"  + currentCourse.getEndDateYear()));

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<Course> courses){
        this.courses = courses;
        notifyDataSetChanged();
    }

    public Course getCourseAt(int position){
        return courses.get(position);
    }

    class CourseHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewStartDate;
        private TextView textViewEndDate;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_course_title);
            textViewStartDate = itemView.findViewById(R.id.text_view_course_start_date);
            textViewEndDate = itemView.findViewById(R.id.text_view_course_end_date);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
//                    listener.onItemClick(courses.get(position));
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(courses.get(position));
                    }
                }

            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Course course);
    }

    public void setOnItemClickListener(CourseAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
