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
import android.widget.EditText;
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

import com.example.myapplication.Adapters.AssessmentAdapter;
import com.example.myapplication.Adapters.CourseAdapter;
import com.example.myapplication.Adapters.NoteAdapter;
import com.example.myapplication.Adapters.TermAdapter;
import com.example.myapplication.AlertReceiver;
import com.example.myapplication.Assessment;
import com.example.myapplication.Course;
import com.example.myapplication.Note;
import com.example.myapplication.NotificationHelper;
import com.example.myapplication.R;
import com.example.myapplication.Term;
import com.example.myapplication.ViewModels.AssessmentViewModel;
import com.example.myapplication.ViewModels.CourseViewModel;
import com.example.myapplication.ViewModels.NoteViewModel;
import com.example.myapplication.ViewModels.TermViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DetailedCourseViewActivity extends AppCompatActivity {
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
    public static final String EXRA_TERMID =
            "com.example.myapplication.Activities.EXTRA_TERMID";
    public static final String EXTRA_TERM_ID =
            "com.example.myapplication.Activities.EXTRA_TERM_ID";
    public static final String  EXTRA_NOTE =
            "com.example.myapplication.Activities.EXTRA_NOTE";
    public static final String  EXTRA_ASSESSMENTALERT =
            "com.example.myapplication.Activities.EXTRA_ASSESSMENTALERT";


    private TextView detailedTitle;
    private TextView detailedStartDate;
    private TextView detailedEndDate;
    private TextView detailedStatus;
    private TextView detailedMentorName;
    private TextView detailedMentorPhone;
    private TextView detailedMentorEmail;
    private EditText detailedNote;

    private NotificationHelper mNotificationHelper;

    public static final int ADD_ASSESSMENT_REQUEST = 6;
    public static final int EDIT_COURSE_REQUEST = 2;
    private CourseViewModel courseViewModel;
    private CourseViewModel courseViewModel2;
    private AssessmentViewModel assessmentViewModel;
    private NoteViewModel noteViewModel;
    private String tempTermId;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_course_view);

        final Intent pageIntent = getIntent();

        detailedTitle = findViewById(R.id.d_course_view_title);
        detailedStartDate = findViewById(R.id.d_course_view_start_date);
        detailedEndDate = findViewById(R.id.d_course_view_end_date);
        detailedStatus = findViewById(R.id.d_course_view_status);
        detailedMentorName = findViewById(R.id.d_course_view_mentor_name);
        detailedMentorPhone = findViewById(R.id.d_course_view_mentor_phone);
        detailedMentorEmail = findViewById(R.id.d_course_view_mentor_email);
        detailedNote = findViewById(R.id.d_course_view_note);

        mNotificationHelper = new NotificationHelper(this);

        detailedTitle.setText(getIntent().getStringExtra(EXTRA_COURSETITLE));
        detailedStatus.setText(getIntent().getStringExtra(EXTRA_COURSESTATUS));
        detailedMentorName.setText(getIntent().getStringExtra(EXTRA_COURSEMENTORNAME));
        detailedMentorPhone.setText(getIntent().getStringExtra(EXTRA_COURSEMENTORPHONE));
        detailedMentorEmail.setText(getIntent().getStringExtra(EXTRA_COURSEMENTOREMAIL));

        tempTermId = pageIntent.getStringExtra(EXTRA_TERM_ID);

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.YEAR, Integer.parseInt(pageIntent.getStringExtra(EXTRA_COURSESTARTDATEYEAR)));
        startCalendar.set(Calendar.MONTH, Integer.parseInt(pageIntent.getStringExtra(EXTRA_COURSESTARTDATEMONTH)) - 1);
        startCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(pageIntent.getStringExtra(EXTRA_COURSESTARTDATEDAY)));
        SimpleDateFormat formatStart = new SimpleDateFormat("MM/dd/yyyy");
        detailedStartDate.setText(formatStart.format(startCalendar.getTime()));


        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.YEAR, Integer.parseInt(pageIntent.getStringExtra(EXTRA_COURSEENDDATEYEAR)));
        endCalendar.set(Calendar.MONTH, Integer.parseInt(pageIntent.getStringExtra(EXTRA_COURSEENDDATEMONTH)) - 1);
        endCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(pageIntent.getStringExtra(EXTRA_COURSEENDDATEDAY)));
        SimpleDateFormat formatEnd = new SimpleDateFormat("MM/dd/yyyy");
        detailedEndDate.setText(formatEnd.format(endCalendar.getTime()));



        Button addAssessment = findViewById(R.id.d_course_view_add_button);
        addAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedCourseViewActivity.this, AddAssessmentActivity.class);
                startActivityForResult(intent, ADD_ASSESSMENT_REQUEST);

            }
        });

        Button editCourse = findViewById(R.id.d_course_view_update_button);
        editCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedCourseViewActivity.this, AddCourseActivity.class);
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEID, pageIntent.getStringExtra(EXTRA_COURSEID));

                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSETITLE, pageIntent.getStringExtra(EXTRA_COURSETITLE));

                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSESTARTDATEMONTH, pageIntent.getStringExtra(EXTRA_COURSESTARTDATEMONTH));
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSESTARTDATEDAY, pageIntent.getStringExtra(EXTRA_COURSESTARTDATEDAY));
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSESTARTDATEYEAR, pageIntent.getStringExtra(EXTRA_COURSESTARTDATEYEAR));

                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEENDDATEMONTH, pageIntent.getStringExtra(EXTRA_COURSEENDDATEMONTH));
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEENDDATEDAY, pageIntent.getStringExtra(EXTRA_COURSEENDDATEDAY));
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEENDDATEYEAR, pageIntent.getStringExtra(EXTRA_COURSEENDDATEYEAR));

                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSESTATUS, pageIntent.getStringExtra(EXTRA_COURSESTATUS));
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEMENTORNAME, pageIntent.getStringExtra(EXTRA_COURSEMENTORNAME));
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEMENTORPHONE, pageIntent.getStringExtra(EXTRA_COURSEMENTORPHONE));
                intent.putExtra(DetailedCourseViewActivity.EXTRA_COURSEMENTOREMAIL, pageIntent.getStringExtra(EXTRA_COURSEMENTOREMAIL));

                startActivityForResult(intent, EDIT_COURSE_REQUEST);
            }
        });

        Button addNoteButton = findViewById(R.id.d_course_view_save_note_button);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detailedNote.getText().toString().trim().isEmpty()){
                    Toast.makeText(DetailedCourseViewActivity.this, "Please insert a note", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    saveNote();
                }
            }
        });


        RecyclerView recyclerView = findViewById(R.id.d_course_view_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AssessmentAdapter adapter = new AssessmentAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        String extra = getIntent().getStringExtra(EXTRA_COURSEID);
        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAssessmentListByCourse(Integer.parseInt(extra)).observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(@Nullable List<Assessment> assessments) {
                adapter.setAssessments(assessments);
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
                    assessmentViewModel.delete(adapter.getAssessmentAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(DetailedCourseViewActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                }
            }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new AssessmentAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Assessment assessment) {
                    Intent intent = new Intent(DetailedCourseViewActivity.this, DetailedAssessmentViewActivity.class);


                    intent.putExtra(DetailedAssessmentViewActivity.EXTRA_COURSEID, Integer.toString(assessment.getCourseId()));
                    intent.putExtra(DetailedAssessmentViewActivity.EXTRA_ASSESSMENTID, Integer.toString(assessment.getAssessmentId()));
                    intent.putExtra(DetailedAssessmentViewActivity.EXTRA_ASSESSMENTTITLE, assessment.getTitle());
                    intent.putExtra(DetailedAssessmentViewActivity.EXTRA_ASSESSMENTTYPE, assessment.getType());

                    intent.putExtra(DetailedAssessmentViewActivity.EXTRA_ASSESSMENTDUEDATEMONTH, Integer.toString(assessment.getDueDateMonth()));
                    intent.putExtra(DetailedAssessmentViewActivity.EXTRA_ASSESSMENTDUEDATEDAY, Integer.toString(assessment.getDueDateDay()));
                    intent.putExtra(DetailedAssessmentViewActivity.EXTRA_ASSESSMENTDUEDATEYEAR, Integer.toString(assessment.getDueDateYear()));

                    startActivity(intent);


                }

        });

        RecyclerView noteRecyclerView = findViewById(R.id.d_course_view_recycler_view_notes);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setHasFixedSize(true);

        final NoteAdapter noteAdapter = new NoteAdapter();
        noteRecyclerView.setAdapter(noteAdapter);

        courseViewModel2 = ViewModelProviders.of(this).get(CourseViewModel.class);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);


        noteViewModel.getNoteListByCourse(Integer.parseInt(extra)).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                noteAdapter.setNotes(notes);
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
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(DetailedCourseViewActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(noteRecyclerView);

        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(DetailedCourseViewActivity.this, DetailedNoteView.class);


                intent.putExtra(DetailedNoteView.EXTRA_COURSEID, Integer.toString(note.getCId()));
                intent.putExtra(DetailedNoteView.EXTRA_NOTE, note.getNote());

                startActivity(intent);

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddCourseActivity.EXTRA_COURSEID, -1);

            if (id == -1){
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

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

            int termId = Integer.parseInt(getIntent().getStringExtra(EXTRA_TERM_ID));

            Course course = new Course(title, startDateMonth, startDateDay, startDateYear, endDateMonth, endDateDay, endDateYear, status, mentorName, mentorPhone, mentorEmail, termId);
            course.setId(id);
            courseViewModel.update(course);

            Toast.makeText(this, "Course Updated", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetailedCourseViewActivity.this, MainActivity.class);
            startActivity(intent);

        }
        if(requestCode == EDIT_COURSE_REQUEST && requestCode != RESULT_OK ){
            Toast.makeText(this, "Term not saved", Toast.LENGTH_SHORT).show();
        }


        if(requestCode == ADD_ASSESSMENT_REQUEST && resultCode == RESULT_OK){

            String dueAlert = data.getStringExtra(AddAssessmentActivity.EXTRA_ASSESSMENTALERT);

            String title = data.getStringExtra(AddAssessmentActivity.EXTRA_ASSESSMENTTITLE);
            int dueDateMonth = data.getIntExtra(AddAssessmentActivity.EXTRA_ASSESSMENTDUEDATEMONTH, 1);
            int dueDateDay = data.getIntExtra(AddAssessmentActivity.EXTRA_ASSESSMENTDUEDATEDAY, 1);
            int dueDateYear = data.getIntExtra(AddAssessmentActivity.EXTRA_ASSESSMENTDUEDATEYEAR, 2020);
            String type = data.getStringExtra(AddAssessmentActivity.EXTRA_ASSESSMENTTYPE);


            int courseId = Integer.parseInt(getIntent().getStringExtra(EXTRA_COURSEID));

            Calendar dueCalendar = Calendar.getInstance();
            dueCalendar.set(Calendar.MONTH, dueDateMonth);
            dueCalendar.set(Calendar.DAY_OF_MONTH, dueDateDay);
            dueCalendar.set(Calendar.YEAR, dueDateYear);

            Assessment assessment = new Assessment(courseId, title, type, dueDateMonth, dueDateDay, dueDateYear);
            assessmentViewModel.insert(assessment);

            if(dueAlert == "Add Alert"){
                startAlarm(dueCalendar);
                Toast.makeText(this, "Due date alert set", Toast.LENGTH_SHORT).show();
            }


            Toast.makeText(this, "Assessment Added", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetailedCourseViewActivity.this, MainActivity.class);
            startActivity(intent);

            sendOnAssessmentChannel(detailedTitle.getText().toString());

        }

        if(requestCode == ADD_ASSESSMENT_REQUEST && resultCode != RESULT_OK){
            Toast.makeText(this, "Assessment not saved", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveNote(){
        String newNote = detailedNote.getText().toString();
        int id = Integer.parseInt(getIntent().getStringExtra(EXTRA_COURSEID));

        Note note =  new Note(newNote, id);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.insert(note);
        detailedNote.getText().clear();

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
                Intent intent = new Intent(DetailedCourseViewActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.go_to_home:
                Intent intent2 = new Intent(DetailedCourseViewActivity.this, HomePageActivity.class);
                startActivity(intent2);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlarm(Calendar calendar){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

    public void sendOnAssessmentChannel(String title){
        NotificationCompat.Builder nb = mNotificationHelper.getAssessmentChannelNotification(title);
        mNotificationHelper.getManager().notify(2, nb.build());
    }

}





