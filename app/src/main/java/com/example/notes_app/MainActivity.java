package com.example.notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.ItemClickListener, Adapter.ItemClickListener2{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Note> note;
    Adapter adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    RecyclerView recycler;
    ImageView delete;
    ImageView updateNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateNote = findViewById(R.id.update);
        recycler = findViewById(R.id.recycler);
        note = new ArrayList<Note>();
        adapter = new Adapter(this, note, this, this);
        delete = findViewById(R.id.delete);


        GetAllNots();
    }
    private void GetAllNots() {

        db.collection("Notes").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("Hanaa", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String id = documentSnapshot.getId();
                                    String title = documentSnapshot.getString("title");
                                    String content = documentSnapshot.getString("content");
                                    Note user = new Note(id, title , content);
                                    note.add(user);
                                    recycler.setLayoutManager(layoutManager);
                                    recycler.setHasFixedSize(true);
                                    recycler.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    Log.e("LogDATA", note.toString());
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("LogDATA", "get failed with ");


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

    @Override
    public void onItemClick(int position, String id) {
        Delete(note.get(position));
    }

    @Override
    public void onItemClick2(int position, String id) {
        update(note.get(position));

    }
}
