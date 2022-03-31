package me.bxhuynh.vocabnote;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseDBHandler {
    private FirebaseDatabase database;
    private DatabaseReference backupRef, topicRef;



    public FirebaseDBHandler(Context context) {
       database = FirebaseDatabase.getInstance();
       backupRef = database.getReference("backup");
       topicRef = database.getReference("topic");
    }

    public String uploadToCloud(Context context){
        DatabaseReference pushedKey = backupRef.push();
        DBHandler dbHandler = new DBHandler(context);
        ArrayList<WordModal> wordsList = dbHandler.readWords(0);
        pushedKey.setValue(wordsList);
        return pushedKey.getKey();
    }

    public void importFromCloud(Context context, String key) {
        backupRef.child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    DBHandler dbHandler = new DBHandler(context);
                    DataSnapshot dataSnapshot = task.getResult();
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        WordModal word = d.getValue(WordModal.class);
                        dbHandler.addNewWord(word.getWord(), word.getSoundlike(), word.getMeaning(), word.getIsStudying());
                    }
                }
            }
        });
    }
}

//-MzUbAVpsHlQE3bRoV-D

