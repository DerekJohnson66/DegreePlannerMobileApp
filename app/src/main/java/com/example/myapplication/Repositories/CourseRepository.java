package com.example.myapplication.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.Course;
import com.example.myapplication.Databases.AppDatabase;
import com.example.myapplication.dao.CourseDao;

import java.util.List;

public class CourseRepository {

    private CourseDao courseDao;
    private LiveData<List<Course>> allCourses;
    private int count;

    public CourseRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        courseDao = database.courseDao();
    }

    public void insert(Course course){new InsertCourseAsyncTask(courseDao).execute(course);}

    public void update(Course course){new UpdateCourseAsyncTask(courseDao).execute(course);}

    public void delete(Course course){new DeleteCourseAsyncTask(courseDao).execute(course);}

    public void deleteAllCourses(){new DeleteAllCoursesAsyncTask(courseDao).execute();}


    public LiveData<List<Course>> getCourseListByTerm(int termId){
        allCourses = courseDao.getCourseListByTerm(termId);
        return allCourses;
    }

    public int getCourseCountByTerm(int termId){
        count = courseDao.getCourseCountByTerm(termId);
        return count;
    }

    private static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;

        private InsertCourseAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }
        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.insert(courses[0]);
            return null;
        }
    }

    private static class UpdateCourseAsyncTask extends AsyncTask<Course, Void, Void>{
        private CourseDao courseDao;

        private UpdateCourseAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }
        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.update(courses[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<Course, Void, Void>{
        private CourseDao courseDao;

        private DeleteCourseAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }
        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.delete(courses[0]);
            return null;
        }
    }

    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void>{
        private CourseDao courseDao;

        private DeleteAllCoursesAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            courseDao.deleteAllCourses();
            return null;
        }
    }
}
