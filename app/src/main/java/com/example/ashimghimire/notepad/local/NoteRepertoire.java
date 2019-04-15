package com.example.ashimghimire.notepad.local;

import android.app.Application;

import com.example.ashimghimire.notepad.config.AppNoteDataBase;
import com.example.ashimghimire.notepad.model.Note;
import com.example.ashimghimire.notepad.local.NoteDao;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepertoire {
    private NoteDao notedao;
    private LiveData<List<Note>> allNotes;

    public NoteRepertoire(Application application) {

        AppNoteDataBase database = AppNoteDataBase.getDBInstance(application);
        notedao = database.noteDao();
        allNotes = notedao.getAllNotes();
    }

    public void insert(Note note) {
        Thread t1 = new Thread(new InsertThreadClass(notedao, note));
        t1.start();
    }

    public void delete(Note note) {
        Thread t1 = new Thread(new DeleteThreadClass(notedao, note));
        t1.start();
    }

    public void update(Note note) {

        Thread t1 = new Thread(new UpdateThreadClass(notedao, note));
        t1.start();

    }

    public void deleteAll() {

        Thread t1 = new Thread(new DeleteAllNoteThreadClass(notedao));
        t1.start();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }


///////////////////////////Insert Thread

    private static class InsertThreadClass implements Runnable {
        private NoteDao notedao;
        private Note note;

        public InsertThreadClass(NoteDao notedao, Note note) {
            this.notedao = notedao;
            this.note = note;
        }

        @Override
        public void run() {
            notedao.insert(note);

        }
    }


    ///////////////////////////Update Thread
    private static class UpdateThreadClass implements Runnable {
        private NoteDao notedao;
        private Note note;

        public UpdateThreadClass(NoteDao notedao, Note note) {
            this.notedao = notedao;
            this.note = note;
        }

        @Override
        public void run() {
            notedao.update(note);

        }
    }


    ///////////////////////////Delete Thread
    private static class DeleteThreadClass implements Runnable {
        private NoteDao notedao;
        private Note note;

        public DeleteThreadClass(NoteDao notedao, Note note) {
            this.notedao = notedao;
            this.note = note;
        }

        @Override
        public void run() {
            notedao.delete(note);

        }
    }

    ///////////////////////////Delete All Note Thread
    private static class DeleteAllNoteThreadClass implements Runnable {
        private NoteDao notedao;


        public DeleteAllNoteThreadClass(NoteDao notedao) {
            this.notedao = notedao;

        }

        @Override
        public void run() {
            notedao.deleteAllNotes();

        }
    }
}