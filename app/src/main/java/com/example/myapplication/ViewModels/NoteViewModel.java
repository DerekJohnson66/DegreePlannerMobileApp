package com.example.myapplication.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.Course;
import com.example.myapplication.Note;
import com.example.myapplication.Repositories.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
    }

    public void insert (Note note){
        repository.insert(note);
    }

    public void delete(Note note){
        repository.delete(note);
    }

    public LiveData<List<Note>> getNoteListByCourse(int cId){
        allNotes = repository.getNoteListByCourse(cId);
        return allNotes;
    }
}
