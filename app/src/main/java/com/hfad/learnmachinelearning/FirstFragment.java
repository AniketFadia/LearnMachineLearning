package com.hfad.learnmachinelearning;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.content.Intent;


public class FirstFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int[] arr = {3, 4, 4, 3};


    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(int page,String title) {
        FirstFragment fragmentFirst = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    int getCount(int i){
        int count = 0;
        if(i == -1) return 0;
        for(int j = 0; j <= i; j++){
            count += arr[j];
        }
        return count;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        //title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> listView,
                                            View v,
                                            int position,
                                            long id) {

                        try{
                            SQLiteOpenHelper mlDatabaseHelper = new MachineLearningDatabaseHelper(getActivity());

                            db = mlDatabaseHelper.getReadableDatabase();

                            cursor = db.query("SUB_TOPICS", new String[] {"NAME"}, "_id = ?", new String[] {Integer.toString(getCount(page - 1) + (position) + 1)}, null, null, null);

                        } catch(SQLiteException e) {
                            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        String topicTitle = "";
                        if(cursor.moveToFirst()){
                            topicTitle = cursor.getString(0);
                        }

                        Intent intent = new Intent(getActivity(), IndividualContentActivity.class);
                        intent.putExtra(IndividualContentActivity.EXTRA_MESSAGE, topicTitle);
                        startActivity(intent);

                    }
        };


        ListView listView = (ListView) view.findViewById(R.id.subTopics);
       try {
            SQLiteOpenHelper mlDatabaseHelper = new MachineLearningDatabaseHelper(getActivity());

            db = mlDatabaseHelper.getReadableDatabase();

            cursor = db.query("SUB_TOPICS", new String[] {"_id","NAME"}, "MAIN_TOPIC_ID = ?", new String[] {Integer.toString(page)}, null, null, null);

            CursorAdapter listAdapter = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            listView.setAdapter(listAdapter);
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        listView.setOnItemClickListener(itemClickListener);
        return view;
    }
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

    /*public void onListItemClick(ListView listView,
                                View itemView,
                                int position,
                                long id) {
        Intent intent = new Intent(getActivity(), IndividualTopicActivity.class);
        //intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int)id);
        startActivity(intent);
    }*/
}