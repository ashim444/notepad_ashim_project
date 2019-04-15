package com.example.ashimghimire.notepad.viewmodel;

import android.app.Application;

//import com.example.ashimghimire.notepad.data.Note;

import com.example.ashimghimire.notepad.local.NoteRepertoire;
import com.example.ashimghimire.notepad.model.Note;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MyViewModel extends AndroidViewModel {

    private NoteRepertoire repertoire;
    private LiveData<List<Note>> allNotes;

    public MyViewModel(Application application) {
        super(application);
        repertoire = new NoteRepertoire(application);
        allNotes = repertoire.getAllNotes();
    }

    public void insert(Note note) {
        repertoire.insert(note);
    }

    public void delete(Note note) {
        repertoire.delete(note);
    }

    public void update(Note note) {
        repertoire.update(note);
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
