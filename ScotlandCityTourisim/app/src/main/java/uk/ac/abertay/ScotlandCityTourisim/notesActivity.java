package uk.ac.abertay.cmp309_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashSet;

public class notesActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.create_note){
            Intent menuIntent = new Intent(getApplicationContext(),notesEditorActivity.class);
            startActivity(menuIntent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        ListView listview = (ListView) findViewById(R.id.listView);

        SharedPreferences sharedPerfs = getApplicationContext().getSharedPreferences("uk.ac.abertay.cmp309_app", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPerfs.getStringSet("notes", null);

        if(set != null){
            notes = new ArrayList<>(set);
        }


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);

        listview.setAdapter(arrayAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent editorIntent = new Intent(getApplicationContext(), notesEditorActivity.class);
                editorIntent.putExtra("noteID", i);
                startActivity(editorIntent);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int noteForDeletion = i;

                new AlertDialog.Builder(notesActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete note?")
                        .setMessage("Would you like to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                notes.remove(noteForDeletion);
                                arrayAdapter.notifyDataSetChanged();

                                //Save notes to shared perfs
                                SharedPreferences sharedPerfs = getApplicationContext().getSharedPreferences("uk.ac.abertay.cmp309_app", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet<>(notesActivity.notes);
                                sharedPerfs.edit().putStringSet("notes", set).apply();
                            }
                        }).setNegativeButton("No",null)
                        .show();
                return true;
            }
        });
    }
}
