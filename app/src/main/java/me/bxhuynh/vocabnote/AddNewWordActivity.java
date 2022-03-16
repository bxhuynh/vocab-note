package me.bxhuynh.vocabnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewWordActivity extends AppCompatActivity {
    EditText etWord, etSoundLike, etMeaning;
    CheckBox cbAddToStudy;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_word);

        etWord = findViewById(R.id.et_word);
        etSoundLike = findViewById(R.id.et_soundLike);
        etMeaning = findViewById(R.id.et_meaning);
        cbAddToStudy = findViewById(R.id.cb_addToStudying);

        dbHandler = new DBHandler(AddNewWordActivity.this);

    }

    public void onAdd(View v) {
        String word = etWord.getText().toString();
        String soundlike = etSoundLike.getText().toString();
        String meaning = etMeaning.getText().toString();
        int isAddedToStudy = 1;
        if (!cbAddToStudy.isChecked()) isAddedToStudy = 0;

        if (word.isEmpty() || soundlike.isEmpty() || meaning.isEmpty()) {
            Toast.makeText(AddNewWordActivity.this, "Please enter all the data...", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHandler.addNewWord(word, soundlike, meaning, isAddedToStudy);
        Toast.makeText(AddNewWordActivity.this, "Word has been added", Toast.LENGTH_LONG).show();
        etWord.setText("");
        etSoundLike.setText("");
        etMeaning.setText("");
        cbAddToStudy.setChecked(true);

    }

    public void onClickCancel(View v){
        finish();
    }
}