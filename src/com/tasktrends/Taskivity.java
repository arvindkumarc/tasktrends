package com.tasktrends;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.tasktrends.dao.DataHelper;

public class Taskivity extends Activity {
    private Button createTask;
    private EditText taskName;
    private DatePicker taskDate;
    private DataHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.tasktrends.R.layout.main);
        initializeControls();
        dbHelper = DataHelper.getInstance(getApplicationContext());
    }

    private void initializeControls() {
        createTask = (Button) findViewById(com.tasktrends.R.id.create_task_button);
        taskName = (EditText) findViewById(com.tasktrends.R.id.task_name);
        taskDate = (DatePicker) findViewById(com.tasktrends.R.id.task_date);

        taskName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String taskNameText = getResources().getString(com.tasktrends.R.string.taskName);
                if (taskNameText.equals(taskName.getText().toString()))
                    taskName.setText("");
            }
        });

        createTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dbHelper.insert(taskName.getText().toString(), (taskDate.getYear() + "-" + taskDate.getMonth() + "-" + taskDate.getDayOfMonth()));
                Toast.makeText(getApplicationContext(), "Task Created", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
