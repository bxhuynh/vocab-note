package me.bxhuynh.vocabnote;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class StatisticsFragment extends Fragment {
    PieChart pieChart;
    BarChart barChart;
    TextView tvTotal, tvStudying, tvStudied;
    DBHandler dbHandler;
    int total, st;
    ArrayList<StudiedWordsStatistic> studiedWordsStatistics;

    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        pieChart.startAnimation();
        barChart.startAnimation();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHandler = new DBHandler(getActivity());
        total = dbHandler.getCountWords(false);
        st = dbHandler.getCountWords(true);

        //first card
        tvTotal = view.findViewById(R.id.totalWordsValue);
        tvStudied = view.findViewById(R.id.studiedValue);
        tvStudying = view.findViewById(R.id.studyingValue);

        tvTotal.setText(String.valueOf(total));
        tvStudied.setText(String.valueOf(total - st));
        tvStudying.setText(String.valueOf(st));

        pieChart = view.findViewById(R.id.piechart);
        pieChart.addPieSlice(
                new PieModel(
                        "Studying",
                        st,
                        getResources().getColor(R.color.tertiary, getActivity().getTheme())));
        pieChart.addPieSlice(
                new PieModel(
                        "Studied",
                        total - st,
                        getResources().getColor(R.color.tertiary_dark, getActivity().getTheme()) ));
        pieChart.setInnerPadding(40);


        //second card
        barChart = view.findViewById(R.id.barchart);

        studiedWordsStatistics = new ArrayList<>();
        studiedWordsStatistics = dbHandler.getStatistics();

        for (int i = studiedWordsStatistics.size() - 1; i >= 0; i--){
            StudiedWordsStatistic st = studiedWordsStatistics.get(i);
            String legendLabel = st.getMonth() +"/" +st.getYear()%100;
            barChart.addBar(new BarModel(legendLabel, st.getNumberOfWords(), getResources().getColor(R.color.tertiary_dark, getActivity().getTheme())));
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHandler.close();
    }
}