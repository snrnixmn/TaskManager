package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnAddTask;
    ListView lv;
    ArrayList<Task> al;
    TaskAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddTask = (Button) findViewById(R.id.btnAddNewTask);
        lv = findViewById(R.id.lv);
        DBHelper db = new DBHelper(MainActivity.this);

        al = new ArrayList<>();
        aa = new TaskAdapter(this, R.layout.row, al);
        lv.setAdapter(aa);
        al.clear();
        al.addAll(db.getTask());
        aa.notifyDataSetChanged();
        db.close();

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(i, 9);
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode,
        Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK && requestCode == 9) {
                DBHelper db = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(db.getTask());
                db.close();
                aa.notifyDataSetChanged();
            }

        }
    }

