package com.hfad.learnmachinelearning;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class IndividualContentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_content);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Topic Name");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override

                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_intuition:
                                Toast.makeText(IndividualContentActivity.this, "Yeay1", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.action_example:
                                Toast.makeText(IndividualContentActivity.this, "Yeay2", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.action_math:
                                Toast.makeText(IndividualContentActivity.this, "Yeay3", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.action_code:
                                Toast.makeText(IndividualContentActivity.this, "Yeay4", Toast.LENGTH_SHORT).show();
                                break;

                            /*case R.id.action_discussion:
                                Toast.makeText(IndividualContentActivity.this, "Yeay4", Toast.LENGTH_SHORT).show();
                                break;*/
                        }
                        return true;
                    }
                });
    }
}
