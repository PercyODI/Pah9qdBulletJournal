package pah9qdbulletjournal;

import java.util.ArrayList;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Journal.java
//  @ Date : 4/17/2016
//  @ Author : 
//
//
public class Journal {
    private String name;
    private String description;
    private ArrayList<Page> pages = new ArrayList<>();
    private static ArrayList<Journal> journals = new ArrayList<>();
    
    public Journal() {
        journals.add(this);
    }
    
    public Journal(String name) {
        this();
        setName(name);
    }
    
    public Journal(String name, String description) {
        this(name);
        setDescription(description);
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public ArrayList<Page> getPages() {
        return pages;
    }
    
    public void addPage(Page page) {
        if(page == null) {
            System.out.println("Null Page");
        } else {
            pages.add(page);
        }
    }
    
    public void removePage(Page page) {
        pages.remove(page);
    }
    
    public void saveJournalToFile() {
        
    }
    
    public void loadJournalFromFile() {
    }
    
    public static ArrayList<Journal> getJournals() {
        return journals;
    }
    
    public static void addJournal(Journal journal) {
        Journal.journals.add(journal);
    }

}
