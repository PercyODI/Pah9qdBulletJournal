package com.app.taskpage;
//
//  Generated by StarUML(tm) Java Add-In

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import pah9qdbulletjournal.Page;

//
//  @ Project : Untitled
//  @ File Name : TaskPage.java
//  @ Date : 4/17/2016
//  @ Author : 
//
//

public class TaskPage extends Page{
    private ObservableList<Task> tasks = FXCollections.observableArrayList();
    private final static String FXMLFILENAME = "TaskPageUI.fxml"; //TODO is there a better way to find the fxml's dynamically?
    
    public TaskPage() {
        
    }
    
    public TaskPage(String name) {
        super(name);
    }
    
    public TaskPage(String name, String description) {
        super(name, description);
    }
    
    public ObservableList<Task> getTasks() {
        return tasks;
    }
    
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    @Override
    public FXMLLoader getFXMLLoader() {
        return new FXMLLoader(getClass().getResource(TaskPage.FXMLFILENAME));
    }
}
