package com.example.myapplication.Activities;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.AssessmentAdapter;
import com.example.myapplication.Assessment;
import com.example.myapplication.Course;
import com.example.myapplication.R;
import com.example.myapplication.ViewModels.AssessmentViewModel;
import com.example.myapplication.ViewModels.CourseViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DetailedAssessmentViewActivity extends AppCompatActivity {
    public static final String EXTRA_COURSEID =
            "com.example.myapplication.Activities.EXTRA_COURSEID";
    public static final String EXTRA_ASSESSMENTID =
            "com.example.myapplication.Activities.EXTRA_ASSESSMENTID";
    public static final String EXTRA_ASSESSMENTDUEDATEMONTH =
            "com.example.myapplication.Activities.EXTRA_ASSESSMENTDUEDATEMONTH";
    public static final String EXTRA_ASSESSMENTDUEDATEDAY =
            "com.example.myapplication.Activities.EXTRA_ASSESSMENTDUEDATEDAY";
    public static final String EXTRA_ASSESSMENTDUEDATEYEAR =
            "com.example.myapplication.Activities.EXTRA_ASSESSMENTDUEDATEYEAR";
    public static final String EXTRA_ASSESSMENTTYPE =
            "com.example.myapplication.Activities.EXTRA_ASSESSMENTTYPE";
    public static final String EXTRA_ASSESSMENTTITLE =
            "com.example.myapplication.Activities.EXTRA_ASSESSMENTTITLE";

    private TextView detailedTitle;
    private TextView detailedDueDate;
    private TextView detailedType;

    public static final int EDIT_ASSESSMENT_REQUEST = 2;
    private AssessmentViewModel assessmentViewModel;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_assessment_view);

        final Intent pageIntent = getIntent();

        detailedTitle = findViewById(R.id.d_assessment_view_title);
        detailedDueDate = findViewById(R.id.d_assessment_view_due_date);
        detailedType = findViewById(R.id.d_assessment_view_type);


        detailedTitle.setText(getIntent().getStringExtra(EXTRA_ASSESSMENTTITLE));
        detailedType.setText(getIntent().getStringExtra(EXTRA_ASSESSMENTTYPE));

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.YEAR, Integer.parseInt(pageIntent.getStringExtra(EXTRA_ASSESSMENTDUEDATEYEAR)));
        startCalendar.set(Calendar.MONTH, Integer.parseInt(pageIntent.getStringExtra(EXTRA_ASSESSMENTDUEDATEMONTH)));
        startCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(pageIntent.getStringExtra(EXTRA_ASSESSMENTDUEDATEDAY)));
        SimpleDateFormat formatStart = new SimpleDateFormat("MM/dd/yyyy");
        detailedDueDate.setText(formatStart.format(startCalendar.getTime()));


        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);


        Button updateAssessment = findViewById(R.id.d_assessment_view_update_button);
        updateAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedAssessmentViewActivity.this, AddAssessmentActivity.class);
                intent.putExtra(DetailedAssessmentViewActivity.EXTRA_ASSESSMENTID, pageIntent.getStringExtra(EXTRA_ASSESSMENTID));
                intent.putExtra(DetailedAssessmentViewActivity.EXTRA_COURSEID, pageIntent.getStringExtra(EXTRA_COURSEID));

                intent.putExtra(DetailedAssessmentViewActivity.EXTRA_ASSESSMENTTITLE, pageIntent.getStringExtra(EXTRA_ASSESSMENTTITLE));

                intent.putExtra(DetailedAssessmentViewActivity.EXTRA_ASSESSMENTDUEDATEMONTH, pageIntent.getStringExtra(EXTRA_ASSESSMENTDUEDATEMONTH));
                intent.putExtra(DetailedAssessmentViewActivity.EXTRA_ASSESSMENTDUEDATEDAY, pageIntent.getStringExtra(EXTRA_ASSESSMENTDUEDATEDAY));
                intent.putExtra(DetailedAssessmentViewActivity.EXTRA_ASSESSMENTDUEDATEYEAR, pageIntent.getStringExtra(EXTRA_ASSESSMENTDUEDATEYEAR));

                intent.putExtra(DetailedAssessmentViewActivity.EXTRA_ASSESSMENTTYPE, pageIntent.getStringExtra(EXTRA_ASSESSMENTTYPE));
                startActivityForResult(intent, EDIT_ASSESSMENT_REQUEST);

            }
        });


    }

//    Results are pushed to this activity where this method updates the assessment in the database.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == EDIT_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddAssessmentActivity.EXTRA_ASSESSMENTID, -1);

            if (id == -1) {
                Toast.makeText(this, "Assessment can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddAssessmentActivity.EXTRA_ASSESSMENTTITLE);
            int dueDateMonth = data.getIntExtra(AddAssessmentActivity.EXTRA_ASSESSMENTDUEDATEMONTH, 1);
            int dueDateDay = data.getIntExtra(AddAssessmentActivity.EXTRA_ASSESSMENTDUEDATEDAY, 1);
            int dueDateYear = data.getIntExtra(AddAssessmentActivity.EXTRA_ASSESSMENTDUEDATEYEAR, 2020);
            String type = data.getStringExtra(AddAssessmentActivity.EXTRA_ASSESSMENTTYPE);

            int courseId = Integer.parseInt(getIntent().getStringExtra(EXTRA_COURSEID));

            Assessment assessment = new Assessment(courseId, title, type, dueDateMonth, dueDateDay, dueDateYear);
            assessment.setAssessmentId(id);
            assessmentViewModel.update(assessment);

            Toast.makeText(this, "Assessment Updated", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetailedAssessmentViewActivity.this, MainActivity.class);
            startActivity(intent);

        }
        if (requestCode == EDIT_ASSESSMENT_REQUEST && resultCode != RESULT_OK) {
            Toast.makeText(this, "Assessment not saved", Toast.LENGTH_SHORT).show();
        }

    }

//    These two methods are for the action bar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_of_terms:
                Intent intent = new Intent(DetailedAssessmentViewActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.go_to_home:
                Intent intent2 = new Intent(DetailedAssessmentViewActivity.this, HomePageActivity.class);
                startActivity(intent2);
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
