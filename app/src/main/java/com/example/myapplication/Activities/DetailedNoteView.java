package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class DetailedNoteView extends AppCompatActivity {
    public static final String  EXTRA_COURSEID =
            "com.example.myapplication.Activities.EXTRA_COURSEID";
    public static final String  EXTRA_NOTE =
            "com.example.myapplication.Activities.EXTRA_NOTE";

    private EditText messageEditText;
    private EditText subjectEditText;
    private EditText toEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_email_view);

        final Intent pageIntent = getIntent();

        messageEditText = findViewById(R.id.send_email_message);
        subjectEditText = findViewById(R.id.send_email_subject);
        toEditText = findViewById(R.id.send_email_to);

        messageEditText.setText(getIntent().getStringExtra(EXTRA_NOTE));

        Button sendNote = findViewById(R.id.send_email_send_button);
        sendNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();

            }
        });

        Button cancelEmail = findViewById(R.id.send_email_cancel_button);
        cancelEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedNoteView.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void sendMail(){
        String recipientList = toEditText.getText().toString();
        String[] recipients = recipientList.split(",");
        String subject = subjectEditText.getText().toString();
        String message = messageEditText.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Email Client"));

    }
}
