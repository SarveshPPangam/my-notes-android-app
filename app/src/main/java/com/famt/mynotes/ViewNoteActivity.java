package com.famt.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;


public class ViewNoteActivity extends AppCompatActivity {

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_update);
        db = new DBHelper(this);
        EditText titleInput = findViewById(R.id.titleinput);
        EditText descriptionInput = findViewById(R.id.descriptioninput);
        MaterialButton updateBtn = findViewById(R.id.updatebtn);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            titleInput.setText(extras.getString("title"));
            descriptionInput.setText(extras.getString("description"));
        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                long createdTime = System.currentTimeMillis();
                int id = extras.getInt("id");
                Note note = new Note(id, title, description, createdTime);
                db.updateNote(note);

                Toast.makeText(getApplicationContext(), "Note updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ViewNoteActivity.this, MainActivity.class));


            }
        });


    }
}