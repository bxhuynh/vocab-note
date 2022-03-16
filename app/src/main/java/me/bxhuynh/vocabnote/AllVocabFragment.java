package me.bxhuynh.vocabnote;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class AllVocabFragment extends Fragment {
    private VocabListViewAdapter vocabListViewAdapter;
    private RecyclerView allVocabRecyclerView;
    private ArrayList<WordModal> wordModalArrayList;
    private DBHandler dbHandler;

    public AllVocabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_vocab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get data from db
        wordModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(getActivity());
        wordModalArrayList = dbHandler.readWords(0);
        //init adapter n get recyclerview
        vocabListViewAdapter = new VocabListViewAdapter(wordModalArrayList, getActivity());
        allVocabRecyclerView = view.findViewById(R.id.allVocabRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        allVocabRecyclerView.setLayoutManager(linearLayoutManager);
        allVocabRecyclerView.setAdapter(vocabListViewAdapter);

        //context menu register
        registerForContextMenu(allVocabRecyclerView);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = - 1;
        try {
            position = vocabListViewAdapter.getPosition();
        } catch (Exception e)
        {
            Log.d("Error", e.getLocalizedMessage(), e);
        }

        switch (item.getItemId()) {
            case R.id.ctx_add:
                WordModal word = wordModalArrayList.get(position);
                dbHandler.updateWord(word.getWord(), word.getWord(), word.getSoundlike(), word.getMeaning(), 1);
                Toast.makeText(getActivity(), word.getWord() + " is added to study", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ctx_edit:
                Toast.makeText(getActivity(), "EDIT " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                break;
            case R.id.ctx_delete:
                Toast.makeText(getActivity(), "DELETE " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }
}