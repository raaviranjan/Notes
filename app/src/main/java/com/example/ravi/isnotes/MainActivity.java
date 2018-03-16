package com.example.ravi.isnotes;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import java.util.List;
import com.example.ravi.isnotes.adapter.NotesAdapter;
import com.example.ravi.isnotes.database.DatabaseHandler;
import com.example.ravi.isnotes.database.models.Note;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity{
    public static NotesAdapter notesAdapter;
    DatabaseHandler databaseHandler;
    List<Note> noteList;
    ListView noteListView;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        //banner ad
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("50F7BD1535D4AA905405E185DB25BBB7").build();
        mAdView.loadAd(adRequest);

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

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //on add pressed
        if (id == R.id.action_add){
            Intent myIntent = new Intent(MainActivity.this, EditNoteActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("source1", "addPress");

            myIntent.putExtras(bundle);

            startActivity(myIntent);
        }


        return super.onOptionsItemSelected(item);
    }


}
