package com.example.ravi.isnotes.database.models;

/**
 * Created by Ravi on 01-Jul-17.
 */
public class Note {

    private int id;
    private String title = "";
    private String description = "";

    public Note() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
