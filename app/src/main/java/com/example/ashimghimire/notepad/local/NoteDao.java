package com.example.ashimghimire.notepad.local;


import com.example.ashimghimire.notepad.model.Note;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {


    @Query("SELECT * FROM table_note ORDER BY  notePriority DESC ")
    LiveData<List<Note>> getAllNotes();

    @Query("DELETE FROM table_note")
    void deleteAllNotes();

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);


}
