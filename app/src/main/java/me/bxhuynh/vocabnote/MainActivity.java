package me.bxhuynh.vocabnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    StudyingFragment studyingFragment = new StudyingFragment();
    StatisticsFragment statisticsFragment = new StatisticsFragment();
    AllVocabFragment allVocabFragment = new AllVocabFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.navigation_studying:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, studyingFragment).commit();
                        return true;

                    case R.id.navigation_all:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, allVocabFragment).commit();
                        return true;

                    case R.id.navigation_statistics:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, statisticsFragment).commit();
                        return true;
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_studying);
    }

}