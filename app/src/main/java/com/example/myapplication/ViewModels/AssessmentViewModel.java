package com.example.myapplication.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.Assessment;
import com.example.myapplication.Repositories.AssessmentRepository;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    private AssessmentRepository repository;
    private LiveData<List<Assessment>> allAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repository = new AssessmentRepository(application);
    }

    public void insert (Assessment assessment){
        repository.insert(assessment);
    }

    public void update(Assessment assessment){
        repository.update(assessment);
    }

    public void delete(Assessment assessment){
        repository.delete(assessment);
    }

    public void deleteAllAssessments(){
        repository.deleteAllAssessments();
    }

    public LiveData<List<Assessment>> getAssessmentListByCourse(int courseId){
        allAssessments = repository.getAssessmentListByCourse(courseId);
        return allAssessments;
    }
}
