package com.app.taskpage;

//

import com.google.gson.annotations.Expose;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Task.java
//  @ Date : 4/17/2016
//  @ Author : 
//
//
public class Task {
    @Expose
    private String name;
    
    @Expose
    private String description;
    
    @Expose
    private transient BooleanProperty completed = new SimpleBooleanProperty();

    public Task() {
        
    }
    
    public Task(String name) {
        this();
        this.name = name;
    }
    
    public Task(String name, String description) {
        this(name);
        this.description = description;
    }
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BooleanProperty getCompleted() {
        return completed;
    }
    
    public void setCompleted(BooleanProperty completed) {
        this.completed = completed;
    }
}
