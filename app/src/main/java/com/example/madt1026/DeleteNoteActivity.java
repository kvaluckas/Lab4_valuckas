package com.example.madt1026;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
//vis crashindavo su spinneriu,del to dariau su listview
public class DeleteNoteActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> noteList;
    Button btnBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        listView = findViewById(R.id.listView_notes);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        noteList = new ArrayList<>(getListOfNotes());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener((parent, view, position, id) -> {
            String noteToRemove = noteList.get(position);
            removeNote(noteToRemove);
            noteList.remove(position);
            adapter.notifyDataSetChanged();
        });

        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(DeleteNoteActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private Set<String> getListOfNotes() {
        SharedPreferences sharedPref = getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);
        return sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, new HashSet<>());
    }

    private void removeNote(String noteContentToRemove) {
        SharedPreferences sharedPref = getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, new HashSet<>());
        Set<String> updatedSet = new HashSet<>(savedSet);

        if (updatedSet.contains(noteContentToRemove)) {
            updatedSet.remove(noteContentToRemove);
            editor.putStringSet(Constants.NOTES_ARRAY_KEY, updatedSet);
            editor.apply();
        }
    }
}
