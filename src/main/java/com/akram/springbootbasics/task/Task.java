package com.akram.springbootbasics.task;

import java.util.Date;

public class Task {
    Integer Id;
    String name;
    Date dueDate;
    Boolean completed;

    public Task(Integer id, String name, Date dueDate, Boolean completed) {
        Id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }


}
