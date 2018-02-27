package com.example.ravi.isnotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.ravi.isnotes.EditNoteActivity;
import com.example.ravi.isnotes.R;
import com.example.ravi.isnotes.ViewNotes;
import com.example.ravi.isnotes.database.models.Note;

/**
 * Created by Ravi on 01-Jul-17.
 */
public class NotesAdapter extends ArrayAdapter<Note> {

    private List<Note> noteList;
    private Context context;

    public NotesAdapter(Context context, List<Note> noteList) {
        super(context, R.layout.list_notes, noteList);
        this.noteList = noteList;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_notes, null);
        }

        final Note note = noteList.get(position);

        if (note != null) {
            TextView title = (TextView) v.findViewById(R.id.title);
            TextView description = (TextView) v.findViewById(R.id.description);
            TextView index = (TextView) v.findViewById(R.id.index);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/avenir roman.otf");
            title.setTypeface(custom_font);
            description.setTypeface(custom_font);
            index.setTypeface(custom_font);

            if (title != null) {
                title.setText(note.getTitle());
                index.setText((position + 1) + ".");
            }
            if (description != null) {
                description.setText(note.getDescription());
            }
        }

        //Click Listener
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(context, ViewNotes.class);
                Bundle bundle = new Bundle();

                Note note = noteList.get(position);

                bundle.putString("source", "viewPress");
                bundle.putString("noteTitle", note.getTitle());
                bundle.putString("noteDescription", note.getDescription());
                bundle.putInt("noteId", note.getId());

                i.putExtras(bundle);

                context.startActivity(i);
            }
        });

        return v;
    }
}
