package com.example.notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class editActivity extends AppCompatActivity implements Adapter.ItemClickListener,Adapter.ItemClickListener2{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView updateNote;
    ArrayList<Note> note;
    Adapter adapter;
    ImageView delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        note = new ArrayList<Note>();
        adapter = new Adapter(this, note, this, this);
        note = new ArrayList<Note>();
        delete = findViewById(R.id.delete);
        updateNote = findViewById(R.id.update);
    }
    public void update(final Note note) {

        updateNote = findViewById(R.id.update);

        db.collection("Notes")
                .document(note.getId()).update("content", updateNote.toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Hanaa", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Hanaa", "Error updating document", e);
                    }
                });
    }

    public void Delete(final Note user) {
        db.collection("Notes").document(user.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e("Hanaa", "deleted");
                        note.remove(user);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Hanaa", "fail");
                    }
                });
    }
    @Override
    public void onItemClick2(int position, String id) {
        update(note.get(position));

    }
    @Override
    public void onItemClick(int position, String id) {
        Delete(note.get(position));
    }

}