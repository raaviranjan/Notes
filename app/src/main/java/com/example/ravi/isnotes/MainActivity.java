package com.example.ravi.isnotes;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import java.util.List;
import com.example.ravi.isnotes.adapter.NotesAdapter;
import com.example.ravi.isnotes.database.DatabaseHandler;
import com.example.ravi.isnotes.database.models.Note;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button addNote;
    public static NotesAdapter notesAdapter;
    DatabaseHandler databaseHandler;
    List<Note> noteList;
    ListView noteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //to change font to avenir
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/avenir roman.otf");

        addNote=(Button) findViewById(R.id.addNote);
        addNote.setOnClickListener(this);

        //font of addnote button changed
        addNote.setTypeface(custom_font);

        //getting the list of notes
        databaseHandler = new DatabaseHandler(this);
        noteList = databaseHandler.getAllNotes();

        notesAdapter = new NotesAdapter(this, noteList);
        noteListView = (ListView)findViewById(R.id.ListViewNotes);
        try{
            noteListView.setAdapter(notesAdapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNote:
                //perform following task when addNote button is clicked
                Intent myIntent = new Intent(MainActivity.this, EditNoteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("source1", "addPress");

                myIntent.putExtras(bundle);

                startActivity(myIntent);
                break;
            default:
                break;
        }
    }

}
