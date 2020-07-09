package com.example.myapplication.Databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.Assessment;
import com.example.myapplication.Course;
import com.example.myapplication.Note;
import com.example.myapplication.Term;
import com.example.myapplication.dao.AssessmentDao;
import com.example.myapplication.dao.CourseDao;
import com.example.myapplication.dao.NotesDao;
import com.example.myapplication.dao.TermDao;


@Database(entities = {Term.class, Course.class, Assessment.class, Note.class}, version = 8)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();
    public abstract NotesDao notesDao();

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private TermDao termDao;
        private CourseDao courseDao;
        private AssessmentDao assessmentDao;
        private NotesDao notesDao;



        private PopulateDbAsyncTask(AppDatabase db){
            termDao = db.termDao();
            courseDao = db.courseDao();
            assessmentDao = db.assessmentDao();
            notesDao = db.notesDao();
        }
            @Override
            protected Void doInBackground(Void... voids) {

                termDao.insert(new Term("Term 1", 5, 6, 2017, 30, 12, 2017));
                termDao.insert(new Term("Term 2", 5, 1, 2018, 30, 5, 2018));
                termDao.insert(new Term("Term 3", 5, 6, 2018, 30, 12, 2018));

                courseDao.insert(new Course("Course 1", 5, 6, 2017, 30, 12, 2017, "in progress", "Derek Johnson", "111-111-1111", "abc@gmail.com", 1));
                courseDao.insert(new Course("Course 2", 5, 1, 2018, 30, 5, 2018, "in progress", "Derek Johnson", "111-111-1111", "abc@gmail.com", 2));
                courseDao.insert(new Course("Course 3", 5, 6, 2018, 30, 12, 2018, "in progress", "Derek Johnson", "111-111-1111", "abc@gmail.com", 3));

                assessmentDao.insert(new Assessment(1,  "assessment 1", "objective", 4, 30, 2018));
                assessmentDao.insert(new Assessment(2, "assessment 2", "performance", 8, 25, 2019));
                assessmentDao.insert(new Assessment(3, "assessment 3", "objective", 10, 12, 2019));

                notesDao.insert(new Note("Note 1", 1));
                notesDao.insert(new Note("Note 2", 2));
                notesDao.insert(new Note("Note 3", 3));

                return null;
            }

    }
}
