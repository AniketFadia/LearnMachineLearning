package com.hfad.learnmachinelearning;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class IndividualContentActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Toolbar toolbar;
    public static final String EXTRA_MESSAGE = "message";
    private Menu menu;
    String topicTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_content);
        Intent intent = getIntent();
        topicTitle = intent.getStringExtra(EXTRA_MESSAGE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(topicTitle);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
        fragment = new IntroductionFragment();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override

                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_intuition:
                                fragment = new IntroductionFragment();
                                break;

                            case R.id.action_example:
                                fragment = new ExampleFragment();
                                break;

                            case R.id.action_math:
                                fragment = new MathFragment();
                                break;

                            case R.id.action_code:
                                fragment = new CodeFragment();
                                break;

                            /*default:
                                fragment = new IntroductionFragment();
                                break;*/
                        }
                        transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.main_container, fragment).commit();
                        return true;

                    }
                });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.individual_content_menu, menu);
        this.menu = menu;
        updateBookmarkIcon(getBookmarkValue());
        return true;
    }

    public void updateBookmarkIcon(int n) {
        if (n == 1) {
            menu.findItem(R.id.action_bookmark).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_bookmarked, null));
        }
        else if(n == 0){
            menu.findItem(R.id.action_bookmark).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_bookmark, null));
        }
    }

    public int getBookmarkValue() {
        int bookMark = 0;
        try {
            SQLiteOpenHelper mlDatabaseHelper = new MachineLearningDatabaseHelper(this);
            SQLiteDatabase db = mlDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("SUB_TOPICS",
                    new String[]{"BOOKMARK"},
                    "NAME = ?",
                    new String[]{topicTitle},
                    null, null, null);

            if (cursor.moveToFirst()) {
                bookMark = cursor.getInt(0);
            }
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        return bookMark;
    }

    public void bookmarkChanged(){
        int b = getBookmarkValue();
        ContentValues bmark = new ContentValues();
        bmark.put("BOOKMARK", 1 - b);
        SQLiteOpenHelper mlDatabaseHelper = new MachineLearningDatabaseHelper(this);
        SQLiteDatabase db = mlDatabaseHelper.getWritableDatabase();
        db.update("SUB_TOPICS", bmark, "NAME = ?",  new String[]{topicTitle});
        updateBookmarkIcon(1-b);
        db.close();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_bookmark) {
            bookmarkChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
