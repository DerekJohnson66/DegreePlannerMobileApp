package com.example.myapplication.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.CourseAdapter;
import com.example.myapplication.Adapters.TermAdapter;
import com.example.myapplication.AlertReceiver;
import com.example.myapplication.Course;
import com.example.myapplication.NotificationHelper;
import com.example.myapplication.R;
import com.example.myapplication.Term;
import com.example.myapplication.ViewModels.CourseViewModel;
import com.example.myapplication.ViewModels.TermViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class DetailedTermViewActivity extends AppCompatActivity {
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
    public static final String  EXTRA_STARTCOURSEALERT =
            "com.example.myapplication.Activities.EXTRA_STARTCOURSEALERT";
    public static final String  EXTRA_ENDCOURSEALERT =
            "com.example.myapplication.Activities.EXTRA_ENDCOURSEALERT";


    private TextView detailedTitle;
    private TextView detailedStartDate;
    private TextView detailedEndDate;

    public static final int ADD_COURSE_REQUEST = 3;
    public static final int EDIT_TERM_REQUEST = 4;
    private CourseViewModel courseViewModel;
    private TermViewModel termViewModel;

    private NotificationHelper mNotificationHelper;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_term_view);

        final Intent pageIntent = getIntent();

        detailedTitle = findViewById(R.id.d_term_view_title);
        detailedStartDate = findViewById(R.id.d_term_view_start_date);
        detailedEndDate = findViewById(R.id.d_term_view_end_date);

        mNotificationHelper = new NotificationHelper(this);

        detailedTitle.setText(getIntent().getStringExtra(EXTRA_TERMTITLE));

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeZone(TimeZone.getDefault());
        startCalendar.set(Calendar.YEAR, Integer.parseInt(pageIntent.getStringExtra(EXTRA_TERMSTARTDATEYEAR)));
        startCalendar.set(Calendar.MONTH, Integer.parseInt(pageIntent.getStringExtra(EXTRA_TERMSTARTDATEMONTH))-1);
        startCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(pageIntent.getStringExtra(EXTRA_TERMSTARTDATEDAY)));
        SimpleDateFormat formatStart = new SimpleDateFormat("MM/dd/yyyy");
        detailedStartDate.setText(formatStart.format(startCalendar.getTime()));

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeZone(TimeZone.getDefault());
        endCalendar.set(Calendar.YEAR, Integer.parseInt(pageIntent.getStringExtra(EXTRA_TERMENDDATEYEAR)));
        endCalendar.set(Calendar.MONTH, Integer.parseInt(pageIntent.getStringExtra(EXTRA_TERMENDDATEMONTH))-1);
        endCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(pageIntent.getStringExtra(EXTRA_TERMENDDATEDAY)));
        SimpleDateFormat formatEnd = new SimpleDateFormat("MM/dd/yyyy");
        detailedEndDate.setText(formatEnd.format(endCalendar.getTime()));


        Button addCourse = findViewById(R.id.term_view_add_course_button);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedTermViewActivity.this, AddCourseActivity.class);
                startActivityForResult(intent, ADD_COURSE_REQUEST);

            }
        });

        Button editTerm = findViewById(R.id.term_view_edit_term_button);
        editTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedTermViewActivity.this, AddEditTermActivity.class);
                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMID, pageIntent.getStringExtra(EXTRA_TERMID));

                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMTITLE, pageIntent.getStringExtra(EXTRA_TERMTITLE));

                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMSTARTDATEMONTH, pageIntent.getStringExtra(EXTRA_TERMSTARTDATEMONTH));
                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMSTARTDATEDAY, pageIntent.getStringExtra(EXTRA_TERMSTARTDATEDAY));
                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMSTARTDATEYEAR, pageIntent.getStringExtra(EXTRA_TERMSTARTDATEYEAR));

                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMENDDATEMONTH, pageIntent.getStringExtra(EXTRA_TERMENDDATEMONTH));
                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMENDDATEDAY, pageIntent.getStringExtra(EXTRA_TERMENDDATEDAY));
                intent.putExtra(DetailedTermViewActivity.EXTRA_TERMENDDATEYEAR, pageIntent.getStringExtra(EXTRA_TERMENDDATEYEAR));
                startActivityForResult(intent, EDIT_TERM_REQUEST);
            }
        });

        Button back = findViewById(R.id.d_term_view_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedTermViewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.d_term_view_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CourseAdapter adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);

        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        final String extra = getIntent().getStringExtra(EXTRA_TERMID);
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getCourseListByTerm(Integer.parseInt(extra)).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable List<Course> courses) {
                adapter.setCourses(courses);
            }

        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                courseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(DetailedTermViewActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(DetailedTermViewActivity.this, DetailedCourseViewActivity.class);

                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEID, Integer.toString(course.getCourseId()));
                intent.putExtra(DetailedCourseViewActivity.EXTRA_TERM_ID, Integer.toString(course.getTermId()));

                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSETITLE, course.getTitle());

                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSESTARTDATEMONTH, Integer.toString(course.getStartDateMonth()));
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSESTARTDATEDAY, Integer.toString(course.getStartDateDay()));
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSESTARTDATEYEAR, Integer.toString(course.getStartDateYear()));

                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEENDDATEMONTH, Integer.toString(course.getEndDateMonth()));
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEENDDATEDAY, Integer.toString(course.getEndDateDay()));
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEENDDATEYEAR, Integer.toString(course.getEndDateYear()));

                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEMENTORNAME, course.getMentorName());
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEMENTORPHONE, course.getMentorPhone());
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEMENTOREMAIL, course.getMentorEmail());
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSESTATUS, course.getStatus());
                startActivity(intent);


            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == EDIT_TERM_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditTermActivity.EXTRA_TERMID, -1);

            if (id == -1){
                Toast.makeText(this, "Term can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditTermActivity.EXTRA_TERMTITLE);
            int startDateMonth = data.getIntExtra(AddEditTermActivity.EXTRA_TERMSTARTDATEMONTH, 1);
            int startDateDay = data.getIntExtra(AddEditTermActivity.EXTRA_TERMSTARTDATEDAY, 1);
            int startDateYear = data.getIntExtra(AddEditTermActivity.EXTRA_TERMSTARTDATEYEAR, 2020);
            int endDateMonth = data.getIntExtra(AddEditTermActivity.EXTRA_TERMENDDATEMONTH, 1);
            int endDateDay = data.getIntExtra(AddEditTermActivity.EXTRA_TERMENDDATEDAY, 1);
            int endDateYear = data.getIntExtra(AddEditTermActivity.EXTRA_TERMENDDATEYEAR, 2020);

            Term term = new Term(title, startDateDay, startDateMonth, startDateYear, endDateMonth, endDateDay, endDateYear);
            term.setId(id);
            termViewModel.update(term);

            Toast.makeText(this, "Term Updated", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetailedTermViewActivity.this, MainActivity.class);
            startActivity(intent);
        }
        if(requestCode == EDIT_TERM_REQUEST && resultCode != RESULT_OK){
            Toast.makeText(this, "Term not saved", Toast.LENGTH_SHORT).show();
        }



        if(requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK){

            String startAlert = data.getStringExtra(AddCourseActivity.EXTRA_STARTCOURSEALERT);
            String endAlert = data.getStringExtra(AddCourseActivity.EXTRA_ENDCOURSEALERT);

            String title = data.getStringExtra(AddCourseActivity.EXTRA_COURSETITLE);
            int startDateMonth = data.getIntExtra(AddCourseActivity.EXTRA_COURSESTARTDATEMONTH, 1);
            int startDateDay = data.getIntExtra(AddCourseActivity.EXTRA_COURSESTARTDATEDAY, 1);
            int startDateYear = data.getIntExtra(AddCourseActivity.EXTRA_COURSESTARTDATEYEAR, 2020);
            int endDateMonth = data.getIntExtra(AddCourseActivity.EXTRA_COURSEENDDATEMONTH, 1);
            int endDateDay = data.getIntExtra(AddCourseActivity.EXTRA_COURSEENDDATEDAY, 1);
            int endDateYear = data.getIntExtra(AddCourseActivity.EXTRA_COURSEENDDATEYEAR, 2020);
            String status = data.getStringExtra(AddCourseActivity.EXTRA_COURSESTATUS);
            String mentorName = data.getStringExtra(AddCourseActivity.EXTRA_COURSEMENTORNAME);
            String mentorPhone = data.getStringExtra(AddCourseActivity.EXTRA_COURSEMENTORPHONE);
            String mentorEmail = data.getStringExtra(AddCourseActivity.EXTRA_COURSEMENTOREMAIL);
            int termId = Integer.parseInt(getIntent().getStringExtra(EXTRA_TERMID));

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.MONTH, startDateMonth);
            startCalendar.set(Calendar.DAY_OF_MONTH, startDateDay);
            startCalendar.set(Calendar.YEAR, startDateYear);



            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(Calendar.MONTH, endDateMonth);
            endCalendar.set(Calendar.DAY_OF_MONTH, endDateDay);
            endCalendar.set(Calendar.YEAR, endDateYear);



            Course course = new Course(title, startDateDay, startDateMonth, startDateYear, endDateDay, endDateMonth, endDateYear, status, mentorName, mentorPhone, mentorEmail, termId);
            courseViewModel.insert(course);

            if(startAlert == "Add Alert"){
                startAlarm(startCalendar);
                Toast.makeText(this, "Start date alert set", Toast.LENGTH_LONG).show();
            }
            if(endAlert == "Add Alert"){
                startAlarm(endCalendar);
                Toast.makeText(this, "End date alert set", Toast.LENGTH_LONG).show();
            }


            Toast.makeText(this, "Course Added", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetailedTermViewActivity.this, MainActivity.class);
            startActivity(intent);

            sendOnCourseChannel(detailedTitle.getText().toString());

        }
        if(requestCode == ADD_COURSE_REQUEST && resultCode != RESULT_OK){
            Toast.makeText(this, "course not saved", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(DetailedTermViewActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.go_to_home:
                Intent intent2 = new Intent(DetailedTermViewActivity.this, HomePageActivity.class);
                startActivity(intent2);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlarm(Calendar calendar){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

    public void sendOnCourseChannel(String title){
        NotificationCompat.Builder nb = mNotificationHelper.getCourseChannelNotification(title);
        mNotificationHelper.getManager().notify(1, nb.build());
    }

}
