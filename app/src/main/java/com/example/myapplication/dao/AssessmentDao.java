package com.example.myapplication.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert
    void insert(Assessment assessments);

    @Update
    void update(Assessment assessments);

    @Delete
    void delete(Assessment assessments);

    @Query("DELETE FROM assessments_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessments_table WHERE cId=:courseId ORDER BY dueDateYear DESC")
    LiveData<List<Assessment>> getAssessmentListByCourse(int courseId);
}
