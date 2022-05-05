package uk.ac.abertay.cmp309_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashSet;

public class notesEditorActivity extends AppCompatActivity {

    int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);

        EditText noteEditText = (EditText) findViewById(R.id.noteEditText);

        Intent editorIntent = getIntent();
        noteID = editorIntent.getIntExtra("noteID", -1);

        if(noteID != -1){
            noteEditText.setText(notesActivity.notes.get(noteID));
        }
        else{
            notesActivity.notes.add("");
            noteID = notesActivity.notes.size() -1;
            notesActivity.arrayAdapter.notifyDataSetChanged();
        }

        noteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                notesActivity.notes.set(noteID, String.valueOf(s));
                notesActivity.arrayAdapter.notifyDataSetChanged();

                //Save notes to shared perfs
                SharedPreferences sharedPerfs = getApplicationContext().getSharedPreferences("uk.ac.abertay.cmp309_app", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(notesActivity.notes);
                sharedPerfs.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
