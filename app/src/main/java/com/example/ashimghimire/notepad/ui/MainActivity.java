package com.example.ashimghimire.notepad.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ashimghimire.notepad.viewmodel.MyViewModel;
import com.example.ashimghimire.notepad.model.Note;
import com.example.ashimghimire.notepad.adapter.NoteAdapter;
import com.example.ashimghimire.notepad.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.onDeleteListener, View.OnClickListener {
    //AppNoteDataBase appNoteDataBase;
    public static final int EDIT_NOTE_REQ = 2;
    public static MyViewModel viewObj;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //appNoteDataBase = Room.databaseBuilder(getApplicationContext(),
        //                                AppNoteDataBase.class, "table_note").build();
        findView();
        RecyclerView recyclerView = findViewById(R.id.recyView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter theAdapter = new NoteAdapter(this);
        recyclerView.setAdapter(theAdapter);
        viewObj = ViewModelProviders.of(this).get(MyViewModel.class);
        viewObj.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                theAdapter.setNotes(notes);
            }
        });

        theAdapter.setOnItemClickListner(new NoteAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra(EditorActivity.EXTRA_ID, note.getNoteId());
                intent.putExtra(EditorActivity.EXTRA_TITLE, note.getNoteTitle());
                intent.putExtra(EditorActivity.EXTRA_PRIORITY, note.getNotePriority());
                intent.putExtra(EditorActivity.EXTRA_SUBJECT, note.getNoteSubject());
                intent.putExtra(EditorActivity.EXTRA_DESCRIPTION, note.getNoteDescription());
                startActivityForResult(intent, EDIT_NOTE_REQ);
            }
        });
    }


    private void findView() {
        findViewById(R.id.addNoteBtn).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_NOTE_REQ && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EditorActivity.EXTRA_ID, -1);
            if (id == -1) {
                return;
            }
            String title = data.getStringExtra(EditorActivity.EXTRA_TITLE);
            String description = data.getStringExtra(EditorActivity.EXTRA_DESCRIPTION);
            String sub = data.getStringExtra(EditorActivity.EXTRA_SUBJECT);
            int priority = data.getIntExtra(EditorActivity.EXTRA_PRIORITY, 1);
            Note note = new Note(title, sub, priority, description);
            note.setNoteId(id);
            viewObj.update(note);
        }
    }

    @Override
    public void delete(Note myNote) {
        Toast.makeText(MainActivity.this, R.string.delete, Toast.LENGTH_LONG).show();
        viewObj.delete(myNote);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNoteBtn:
                startActivity(new Intent(MainActivity.this, EditorActivity.class));
                break;
        }
    }
}
