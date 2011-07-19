package com.tasktrends.domain;

import java.util.Date;

public class Task {
    private String taskName;

    public String getTaskName() {
        return taskName;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    private Date taskDate;

    public Task(String taskName, Date taskDate) {
        this.taskName = taskName;
        this.taskDate = taskDate;
    }
}
