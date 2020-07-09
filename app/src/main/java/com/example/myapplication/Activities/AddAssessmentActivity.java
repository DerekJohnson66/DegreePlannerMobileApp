package com.example.myapplication.Activities;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import com.example.myapplication.Fragments.DatePickerFragment;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddAssessmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
        public static final String  EXTRA_ASSESSMENTID =
                "com.example.myapplication.Activities.EXTRA_ASSESSMENTID";
        public static final String  EXTRA_ASSESSMENTTITLE =
                "com.example.myapplication.Activities.EXTRA_ASSESSMENTTITLE";
        public static final String  EXTRA_ASSESSMENTTYPE =
                "com.example.myapplication.Activities.EXTRA_ASSESSMENTTYPE";
        public static final String  EXTRA_ASSESSMENTDUEDATEMONTH =
                "com.example.myapplication.Activities.EXTRA_ASSESSMENTDUEDATEMONTH";
        public static final String  EXTRA_ASSESSMENTDUEDATEDAY =
                "com.example.myapplication.Activities.EXTRA_ASSESSMENTDUEDATEDAY";
        public static final String  EXTRA_ASSESSMENTDUEDATEYEAR =
                "com.example.myapplication.Activities.EXTRA_ASSESSMENTDUEDATEYEAR";
    public static final String  EXTRA_COURSEID =
            "com.example.myapplication.Activities.EXTRA_COURSEID";
    public static final String  EXTRA_ASSESSMENTALERT =
            "com.example.myapplication.Activities.EXTRA_ASSESSMENTALERT";

    public static final String ASSESSMENT_CHANNEL_ID = "assessmentchannel";

    private NotificationManagerCompat notificationManagerCompat;


        private EditText assessmentTitle;
        private EditText assessmentType;
        private EditText assessmentDueDateMonth;
        private EditText assessmentDueDateDay;
        private EditText assessmentDueDateYear;
        private EditText assessmentDueDate;

        RadioGroup dueDateRadioGroup;

        RadioButton dueDateRadioButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

            Intent intent = getIntent();

//            Checks if the activity starts with an assessment ID, if so it starts sets the update layout. Otherwise ;it sets up the add assessment layout.

            if(intent.hasExtra(EXTRA_ASSESSMENTID)){
                setContentView(R.layout.edit_assessment_activity);

                dueDateRadioGroup = findViewById(R.id.add_assessment_due_radio_group);

                assessmentTitle = findViewById(R.id.edit_assessments_title);
                assessmentType = findViewById(R.id.edit_assessments_type);
                assessmentDueDate = findViewById(R.id.edit_assessments_due_date);
                assessmentDueDateMonth = findViewById(R.id.edit_assessments_due_date_month);
                assessmentDueDateDay = findViewById(R.id.edit_assessments_due_date_day);
                assessmentDueDateYear = findViewById(R.id.edit_assessments_due_date_year);

                assessmentTitle.setText(intent.getStringExtra(EXTRA_ASSESSMENTTITLE));
                assessmentDueDateMonth.setText(intent.getStringExtra(EXTRA_ASSESSMENTDUEDATEMONTH));
                assessmentDueDateDay.setText(intent.getStringExtra(EXTRA_ASSESSMENTDUEDATEDAY));
                assessmentDueDateYear.setText(intent.getStringExtra(EXTRA_ASSESSMENTDUEDATEYEAR));
                assessmentType.setText(intent.getStringExtra(EXTRA_ASSESSMENTTYPE));

                Calendar dueDateCalendar = Calendar.getInstance();
                dueDateCalendar.set(Calendar.YEAR, Integer.parseInt(intent.getStringExtra(EXTRA_ASSESSMENTDUEDATEYEAR)));
                dueDateCalendar.set(Calendar.MONTH, Integer.parseInt(intent.getStringExtra(EXTRA_ASSESSMENTDUEDATEMONTH)));
                dueDateCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(intent.getStringExtra(EXTRA_ASSESSMENTDUEDATEDAY)));

                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

                setTitle("Edit Assessment");
                assessmentTitle.setText(intent.getStringExtra(intent.getStringExtra(EXTRA_ASSESSMENTTITLE)));

                assessmentDueDate.setText(format.format(dueDateCalendar.getTime()));

                Button editPickDueDate = findViewById(R.id.edit_assessments_due_date_button);
                editPickDueDate.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        DatePickerFragment dueDatePicker = new DatePickerFragment();
                        dueDatePicker.show(getSupportFragmentManager(), "due date picker");
                    }
                });
                Button updateAssessmentButton = findViewById(R.id.edit_assessments_save_button);
                updateAssessmentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateAssessment();
                    }
                });
                Button editCancelButton = findViewById(R.id.edit_assessments_cancel_button);
                editCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AddAssessmentActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                });


            }else{
                setTitle("Add Assessment");
                setContentView(R.layout.add_assessment_activity);

                dueDateRadioGroup = findViewById(R.id.add_assessment_due_radio_group);

                assessmentTitle = findViewById(R.id.add_assessments_title);
                assessmentType = findViewById(R.id.add_assessments_type);
                assessmentDueDate = findViewById(R.id.add_assessments_due_date);
                assessmentDueDateMonth = findViewById(R.id.add_assessments_due_date_month);
                assessmentDueDateDay = findViewById(R.id.add_assessments_due_date_day);
                assessmentDueDateYear = findViewById(R.id.add_assessments_due_date_year);


                Button pickDueDate = findViewById(R.id.add_assessments_due_date_button);
                pickDueDate.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        DatePickerFragment dueDatePicker = new DatePickerFragment();
                        dueDatePicker.show(getSupportFragmentManager(), "due date picker");
                    }
                });
                Button saveAssessmentButton = findViewById(R.id.add_assessments_save_button);
                saveAssessmentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveAssessment();
                    }
                });
                Button cancelButton = findViewById(R.id.add_assessments_cancel_button);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AddAssessmentActivity.this, AddCourseActivity.class);
                        startActivityForResult(intent, 1);

                    }
                });
            }


        }

//        this is called when selecting the date and sets the EditText fields based on the date picked.
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

                assessmentDueDate.setText(format.format(calendar.getTime()));
                assessmentDueDateMonth.setText(Integer.toString(calendar.get(Calendar.MONTH)));
                assessmentDueDateDay.setText(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
                assessmentDueDateYear.setText(Integer.toString(calendar.get(Calendar.YEAR)));

        }

//        this method sends all of the content back to the detailed course view to be inserted into the database.
        private void saveAssessment(){

            int dueRadioId = dueDateRadioGroup.getCheckedRadioButtonId();

            dueDateRadioButton = findViewById(dueRadioId);

            String title = assessmentTitle.getText().toString();
            int dueDateDay = Integer.parseInt(String.valueOf(assessmentDueDateDay.getText()));
            int dueDateMonth = Integer.parseInt(String.valueOf(assessmentDueDateMonth.getText()));
            int dueDateYear = Integer.parseInt(String.valueOf(assessmentDueDateYear.getText()));
            String saveAssessmentType = assessmentType.getText().toString();

            if(title.trim().isEmpty()){
                Toast.makeText(this, "Please insert a title", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_ASSESSMENTALERT, dueDateRadioButton.getText().toString());
            data.putExtra(EXTRA_ASSESSMENTTITLE, title);
            data.putExtra(EXTRA_ASSESSMENTDUEDATEDAY, dueDateDay);
            data.putExtra(EXTRA_ASSESSMENTDUEDATEMONTH, dueDateMonth);
            data.putExtra(EXTRA_ASSESSMENTDUEDATEYEAR, dueDateYear);
            data.putExtra(EXTRA_ASSESSMENTTYPE, saveAssessmentType);

            int id = getIntent().getIntExtra(EXTRA_ASSESSMENTID, -1);
            if(id != -1){
                data.putExtra(EXTRA_ASSESSMENTID, id);
            }

            setResult(RESULT_OK, data);
            finish();
        }

//        this method send the data back to the detailed course view to update the database.
        private void updateAssessment(){
            String title = assessmentTitle.getText().toString();
            int id = Integer.parseInt(getIntent().getStringExtra(EXTRA_ASSESSMENTID));
            int dueDateDay = Integer.parseInt(String.valueOf(assessmentDueDateDay.getText()));
            int dueDateMonth = Integer.parseInt(String.valueOf(assessmentDueDateMonth.getText()));
            int dueDateYear = Integer.parseInt(String.valueOf(assessmentDueDateYear.getText()));

            String updateAssessmentType = assessmentType.getText().toString();

            if(title.trim().isEmpty()){
                Toast.makeText(this, "Please insert a title", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_ASSESSMENTTITLE, title);
            data.putExtra(EXTRA_ASSESSMENTDUEDATEMONTH, dueDateMonth);
            data.putExtra(EXTRA_ASSESSMENTDUEDATEDAY, dueDateDay);
            data.putExtra(EXTRA_ASSESSMENTDUEDATEYEAR, dueDateYear);
            data.putExtra(EXTRA_ASSESSMENTTYPE, updateAssessmentType);

            data.putExtra(EXTRA_ASSESSMENTID, id);

            setResult(RESULT_OK, data);
            finish();

        }

// These two methods are for the action bar.
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
                Intent intent = new Intent(AddAssessmentActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.go_to_home:
                Intent intent2 = new Intent(AddAssessmentActivity.this, HomePageActivity.class);
                startActivity(intent2);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
