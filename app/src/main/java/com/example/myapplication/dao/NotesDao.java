package com.example.myapplication.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Course;
import com.example.myapplication.Note;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM note_table WHERE cId=:cId ORDER BY noteId DESC")
    LiveData<List<Note>> getNoteListByCourse(int cId);
}
