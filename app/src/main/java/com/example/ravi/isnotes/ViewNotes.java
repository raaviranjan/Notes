package com.example.ravi.isnotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ravi.isnotes.database.DatabaseHandler;
import com.example.ravi.isnotes.database.models.Note;

import java.util.List;

/**
 * Created by Ravi on 25-Feb-18.
 */

public class ViewNotes extends AppCompatActivity {
    TextView viewTitle,viewDescription;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_note);

        //to change font to avenir
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/avenir roman.otf");

        viewTitle=(TextView)findViewById(R.id.viewTitle);
        viewDescription=(TextView)findViewById(R.id.viewDescription);

        //font of textviews changed
        viewTitle.setTypeface(custom_font);
        viewDescription.setTypeface(custom_font);

        //to add back button on toolbar
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //display notes
        bundle=getIntent().getExtras();
        if(bundle.getString("source").equalsIgnoreCase("viewPress")) {
            viewTitle.setText(bundle.getString("noteTitle"));
            viewDescription.setText(bundle.getString("noteDescription"));
        }
    }
    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //on back pressed
        if(id==android.R.id.home)
            finish();

        //on edit pressed
        if (id == R.id.action_edit) {
            Intent myIntent = new Intent(ViewNotes.this, EditNoteActivity.class);
            Bundle bundle1 = new Bundle();

            bundle1.putString("source1", "editPress");
            bundle1.putString("noteTitle1", bundle.getString("noteTitle"));
            bundle1.putString("noteDescription1", bundle.getString("noteDescription"));
            bundle1.putInt("noteId1", bundle.getInt("noteId"));

            myIntent.putExtras(bundle1);

            startActivity(myIntent);
            finish();
        }

        //on delete pressed
        if (id == R.id.action_delete) {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Delete Note").setMessage("Are you sure to delete this note?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            DatabaseHandler databaseHandler = new DatabaseHandler(ViewNotes.this);
                            databaseHandler.deleteNote(String.valueOf(bundle.getInt("noteId")));
                            List<Note> notes = databaseHandler.getAllNotes();
                            MainActivity.notesAdapter.clear();
                            MainActivity.notesAdapter.addAll(notes);
                            MainActivity.notesAdapter.notifyDataSetChanged();

                            Intent ii=new Intent(ViewNotes.this,MainActivity.class);
                            startActivity(ii);
                            finish();
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}
