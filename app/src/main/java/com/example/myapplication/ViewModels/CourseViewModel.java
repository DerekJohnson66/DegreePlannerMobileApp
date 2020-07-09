package com.example.myapplication.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.Course;
import com.example.myapplication.Repositories.CourseRepository;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepository repository;
    private LiveData<List<Course>> allCourses;
    private int count;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseRepository(application);
    }

    public void insert (Course course){
        repository.insert(course);
    }

    public void update(Course course){
        repository.update(course);
    }

    public void delete(Course course){
        repository.delete(course);
    }

    public void deleteAllCourses(){
        repository.deleteAllCourses();
    }

    public LiveData<List<Course>> getCourseListByTerm(int termId){
        allCourses = repository.getCourseListByTerm(termId);
        return allCourses;
    }

    public int getCourseCountByTerm(int termId){
        count = repository.getCourseCountByTerm(termId);
        return count;
    }
}
