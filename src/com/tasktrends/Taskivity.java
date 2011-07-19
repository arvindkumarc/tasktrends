package com.tasktrends;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.tasktrends.dao.DataHelper;
import com.tasktrends.utils.DateTime;

public class Taskivity extends Activity {
    private Button createTask;
    private EditText taskName;
    private TextView taskDate;
    private TextView taskTime;
    private DataHelper dbHelper;

    private static final int DATE_DIALOG_ID = 0;
    private static final int TIME_DIALOG_ID = 1;

    private DateTime dateTime;

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.setDate(year, monthOfYear, dayOfMonth);
            updateDisplay();
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            dateTime.setTime(hour, minute);
            updateDisplay();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.tasktrends.R.layout.main);
        initializeControls();
        dbHelper = DataHelper.getInstance(getApplicationContext());
    }

    private void initializeControls() {
        dateTime = new DateTime();
        createTask = (Button) findViewById(R.id.create_task_button);
        taskName = (EditText) findViewById(R.id.task_name);
        taskDate = (TextView) findViewById(R.id.task_date);
        taskTime = (TextView) findViewById(R.id.task_time);

        taskName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String taskNameText = getResources().getString(com.tasktrends.R.string.taskName);
                if (taskNameText.equals(taskName.getText().toString()))
                    taskName.setText("");
            }
        });

        createTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dbHelper.insert(taskName.getText().toString(), taskDate.getText().toString());
                Toast.makeText(getApplicationContext(), "Task Created", Toast.LENGTH_SHORT).show();
            }
        });

        taskDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        taskTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        updateDisplay();
    }

    private void updateDisplay() {
        taskDate.setText(dateTime.getDate());
        taskTime.setText(dateTime.getTime());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener,
                     dateTime.getYear(), dateTime.getMonthToDisplay(), dateTime.getDay());
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, mTimeSetListener, dateTime.getHour(), dateTime.getMinute(), true);
        }
        return null;
    }
}
