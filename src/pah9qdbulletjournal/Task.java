package pah9qdbulletjournal;

//
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
    private String name;
    private String description;
    private Boolean completed;

    Task(String name) {
        this.name = name;
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
    
    public Boolean getCompleted() {
        return completed;
    }
    
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
