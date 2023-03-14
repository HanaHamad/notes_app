package com.example.notes_app;

public class Note {
    String title;
    String content;
    public String id;

    public Note(String title, String content , String id) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public Note() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

}
