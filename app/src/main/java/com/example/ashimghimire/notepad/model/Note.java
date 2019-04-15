package com.example.ashimghimire.notepad.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_note")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int noteId;

    public Note(String noteTitle, String noteSubject, int notePriority, String noteDescription) {
        this.noteTitle = noteTitle;
        this.noteSubject = noteSubject;
        this.notePriority = notePriority;
        this.noteDescription = noteDescription;
    }


    private String noteTitle;


    private String noteSubject;

    private int notePriority;

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    private String noteDescription;

    public int getNoteId() {
        return noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteSubject() {
        return noteSubject;
    }

    public int getNotePriority() {
        return notePriority;
    }

    public String getNoteDescription() {
        return noteDescription;
    }
}
