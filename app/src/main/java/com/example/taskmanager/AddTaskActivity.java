package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    EditText etName, etDesc;
    Button btnAddTask, btnCancel;
    int reqCode = 12345;
    ArrayList<Task> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etName = (EditText) findViewById(R.id.etName);
        etDesc = (EditText) findViewById(R.id.etDesc);
        btnAddTask = (Button) findViewById(R.id.btnAddTask);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                if (!name.isEmpty() && !desc.isEmpty()) {
                    DBHelper dbhelper = new DBHelper(AddTaskActivity.this);
                    dbhelper.insertToDo(name, desc);
                    setResult(RESULT_OK);
                    finish();
                }

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 5);

                Intent intent = new Intent(AddTaskActivity.this,
                        ScheduledNotificationReceiver.class);

//                for (int a = 0; a < al.size(); a++) {
//                    Task newTask = al.get(a);
//                    String id = Integer.toString(newTask.getId());
//                    String name = newTask.getName();
//                    intent.putExtra("id", id);
//                    intent.putExtra("name", name);
//                    intent.putExtra("desc", newTask);
//                }

                //intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("desc", desc);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        AddTaskActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
