package com.example.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Course;
import com.example.myapplication.Term;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table WHERE termId=:termId ORDER BY startDateYear DESC")
    LiveData<List<Course>> getCourseListByTerm(int termId);

    @Query("SELECT COUNT(*) FROM course_table WHERE termId=:termId")
    int getCourseCountByTerm(int termId);


}
