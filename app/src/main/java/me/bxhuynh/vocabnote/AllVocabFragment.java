package me.bxhuynh.vocabnote;

import android.content.Intent;
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
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllVocabFragment extends Fragment {
    private VocabListViewAdapter vocabListViewAdapter;
    private RecyclerView allVocabRecyclerView;
    private ArrayList<WordModal> wordModalArrayList;
    private DBHandler dbHandler;
    private SearchView searchView;

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

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                vocabListViewAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                vocabListViewAdapter.getFilter().filter(s);
                return false;
            }
        });

        //context menu register
        registerForContextMenu(allVocabRecyclerView);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ctx_add:
                vocabListViewAdapter.addToStudy();
                break;
            case R.id.ctx_edit:
                vocabListViewAdapter.editWord();
                break;
            case R.id.ctx_delete:
                vocabListViewAdapter.deleteItem();
                break;
        }

        return super.onContextItemSelected(item);
    }
}