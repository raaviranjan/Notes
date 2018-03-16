package com.example.ravi.isnotes;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import com.example.ravi.isnotes.database.DatabaseHandler;
import com.example.ravi.isnotes.database.models.Note;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by Ravi on 01-Jul-17.
 */

public class EditNoteActivity extends AppCompatActivity {
    EditText titleEditTextView, descriptionEditTextView;
    String title,description;
    int noteId;
    Boolean isUpdateMode;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView2);
        //banner ad
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("50F7BD1535D4AA905405E185DB25BBB7").build();
        mAdView.loadAd(adRequest);

        isUpdateMode=false;

        //to change font to avenir
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/avenir roman.otf");


        titleEditTextView = (EditText)this.findViewById(R.id.titleEditText);
        descriptionEditTextView = (EditText)this.findViewById(R.id.descriptionEditText);

        //font of textviews changed
        titleEditTextView.setTypeface(custom_font);
        descriptionEditTextView.setTypeface(custom_font);

        //to add back button on toolbar
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        
        Bundle bundle=getIntent().getExtras();

        //handle the event either we have to edit current note or add new note
        if(bundle != null && bundle.containsKey("source1")) {
            if(bundle.getString("source1").equalsIgnoreCase("editPress")) {
                isUpdateMode=true;
                noteId=bundle.getInt("noteId1");
                titleEditTextView.setText(bundle.getString("noteTitle1"));
                descriptionEditTextView.setText(bundle.getString("noteDescription1"));
            } else if (bundle.getString("source1").equalsIgnoreCase("addPress")) {
                isUpdateMode=false;
            } else {
                Toast.makeText(this, "Invalid Arguments", Toast.LENGTH_LONG).show();
                super.onBackPressed();
            }
        }
    }
    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //on back button pressed
        if(id==android.R.id.home)
            finish();

        //on save button pressed
        if (id == R.id.action_save) {
            saveNote();

        }
        return super.onOptionsItemSelected(item);
    }
    private void saveNote(){
        title=titleEditTextView.getText().toString();
        description=descriptionEditTextView.getText().toString();

        if(!isValidNote()){
            return;
        }
        Note note = new Note();
        note.setDescription(description);
        note.setTitle(title);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        if(!isUpdateMode){
            databaseHandler.addNote(note);
            Toast.makeText(this, "Note Added Successfully", Toast.LENGTH_SHORT).show();
        } else {
            note.setId(noteId);
            databaseHandler.updateNote(note);
            Toast.makeText(this, "Note Updated Successfully", Toast.LENGTH_SHORT).show();
        }

        List<Note> notes = databaseHandler.getAllNotes();
        MainActivity.notesAdapter.clear();
        MainActivity.notesAdapter.addAll(notes);
        MainActivity.notesAdapter.notifyDataSetChanged();

        Intent i=new Intent(EditNoteActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
    public Boolean isValidNote(){
        if(title.isEmpty() && description.isEmpty()) {
            Toast.makeText(this, "Please Enter Title and Description", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if( title.isEmpty()) {
            Toast.makeText(this, "Please Enter Title", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(description.isEmpty()){
            Toast.makeText(this, "Please Enter Description", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

}
