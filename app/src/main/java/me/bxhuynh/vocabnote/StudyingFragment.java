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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class StudyingFragment extends Fragment {
    FloatingActionButton fab;
    private ArrayList<WordModal> wordModalArrayList;
    private DBHandler dbHandler;
    private StudyingListViewAdapter studyingListViewAdapter;
    private RecyclerView studyingListView;

    public StudyingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_studying, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wordModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(getActivity());
        wordModalArrayList = dbHandler.readWords(1);

        studyingListViewAdapter = new StudyingListViewAdapter(wordModalArrayList, getActivity());
        studyingListView = view.findViewById(R.id.studyingListView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        studyingListView.setLayoutManager(linearLayoutManager);
        studyingListView.setAdapter(studyingListViewAdapter);

        fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddNewWordActivity.class);
                startActivity(i);
            }
        });
    }
}