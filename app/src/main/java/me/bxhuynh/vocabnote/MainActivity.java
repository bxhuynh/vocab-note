package me.bxhuynh.vocabnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    StudyingFragment studyingFragment = new StudyingFragment();
    StatisticsFragment statisticsFragment = new StatisticsFragment();
    AllVocabFragment allVocabFragment = new AllVocabFragment();
    CloudFragment cloudFragment = new CloudFragment();

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

                    case R.id.navigation_cloud:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, cloudFragment).commit();
                        return true;
                }
                return true;
            }
        });

        try {
            String intentFragment = "";
            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    intentFragment = extras.getString("fragmentToLoad");
                }
            } else {
                intentFragment = (String) savedInstanceState.getSerializable("fragmentToLoad");
            }
            switch (intentFragment) {
                case "ALL":
                    bottomNavigationView.setSelectedItemId(R.id.navigation_all);
                    break;
                case "STATISTICS":
                    bottomNavigationView.setSelectedItemId(R.id.navigation_statistics);
                    break;
                case "CLOUD":
                    bottomNavigationView.setSelectedItemId(R.id.navigation_cloud);
                    break;
                default:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_studying);
            }
        } catch (Exception e) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_studying);
        }

    }

}