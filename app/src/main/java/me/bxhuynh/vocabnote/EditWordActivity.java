package me.bxhuynh.vocabnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class EditWordActivity extends AppCompatActivity {
    private EditText edWord, edSoundlike, edMeaning;
    private CheckBox cbAdd;
    private String wordId;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        edWord = findViewById(R.id.et_editWord);
        edSoundlike = findViewById(R.id.et_editSoundLike);
        edMeaning = findViewById(R.id.et_editMeaning);
        cbAdd = findViewById(R.id.cb_editAddToStudying);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                finish();
            } else {
                edWord.setText(extras.getString("WORD"));
                edSoundlike.setText(extras.getString("SOUNDLIKE"));
                edMeaning.setText(extras.getString("MEANING"));
                Boolean isAdd = false;
                if (extras.getString("ISSTUDYING").equals("1")) {
                    isAdd = true;
                }
                cbAdd.setChecked(isAdd);
                wordId = extras.getString("WORD_ID");
            }
        } else {
            edWord.setText((String) savedInstanceState.getSerializable("WORD"));;
            edSoundlike.setText((String) savedInstanceState.getSerializable("SOUNDLIKE"));;
            edMeaning.setText((String) savedInstanceState.getSerializable("MEANING"));;
            Boolean isAdd = false;
            String isStudying = (String) savedInstanceState.getSerializable("ISSTUDYING");
            if (isStudying.equals("1")) {
                isAdd = true;
            }
            cbAdd.setChecked(isAdd);
            wordId = (String) savedInstanceState.getSerializable("WORD_ID");
        }

    }

    public void onSave(View v) {
        if (wordId.isEmpty()) {
            Toast.makeText(EditWordActivity.this, "Something goes wrong, please cancel and try again", Toast.LENGTH_LONG).show();
            return;
        } else {
            String word = edWord.getText().toString();
            String soundlike = edSoundlike.getText().toString();
            String meaning = edMeaning.getText().toString();
            int isAddedToStudy = 0;
            if (cbAdd.isChecked()) isAddedToStudy = 1;
            if (word.isEmpty() || soundlike.isEmpty() || meaning.isEmpty()) {
                Toast.makeText(EditWordActivity.this, "Please enter all the data...", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHandler = new DBHandler(EditWordActivity.this);
            dbHandler.updateWordById(wordId, word, soundlike, meaning, isAddedToStudy);
            Toast.makeText(EditWordActivity.this, "Word is updated", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(EditWordActivity.this, MainActivity.class);
            i.putExtra("fragmentToLoad", "ALL");
            onCancel(v);
        }
    }

    public void onCancel(View v) {
        try {
        Intent i = new Intent(EditWordActivity.this, MainActivity.class);
        i.putExtra("fragmentToLoad", "ALL");
        startActivity(i);
        } catch (Exception e) {
            Log.d("ERROR EDIT", e.getLocalizedMessage(), e);
        }
    }
}