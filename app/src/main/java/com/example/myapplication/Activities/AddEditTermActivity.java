package com.example.myapplication.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Fragments.DatePickerFragment;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddEditTermActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String  EXTRA_TERMID =
            "com.example.myapplication.Activities.EXTRA_TERMID";
    public static final String  EXTRA_TERMTITLE =
            "com.example.myapplication.Activities.EXTRA_TERMTITLE";
    public static final String  EXTRA_TERMSTARTDATEMONTH =
            "com.example.myapplication.Activities.EXTRA_TERMSTARTDATEMONTH";
    public static final String  EXTRA_TERMSTARTDATEDAY =
            "com.example.myapplication.Activities.EXTRA_TERMSTARTDATEDAY";
    public static final String  EXTRA_TERMSTARTDATEYEAR =
            "com.example.myapplication.Activities.EXTRA_TERMSTARTDATETYEAR";
    public static final String  EXTRA_TERMENDDATEMONTH =
            "com.example.myapplication.Activities.EXTRA_TERMENDDATEMONTH";
    public static final String  EXTRA_TERMENDDATEDAY =
            "com.example.myapplication.Activities.EXTRA_TERMENDDATEDAY";
    public static final String  EXTRA_TERMENDDATEYEAR =
            "com.example.myapplication.Activities.EXTRA_TERMENDDATEYEAR";

    public static final int FLAG_START_DATE = 0;
    public static final int FLAG_END_DATE = 1;
    private int flag = 0;


    private EditText startDateEditText;
    private EditText endDateEditText;
    private EditText editTextTitle;
    private EditText editStartDateMonth;
    private EditText editStartDateDay;
    private EditText editStartDateYear;
    private EditText editEndDateMonth;
    private EditText editEndDateDay;
    private EditText editEndDateYear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

//        Checks if there is a Term ID, if so it will set the layout to the edit term layout. Otherwise it will set it to the add term layout.
        if(intent.hasExtra(EXTRA_TERMID)){
            setContentView(R.layout.edit_term_activity);

            Button addPickStartDateButton = findViewById(R.id.edit_term_pick_start_date_button);
            addPickStartDateButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    DatePickerFragment startDatePicker = new DatePickerFragment();
                    flag = FLAG_START_DATE;
                    startDatePicker.show(getSupportFragmentManager(), "start date picker");
                }
            });
            Button addPickEndDateButton = findViewById(R.id.edit_term_pick_end_date_button);
            addPickEndDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment endDatePicker = new DatePickerFragment();
                    flag = FLAG_END_DATE;
                    endDatePicker.show(getSupportFragmentManager(), "end date picker");
                }
            });
            Button saveTermButton = findViewById(R.id.edit_term_update_button);
            saveTermButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateTerm();
                }
            });

            startDateEditText = findViewById(R.id.edit_term_full_start_date);
            endDateEditText = findViewById(R.id.edit_term_full_end_date);
            editTextTitle = findViewById(R.id.edit_term_title);
            editStartDateMonth = findViewById(R.id.edit_term_start_date_month);
            editStartDateDay = findViewById(R.id.edit_term_start_date_day);
            editStartDateYear = findViewById(R.id.edit_term_start_date_year);
            editEndDateMonth = findViewById(R.id.edit_term_end_date_month);
            editEndDateDay = findViewById(R.id.edit_term_end_date_day);
            editEndDateYear = findViewById(R.id.edit_term_end_date_year);

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

            Calendar startDateCalendar = Calendar.getInstance();
            startDateCalendar.set(Calendar.YEAR, Integer.parseInt(intent.getStringExtra(EXTRA_TERMSTARTDATEYEAR)));
            startDateCalendar.set(Calendar.MONTH, Integer.parseInt(intent.getStringExtra(EXTRA_TERMSTARTDATEMONTH)));
            startDateCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(intent.getStringExtra(EXTRA_TERMSTARTDATEDAY)));

            Calendar endDateCalendar = Calendar.getInstance();
            endDateCalendar.set(Calendar.YEAR, Integer.parseInt(intent.getStringExtra(EXTRA_TERMENDDATEYEAR)));
            endDateCalendar.set(Calendar.MONTH, Integer.parseInt(intent.getStringExtra(EXTRA_TERMENDDATEMONTH)));
            endDateCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(intent.getStringExtra(EXTRA_TERMENDDATEDAY)));
            setTitle("Edit Term");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TERMTITLE));
            startDateEditText.setText(format.format(startDateCalendar.getTime()));
            endDateEditText.setText(format.format(endDateCalendar.getTime()));
            editStartDateMonth.setText(intent.getStringExtra(EXTRA_TERMSTARTDATEMONTH));
            editStartDateDay.setText(intent.getStringExtra(EXTRA_TERMSTARTDATEDAY));
            editStartDateYear.setText(intent.getStringExtra(EXTRA_TERMSTARTDATEYEAR));
            editEndDateMonth.setText(intent.getStringExtra(EXTRA_TERMENDDATEMONTH));
            editEndDateDay.setText(intent.getStringExtra(EXTRA_TERMENDDATEDAY));
            editEndDateYear.setText(intent.getStringExtra(EXTRA_TERMENDDATEYEAR));

        }else{
            setContentView(R.layout.add_term_activity);

            Button addPickStartDateButton = findViewById(R.id.add_term_pick_start_date_button);
            addPickStartDateButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    DatePickerFragment startDatePicker = new DatePickerFragment();
                    flag = FLAG_START_DATE;
                    startDatePicker.show(getSupportFragmentManager(), "start date picker");
                }
            });
            Button addPickEndDateButton = findViewById(R.id.add_term_pick_end_date_button);
            addPickEndDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment endDatePicker = new DatePickerFragment();
                    flag = FLAG_END_DATE;
                    endDatePicker.show(getSupportFragmentManager(), "end date picker");
                }
            });
            Button saveTermButton = findViewById(R.id.add_term_save_button);
            saveTermButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveTerm();
                }
            });

            startDateEditText = findViewById(R.id.add_term_full_start_date);
            endDateEditText = findViewById(R.id.add_term_full_end_date);
            editTextTitle = findViewById(R.id.add_term_title);
            editStartDateMonth = findViewById(R.id.add_term_start_date_month);
            editStartDateDay = findViewById(R.id.add_term_start_date_day);
            editStartDateYear = findViewById(R.id.add_term_start_date_year);
            editEndDateMonth = findViewById(R.id.add_term_end_date_month);
            editEndDateDay = findViewById(R.id.add_term_end_date_day);
            editEndDateYear = findViewById(R.id.add_term_end_date_year);

            setTitle("Add Term");
        }



    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        if(flag  == FLAG_START_DATE){
            startDateEditText.setText(format.format(calendar.getTime()));
            editStartDateMonth.setText(Integer.toString(calendar.get(Calendar.MONTH) + 1));
            editStartDateDay.setText(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
            editStartDateYear.setText(Integer.toString(calendar.get(Calendar.YEAR)));
        }
        else if(flag == FLAG_END_DATE){
            endDateEditText.setText(format.format(calendar.getTime()));
            editEndDateMonth.setText(Integer.toString(calendar.get(Calendar.MONTH) + 1));
            editEndDateDay.setText(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
            editEndDateYear.setText(Integer.toString(calendar.get(Calendar.YEAR)));
        }
    }

    private void saveTerm(){


        String title = editTextTitle.getText().toString();
        int startDateDay = Integer.parseInt(String.valueOf(editStartDateDay.getText()));
        int startDateMonth = (Integer.parseInt(String.valueOf(editStartDateMonth.getText())));
        int startDateYear = Integer.parseInt(String.valueOf(editStartDateYear.getText()));

        int endDateMonth = (Integer.parseInt(String.valueOf(editEndDateMonth.getText())));
        int endDateDay = Integer.parseInt(String.valueOf(editEndDateDay.getText()));
        int endDateYear = Integer.parseInt(String.valueOf(editEndDateYear.getText()));
        
        if(title.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TERMTITLE, title);
        data.putExtra(EXTRA_TERMSTARTDATEMONTH, startDateMonth);
        data.putExtra(EXTRA_TERMSTARTDATEDAY, startDateDay);
        data.putExtra(EXTRA_TERMSTARTDATEYEAR, startDateYear);
        data.putExtra(EXTRA_TERMENDDATEMONTH, endDateMonth);
        data.putExtra(EXTRA_TERMENDDATEDAY, endDateDay);
        data.putExtra(EXTRA_TERMENDDATEYEAR, endDateYear);
        data.putExtra(EXTRA_TERMID, -1);

        setResult(RESULT_OK, data);
        finish();
    }

    private void updateTerm(){

        String title = editTextTitle.getText().toString();
        int id = Integer.parseInt(getIntent().getStringExtra(EXTRA_TERMID));
        int startDateDay = Integer.parseInt(String.valueOf(editStartDateDay.getText()));
        int startDateMonth = (Integer.parseInt(String.valueOf(editStartDateMonth.getText())) - 1);
        int startDateYear = Integer.parseInt(String.valueOf(editStartDateYear.getText()));

        int endDateMonth = (Integer.parseInt(String.valueOf(editEndDateMonth.getText())) - 1);
        int endDateDay = Integer.parseInt(String.valueOf(editEndDateDay.getText()));
        int endDateYear = Integer.parseInt(String.valueOf(editEndDateYear.getText()));

        if(title.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TERMTITLE, title);
        data.putExtra(EXTRA_TERMSTARTDATEMONTH, startDateMonth);
        data.putExtra(EXTRA_TERMSTARTDATEDAY, startDateDay);
        data.putExtra(EXTRA_TERMSTARTDATEYEAR, startDateYear);
        data.putExtra(EXTRA_TERMENDDATEMONTH, endDateMonth);
        data.putExtra(EXTRA_TERMENDDATEDAY, endDateDay);
        data.putExtra(EXTRA_TERMENDDATEYEAR, endDateYear);


            data.putExtra(EXTRA_TERMID, id);


        setResult(RESULT_OK, data);
        finish();
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
                Intent intent = new Intent(AddEditTermActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.go_to_home:
                Intent intent2 = new Intent(AddEditTermActivity.this, HomePageActivity.class);
                startActivity(intent2);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
