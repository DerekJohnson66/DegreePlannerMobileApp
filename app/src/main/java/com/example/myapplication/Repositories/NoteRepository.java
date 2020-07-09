package com.example.myapplication.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.Course;
import com.example.myapplication.Databases.AppDatabase;
import com.example.myapplication.Note;
import com.example.myapplication.Term;
import com.example.myapplication.dao.CourseDao;
import com.example.myapplication.dao.NotesDao;
import com.example.myapplication.dao.TermDao;

import java.util.List;

public class NoteRepository {
    private NotesDao notesDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        notesDao = database.notesDao();
    }

    public LiveData<List<Note>> getNoteListByCourse(int cId) {
        allNotes = notesDao.getNoteListByCourse(cId);
        return allNotes;
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(notesDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(notesDao).execute(note);
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NotesDao notesDao;

        private InsertNoteAsyncTask(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notesDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NotesDao notesDao;

        private DeleteNoteAsyncTask(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notesDao.delete(notes[0]);
            return null;
        }
    }


}
