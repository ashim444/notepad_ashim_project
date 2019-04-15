package com.example.ashimghimire.notepad.config;

import android.content.Context;

import com.example.ashimghimire.notepad.model.Note;
import com.example.ashimghimire.notepad.local.NoteDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class AppNoteDataBase extends RoomDatabase {


    public abstract NoteDao noteDao();
    private static AppNoteDataBase appDBInstance;

    public static synchronized AppNoteDataBase getDBInstance(Context c) {
        if (appDBInstance == null) {
            appDBInstance = Room.databaseBuilder(c.getApplicationContext(), AppNoteDataBase.class,
                    "database_note").fallbackToDestructiveMigration().addCallback(firstEntityCall).build();
        }
        return appDBInstance;
    }

    /**
     *
     */
    private static RoomDatabase.Callback firstEntityCall = new RoomDatabase.Callback() {

        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            Thread t1 = new Thread(new FillInBegin(appDBInstance));
            t1.start();
        }
    };

    /**
     *
     */

    private static class FillInBegin implements Runnable {
        private NoteDao notedao;

        private FillInBegin(AppNoteDataBase appDB) {
            this.notedao = appDB.noteDao();
        }
        @Override
        public void run() {

        }
    }

}
