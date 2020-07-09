package com.example.myapplication.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.Assessment;
import com.example.myapplication.Databases.AppDatabase;
import com.example.myapplication.dao.AssessmentDao;

import java.util.List;

public class AssessmentRepository {

    private AssessmentDao assessmentDao;
    private LiveData<List<Assessment>> allAssessments;

    public AssessmentRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        assessmentDao = database.assessmentDao();
    }

    public void insert(Assessment assessments){new InsertAssessmentAsyncTask(assessmentDao).execute(assessments);}

    public void update(Assessment assessments){new UpdateAssessmentAsyncTask(assessmentDao).execute(assessments);}

    public void delete(Assessment assessments){new DeleteAssessmentAsyncTask(assessmentDao).execute(assessments);}

    public void deleteAllAssessments(){new DeleteAllAssessmentsAsyncTask(assessmentDao).execute();}


    public LiveData<List<Assessment>> getAssessmentListByCourse(int courseId){
        allAssessments = assessmentDao.getAssessmentListByCourse(courseId);
        return allAssessments;
    }

    private static class InsertAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {
        private AssessmentDao assessmentDao;

        private InsertAssessmentAsyncTask(AssessmentDao assessmentDao){
            this.assessmentDao = assessmentDao;
        }
        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.insert(assessments[0]);
            return null;
        }
    }

    private static class UpdateAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void>{
        private AssessmentDao assessmentDao;

        private UpdateAssessmentAsyncTask(AssessmentDao assessmentDao){
            this.assessmentDao = assessmentDao;
        }
        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.update(assessments[0]);
            return null;
        }
    }

    private static class DeleteAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void>{
        private AssessmentDao assessmentDao;

        private DeleteAssessmentAsyncTask(AssessmentDao assessmentDao){
            this.assessmentDao = assessmentDao;
        }
        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.delete(assessments[0]);
            return null;
        }
    }

    private static class DeleteAllAssessmentsAsyncTask extends AsyncTask<Void, Void, Void>{
        private AssessmentDao assessmentDao;

        private DeleteAllAssessmentsAsyncTask(AssessmentDao assessmentDao){
            this.assessmentDao = assessmentDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            assessmentDao.deleteAllAssessments();
            return null;
        }
    }
}
