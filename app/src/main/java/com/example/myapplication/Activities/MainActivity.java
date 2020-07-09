package com.example.myapplication.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Course;
import com.example.myapplication.R;
import com.example.myapplication.Term;
import com.example.myapplication.Adapters.TermAdapter;
import com.example.myapplication.ViewModels.CourseViewModel;
import com.example.myapplication.ViewModels.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_TERM_REQUEST = 1;
    //public static final int EDIT_TERM_REQUEST = 2;
    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAddTerm = findViewById(R.id.button_add_term);
        buttonAddTerm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, AddEditTermActivity.class);
                startActivityForResult(intent, ADD_TERM_REQUEST);
            }
        });

        Button homePageButton = findViewById(R.id.list_of_terms_home_button);
        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });



        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TermAdapter adapter = new TermAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(@Nullable List<Term> terms) {
                adapter.setTerms(terms);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                0) {



            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
                Term tempTerm = adapter.getTermAt(viewHolder.getAdapterPosition());
                int tempTermId = tempTerm.getTermId();
                int tempIsEmpty = courseViewModel.getCourseCountByTerm(tempTermId);
                if (tempIsEmpty == 0){
                    final int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int dragFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }else{
                    final int dragFlags = 0;
                    final int swipeFlags = 0;
                    Toast.makeText(MainActivity.this, "Term could  not be deleted, there are courses tied to it", Toast.LENGTH_LONG).show();
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                    termViewModel.delete(adapter.getTermAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

            }


        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TermAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Term term) {
                Intent intent = new Intent(MainActivity.this, DetailedTermViewActivity.class);

                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMID, Integer.toString(term.getId()));

                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMTITLE, term.getTitle());

                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMSTARTDATEMONTH, Integer.toString(term.getStartDateMonth()));
                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMSTARTDATEDAY, Integer.toString(term.getStartDateDay()));
                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMSTARTDATEYEAR, Integer.toString(term.getStartDateYear()));

                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMENDDATEMONTH, Integer.toString(term.getEndDateMonth()));
                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMENDDATEDAY, Integer.toString(term.getEndDateDay()));
                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMENDDATEYEAR, Integer.toString(term.getEndDateYear()));
                startActivity(intent);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_TERM_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditTermActivity.EXTRA_TERMTITLE);
            int startDateMonth = data.getIntExtra(AddEditTermActivity.EXTRA_TERMSTARTDATEMONTH, 1);
            int startDateDay = data.getIntExtra(AddEditTermActivity.EXTRA_TERMSTARTDATEDAY, 1);
            int startDateYear = data.getIntExtra(AddEditTermActivity.EXTRA_TERMSTARTDATEYEAR, 2020);
            int endDateMonth = data.getIntExtra(AddEditTermActivity.EXTRA_TERMENDDATEMONTH, 1);
            int endDateDay = data.getIntExtra(AddEditTermActivity.EXTRA_TERMENDDATEDAY, 1);
            int endDateYear = data.getIntExtra(AddEditTermActivity.EXTRA_TERMENDDATEYEAR, 2020);

            Term term = new Term(title, startDateDay, startDateMonth, startDateYear, endDateDay, endDateMonth,  endDateYear);
            termViewModel.insert(term);

            Toast.makeText(this, "Term Saved", Toast.LENGTH_SHORT).show();
         }

        if(requestCode == ADD_TERM_REQUEST && resultCode != RESULT_OK){
            Toast.makeText(this, "Term not saved", Toast.LENGTH_SHORT).show();
        }

        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.main_menu, menu);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.list_of_terms:
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.go_to_home:
                Intent intent2 = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(intent2);
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
