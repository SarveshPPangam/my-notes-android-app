package com.famt.mynotes;


public class Note {
    private int id;
    private String title;
    private String description;
    private long createdTime;


    public Note(int id, String title, String description, long createdTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdTime = createdTime;
    }

    public Note(String title, String description, long createdTime) {
        this.title = title;
        this.description = description;
        this.createdTime = createdTime;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
