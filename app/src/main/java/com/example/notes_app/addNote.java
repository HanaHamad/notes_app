package com.example.notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addNote extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText titleNote;
    EditText contentNote;
    ImageButton savebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleNote = findViewById(R.id.noteTittle);
        contentNote = findViewById(R.id.add_note);
        savebtn = findViewById(R.id.save_btn);
/*
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
*/
    }
    /*
    public void saveNote(){
        String noteTitle = titleNote.getText().toString();
        String noteContent = contentNote.getText().toString();
        if(noteTitle == null || noteTitle.isEmpty()){
            titleNote.setError("Title is required");
            return;
        }*/

    public void saveToFirebase(View v) {
        String noteTitle = titleNote.getText().toString();
        String noteContent = contentNote.getText().toString();
        Map<String, Object> note = new HashMap<>();
        if(!noteTitle.isEmpty() && !noteContent.isEmpty()) {
            note.put("title",noteTitle);
            note.put("content",noteContent);

            db.collection("Notes")
                    .add(note)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.e("Hanaa", "Data added successfully to database: ");
                            openSecondSecreen();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Hanaa", "Failed to add database", e);
                        }
                    });

        }else {
            Toast.makeText(this,"please fill feilds" ,Toast.LENGTH_SHORT).show();
        }
    }
    public  void  openSecondSecreen(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}