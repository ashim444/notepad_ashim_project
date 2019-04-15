package com.example.ashimghimire.notepad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ashimghimire.notepad.model.Note;
import com.example.ashimghimire.notepad.R;

public class EditorActivity extends AppCompatActivity {

    public int getPriority() {
        return priority;
    }

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_PRIORITY = "priority";
    public static final String EXTRA_DESCRIPTION = "desc";
    public static final String EXTRA_SUBJECT = "sub";
    EditText titleText;
    EditText subjectText;
    EditText descText;
    Spinner prioritySpin;
    static  int priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        setViewParameters();
        createSpinner();
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            titleText.setText(intent.getStringExtra(EXTRA_TITLE));
            subjectText.setText(intent.getStringExtra(EXTRA_SUBJECT));
            descText.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            prioritySpin.setSelection(0);
        }
    }

    private void setViewParameters() {
        titleText               = findViewById(R.id.editor_title);
        subjectText             = findViewById(R.id.editor_subject);
        descText                = findViewById(R.id.editor_memo);
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    private void createSpinner() {
        prioritySpin = findViewById(R.id.editor_note_priority);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priorityArray, android.R.layout.simple_spinner_item);

        prioritySpin.setAdapter(adapter);
        prioritySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        setPriority(1);
                    case 1:
                        setPriority(2);
                    case 2:
                       setPriority(3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     *
     * @param menu
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveTheNote:
                saveTheNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     */

    private void saveTheNote() {

        String titleStr = titleText.getText().toString();
        String subjStr = subjectText.getText().toString();
        String desStr = descText.getText().toString();

        if (checkInputStrings(titleStr, subjStr, desStr)) {
            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                Intent data = new Intent();
                data.putExtra(EXTRA_TITLE, titleStr);
                data.putExtra(EXTRA_SUBJECT, subjStr);
                data.putExtra(EXTRA_DESCRIPTION, desStr);
                data.putExtra(EXTRA_PRIORITY, priority);
                data.putExtra(EXTRA_ID, id);
                setResult(RESULT_OK, data);
            } else {
                MainActivity.viewObj.insert(new Note(titleStr, subjStr, priority, desStr));
            }
            finish();
        }
    }

    private boolean checkInputStrings(String titleStr, String subjStr, String desStr) {
        String emptyString = "";
        if (titleStr.equals(emptyString) || subjStr.equals(emptyString) || desStr.equals(emptyString)) {
            Toast.makeText(this, R.string.emptyWrn, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
