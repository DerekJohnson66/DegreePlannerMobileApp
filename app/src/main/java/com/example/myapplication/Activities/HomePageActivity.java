package com.example.myapplication.Activities;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class HomePageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Button listOfTerms = (Button) findViewById(R.id.list_of_terms_button);
        listOfTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

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
                Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.go_to_home:
                Intent intent2 = new Intent(HomePageActivity.this, HomePageActivity.class);
                startActivity(intent2);
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
