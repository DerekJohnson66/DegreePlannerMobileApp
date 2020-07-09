package com.example.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Term;

import java.util.List;

@Dao
public interface TermDao {
    @Insert
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("DELETE FROM term_table")
    void deleteAllTerms();

    @Query("SELECT * FROM term_table ORDER BY startDateYear DESC")
    List<Term> justTerms();

    @Query("SELECT * FROM term_table ORDER BY startDateYear DESC")
    LiveData<List<Term>> getAllTerms();

    @Query("SELECT * FROM term_table WHERE id=:termId")
    LiveData<Term> getTermById(Integer termId);
}
