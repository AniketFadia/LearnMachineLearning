package com.hfad.learnmachinelearning;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;
import android.database.sqlite.SQLiteException;
import android.content.Intent;

public class ExploreMachineLearning extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String tabHeading;
    private final List<String> mFragmentTitleList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_machine_learning);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Explore ML");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        try{
            SQLiteOpenHelper mlDatabaseHelper = new MachineLearningDatabaseHelper(this);
            SQLiteDatabase db = mlDatabaseHelper.getReadableDatabase();
            cursor = db.query("MAIN_TOPICS", new String[] {"NAME"}, null, null, null, null, null);

        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        while (cursor.moveToNext()) {
            String nameText = cursor.getString(0);
            mFragmentTitleList.add(nameText);
            //adapter.addFragment(new CommonFragment(), nameText);
        }
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 4;
        //private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            //return mFragmentList.get(position);
            switch (position) {
                case 0:
                    return FirstFragment.newInstance(position, mFragmentTitleList.get(0));
                case 1:
                    return FirstFragment.newInstance(position,mFragmentTitleList.get(1));
                case 2:
                    return FirstFragment.newInstance(position,mFragmentTitleList.get(2));
                case 3:
                    return FirstFragment.newInstance(position, mFragmentTitleList.get(3));
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
                    //mFragmentList.size();
        }

        /*public void setText(String myTabHeading){
            tabHeading = myTabHeading;
        }*/

        /*public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }*/

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
            //mFragmentTitleList.get(position);
        }
    }
     /*   public String getMyData(){
            return tabHeading;
    }*/
     public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.explore_ml_menu, menu);
         return true;
     }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_bookmark) {
            Intent intent = new Intent(this, BookmarkActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

