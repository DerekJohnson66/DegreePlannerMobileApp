package com.example.myapplication.Activities;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.Fragments.DatePickerFragment;
import com.example.myapplication.Note;
import com.example.myapplication.R;
import com.example.myapplication.ViewModels.AssessmentViewModel;
import com.example.myapplication.ViewModels.NoteViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddCourseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String  EXTRA_COURSEID =
            "com.example.myapplication.Activities.EXTRA_COURSEID";
    public static final String  EXTRA_COURSETITLE =
            "com.example.myapplication.Activities.EXTRA_COURSETITLE";
    public static final String  EXTRA_COURSESTARTDATEMONTH =
            "com.example.myapplication.Activities.EXTRA_COURSESTARTDATEMONTH";
    public static final String  EXTRA_COURSESTARTDATEDAY =
            "com.example.myapplication.Activities.EXTRA_COURSESTARTDATEDAY";
    public static final String  EXTRA_COURSESTARTDATEYEAR =
            "com.example.myapplication.Activities.EXTRA_COURSESTARTDATETYEAR";
    public static final String  EXTRA_COURSEENDDATEMONTH =
            "com.example.myapplication.Activities.EXTRA_COURSEENDDATEMONTH";
    public static final String  EXTRA_COURSEENDDATEDAY =
            "com.example.myapplication.Activities.EXTRA_COURSEENDDATEDAY";
    public static final String  EXTRA_COURSEENDDATEYEAR =
            "com.example.myapplication.Activities.EXTRA_COURSEENDDATEYEAR";
    public static final String  EXTRA_COURSESTATUS =
            "com.example.myapplication.Activities.EXTRA_COURSESTATUS";
    public static final String  EXTRA_COURSEMENTORNAME =
            "com.example.myapplication.Activities.EXTRA_COURSEMENTORNAME";
    public static final String  EXTRA_COURSEMENTORPHONE =
            "com.example.myapplication.Activities.EXTRA_COURSEMENTORPHONE";
    public static final String  EXTRA_COURSEMENTOREMAIL =
            "com.example.myapplication.Activities.EXTRA_COURSEMENTOREMAIL";
    public static final String  EXTRA_COURSENOTES =
            "com.example.myapplication.Activities.EXTRA_COURSENOTES";
    public static final String EXRA_TERMID =
            "com.example.myapplication.Activities.EXTRA_TERMID";
    public static final String  EXTRA_STARTCOURSEALERT =
            "com.example.myapplication.Activities.EXTRA_STARTCOURSEALERT";
    public static final String  EXTRA_ENDCOURSEALERT =
            "com.example.myapplication.Activities.EXTRA_ENDCOURSEALERT";

    public static final int FLAG_START_DATE = 0;
    public static final int FLAG_END_DATE = 1;

    public static final String COURSE_CHANNEL_ID = "coursechannel";


    private NotificationManagerCompat notificationManagerCompat;

    private int flag = 0;

    private NoteViewModel noteViewModel;

    RadioGroup startDateRadioGroup;
    RadioGroup endDateRadioGroup;

    RadioButton startDateRadioButton;
    RadioButton endDateRadioButton;

    private EditText startDateEditText;
    private EditText endDateEditText;
    private EditText editTextTitle;
    private EditText editStartDateMonth;
    private EditText editStartDateDay;
    private EditText editStartDateYear;
    private EditText editEndDateMonth;
    private EditText editEndDateDay;
    private EditText editEndDateYear;
    private EditText editStatus;
    private EditText editMentorName;
    private EditText editMentorPhone;
    private EditText editMentorEmail;
    private EditText editNote;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


//Checks if there is a course ID, if so it sets the layout to the edit course layout. Otherwise it sets the layout to the add course layout.
        if(intent.hasExtra(EXTRA_COURSEID)){
            setContentView(R.layout.edit_course_activity);

            notificationManagerCompat = NotificationManagerCompat.from(this);

            startDateRadioGroup = findViewById(R.id.add_course_start_date_radio_group);
            endDateRadioGroup = findViewById(R.id.add_course_end_date_radio_group);

            startDateEditText = findViewById(R.id.edit_courses_start_date);
            endDateEditText = findViewById(R.id.edit_courses_end_date);
            editTextTitle = findViewById(R.id.edit_courses_title_text);
            editStartDateMonth = findViewById(R.id.edit_courses_start_date_month);
            editStartDateDay = findViewById(R.id.edit_courses_start_date_day);
            editStartDateYear = findViewById(R.id.edit_courses_start_date_year);
            editEndDateMonth = findViewById(R.id.edit_courses_end_date_month);
            editEndDateDay = findViewById(R.id.edit_courses_end_date_day);
            editEndDateYear = findViewById(R.id.edit_courses_end_date_year);
            editStatus = findViewById(R.id.edit_courses_status);
            editMentorName = findViewById(R.id.edit_courses_mentor_name);
            editMentorPhone = findViewById(R.id.edit_courses_mentor_phone);
            editMentorEmail = findViewById(R.id.edit_courses_mentor_email);


            Calendar startDateCalendar = Calendar.getInstance();
            startDateCalendar.set(Calendar.YEAR, Integer.parseInt(intent.getStringExtra(EXTRA_COURSESTARTDATEYEAR)));
            startDateCalendar.set(Calendar.MONTH, Integer.parseInt(intent.getStringExtra(EXTRA_COURSESTARTDATEMONTH)));
            startDateCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(intent.getStringExtra(EXTRA_COURSESTARTDATEDAY)));

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

            Calendar endDateCalendar = Calendar.getInstance();
            endDateCalendar.set(Calendar.YEAR, Integer.parseInt(intent.getStringExtra(EXTRA_COURSEENDDATEYEAR)));
            endDateCalendar.set(Calendar.MONTH, Integer.parseInt(intent.getStringExtra(EXTRA_COURSEENDDATEMONTH)));
            endDateCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(intent.getStringExtra(EXTRA_COURSEENDDATEDAY)));
            setTitle("Edit Course");
            editTextTitle.setText(intent.getStringExtra(EXTRA_COURSETITLE));
            startDateEditText.setText(format.format(startDateCalendar.getTime()));
            endDateEditText.setText(format.format(endDateCalendar.getTime()));
            editEndDateMonth.setText(intent.getStringExtra(EXTRA_COURSEENDDATEMONTH));
            editEndDateDay.setText(intent.getStringExtra(EXTRA_COURSEENDDATEDAY));
            editEndDateYear.setText(intent.getStringExtra(EXTRA_COURSEENDDATEYEAR));
            editStartDateMonth.setText(intent.getStringExtra(EXTRA_COURSESTARTDATEMONTH));
            editStartDateDay.setText(intent.getStringExtra(EXTRA_COURSESTARTDATEDAY));
            editStartDateYear.setText(intent.getStringExtra(EXTRA_COURSESTARTDATEYEAR));
            editStatus.setText(intent.getStringExtra(EXTRA_COURSESTATUS));
            editMentorName.setText(intent.getStringExtra(EXTRA_COURSEMENTORNAME));
            editMentorPhone.setText(intent.getStringExtra(EXTRA_COURSEMENTORPHONE));
            editMentorEmail.setText(intent.getStringExtra(EXTRA_COURSEMENTOREMAIL));

            Button editCoursesPickStartDate = findViewById(R.id.edit_courses_start_date_button);
            editCoursesPickStartDate.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    DatePickerFragment startDatePicker = new DatePickerFragment();
                    flag = FLAG_START_DATE;
                    startDatePicker.show(getSupportFragmentManager(), "start date picker");
                }
            });
            Button editCoursesPickEndDate = findViewById(R.id.edit_courses_end_date_button);
            editCoursesPickEndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment endDatePicker = new DatePickerFragment();
                    flag = FLAG_END_DATE;
                    endDatePicker.show(getSupportFragmentManager(), "end date picker");
                }
            });
            Button updateCourseButton = findViewById(R.id.edit_courses_save_button);
            updateCourseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateCourse();
                }
            });

            Button editCancelButton = findViewById(R.id.edit_courses_cancel_button);
            editCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddCourseActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            });


        }else{
            setTitle("Add Course");
            setContentView(R.layout.add_courses_activity);

            startDateRadioGroup = findViewById(R.id.add_course_start_date_radio_group);
            endDateRadioGroup = findViewById(R.id.add_course_end_date_radio_group);

            startDateEditText = findViewById(R.id.add_courses_start_date);
            endDateEditText = findViewById(R.id.add_courses_end_date);
            editTextTitle = findViewById(R.id.add_courses_title_text);
            editStartDateMonth = findViewById(R.id.add_courses_start_date_month);
            editStartDateDay = findViewById(R.id.add_courses_start_date_day);
            editStartDateYear = findViewById(R.id.add_courses_start_date_year);
            editEndDateMonth = findViewById(R.id.add_courses_end_date_month);
            editEndDateDay = findViewById(R.id.add_courses_end_date_day);
            editEndDateYear = findViewById(R.id.add_courses_end_date_year);
            editStatus = findViewById(R.id.add_courses_status);
            editMentorName = findViewById(R.id.add_courses_mentor_name);
            editMentorPhone = findViewById(R.id.add_courses_mentor_phone);
            editMentorEmail = findViewById(R.id.add_courses_mentor_email);

            Button coursesPickStartDate = findViewById(R.id.add_courses_start_date_button);
            coursesPickStartDate.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    DatePickerFragment startDatePicker = new DatePickerFragment();
                    flag = FLAG_START_DATE;
                    startDatePicker.show(getSupportFragmentManager(), "start date picker");
                }
            });
            Button coursesPickEndDate = findViewById(R.id.add_courses_end_date_button);
            coursesPickEndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment endDatePicker = new DatePickerFragment();
                    flag = FLAG_END_DATE;
                    endDatePicker.show(getSupportFragmentManager(), "end date picker");
                }
            });
            Button saveCourseButton = findViewById(R.id.add_courses_save_button);
            saveCourseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveCourse();
                }
            });


            Button cancelButton = findViewById(R.id.add_courses_cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddCourseActivity.this, AddEditTermActivity.class);
                    startActivityForResult(intent, 1);

                }
            });
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

    private void saveCourse(){

        int startRadioId = startDateRadioGroup.getCheckedRadioButtonId();
        int endRadioId = endDateRadioGroup.getCheckedRadioButtonId();

        startDateRadioButton = findViewById(startRadioId);
        endDateRadioButton = findViewById(endRadioId);

        Intent intent = getIntent();

        String title = editTextTitle.getText().toString();
        int startDateDay = Integer.parseInt(String.valueOf(editStartDateDay.getText()));
        int startDateMonth = Integer.parseInt(String.valueOf(editStartDateMonth.getText()));
        int startDateYear = Integer.parseInt(String.valueOf(editStartDateYear.getText()));

        int endDateMonth = Integer.parseInt(String.valueOf(editEndDateMonth.getText()));
        int endDateDay = Integer.parseInt(String.valueOf(editEndDateDay.getText()));
        int endDateYear = Integer.parseInt(String.valueOf(editEndDateYear.getText()));
        String courseStatus = editStatus.getText().toString();
        String courseMentorName = editMentorName.getText().toString();
        String courseMentorPhone = editMentorPhone.getText().toString();
        String courseMentorEmail = editMentorEmail.getText().toString();


        if(title.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_STARTCOURSEALERT, startDateRadioButton.getText().toString());
        data.putExtra(EXTRA_ENDCOURSEALERT, endDateRadioButton.getText().toString());
        data.putExtra(EXTRA_COURSETITLE, title);
        data.putExtra(EXTRA_COURSESTARTDATEMONTH, startDateMonth);
        data.putExtra(EXTRA_COURSESTARTDATEDAY, startDateDay);
        data.putExtra(EXTRA_COURSESTARTDATEYEAR, startDateYear);
        data.putExtra(EXTRA_COURSEENDDATEMONTH, endDateMonth);
        data.putExtra(EXTRA_COURSEENDDATEDAY, endDateDay);
        data.putExtra(EXTRA_COURSEENDDATEYEAR, endDateYear);
        data.putExtra(EXTRA_COURSESTATUS, courseStatus);
        data.putExtra(EXTRA_COURSEMENTORNAME, courseMentorName);
        data.putExtra(EXTRA_COURSEMENTORPHONE, courseMentorPhone);
        data.putExtra(EXTRA_COURSEMENTOREMAIL, courseMentorEmail);



        int id = getIntent().getIntExtra(EXTRA_COURSEID, -1);
        if(id != -1){
            data.putExtra(EXTRA_COURSEID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    private void updateCourse(){

        String title = editTextTitle.getText().toString();
        int id = Integer.parseInt(getIntent().getStringExtra(EXTRA_COURSEID));
        int startDateDay = Integer.parseInt(String.valueOf(editStartDateDay.getText()));
        int startDateMonth = Integer.parseInt(String.valueOf(editStartDateMonth.getText()));
        int startDateYear = Integer.parseInt(String.valueOf(editStartDateYear.getText()));

        int endDateMonth = Integer.parseInt(String.valueOf(editEndDateMonth.getText()));
        int endDateDay = Integer.parseInt(String.valueOf(editEndDateDay.getText()));
        int endDateYear = Integer.parseInt(String.valueOf(editEndDateYear.getText()));

        String courseStatus = editStatus.getText().toString();
        String courseMentorName = editMentorName.getText().toString();
        String courseMentorPhone = editMentorPhone.getText().toString();
        String courseMentorEmail = editMentorEmail.getText().toString();


        if(title.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_COURSETITLE, title);
        data.putExtra(EXTRA_COURSESTARTDATEMONTH, startDateMonth);
        data.putExtra(EXTRA_COURSESTARTDATEDAY, startDateDay);
        data.putExtra(EXTRA_COURSESTARTDATEYEAR, startDateYear);
        data.putExtra(EXTRA_COURSEENDDATEMONTH, endDateMonth);
        data.putExtra(EXTRA_COURSEENDDATEDAY, endDateDay);
        data.putExtra(EXTRA_COURSEENDDATEYEAR, endDateYear);
        data.putExtra(EXTRA_COURSESTATUS, courseStatus);
        data.putExtra(EXTRA_COURSEMENTORNAME, courseMentorName);
        data.putExtra(EXTRA_COURSEMENTORPHONE, courseMentorPhone);
        data.putExtra(EXTRA_COURSEMENTOREMAIL, courseMentorEmail);

        data.putExtra(EXTRA_COURSEID, id);

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
                Intent intent = new Intent(AddCourseActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.go_to_home:
                Intent intent2 = new Intent(AddCourseActivity.this, HomePageActivity.class);
                startActivity(intent2);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void setFlag(int i){
        flag = i;
    }

}

