package com.example.myapplication.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.Repositories.TermRepository;
import com.example.myapplication.Term;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private TermRepository repository;
    private LiveData<List<Term>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new TermRepository(application);
        allTerms = repository.getAllTerms();
    }

    public void insert (Term term){
        repository.insert(term);
    }

    public void update(Term term){
        repository.update(term);
    }

    public void delete(Term term){
        repository.delete(term);
    }

    public void deleteAllTerms(){
        repository.deleteAllTerms();
    }

    public LiveData<List<Term>> getAllTerms(){
        return (LiveData<List<Term>>) allTerms;
    }
}
