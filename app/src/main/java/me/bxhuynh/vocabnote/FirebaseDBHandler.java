package me.bxhuynh.vocabnote;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDBHandler {
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    public FirebaseDBHandler() {
       database = FirebaseDatabase.getInstance();
       myRef = database.getReference("message");
        myRef.setValue("Hello, World!");

    }
}
