package com.example.jmmar.flashstudy;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.jmmar.flashstudy.MainActivity.FRAG_CHOOSE_SET;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_SET_VIEW;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_SET_VIEW_ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseSetFragment extends Fragment {
    public static final String TAG = "ChooseSetFragment";

    private ListView mSetsListView;
    private ArrayList<String> mSetNames;
    private String []mSets;

    private static String sSetName;

    private DBHelper db;
    private static FragmentManager sFragmentManager;

    public ChooseSetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose_set, container, false);

        // Retrieve set names from database
        db = MainActivity.getDB();
        sFragmentManager = MainActivity.getFragManager();

        Cursor cursor = db.getSets();
        mSetNames = new ArrayList<>();

        // Check if database is empty
        if (cursor != null){
            int setnameIndex = cursor.getColumnIndex(DBHelper.SETS_COLUMN_SET_NAME);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                mSetNames.add(cursor.getString(setnameIndex));
                cursor.moveToNext();
            }
        }


        // Convert setNames into a String[]
        mSets = new String[mSetNames.size()];
        for (int i = 0;i<mSets.length;i++)
            mSets[i] = mSetNames.get(i);

        Arrays.sort(mSets);

        // Set the ListView to display the setnames
        // TODO: Create a layout to adjust the text inside the ListView
        mSetsListView = (ListView) v.findViewById(R.id.list_view_setnames);
        mSetsListView.setAdapter(new ArrayAdapter<String>(this.getContext()
                ,android.R.layout.simple_list_item_1,mSets));

        mSetsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                // Get Set Name
                sSetName = mSets[position];

                MainActivity.setCurrFragDisplayed(FRAG_SET_VIEW_ID);
                MainActivity.launchFragment(new SetViewFragment(),FRAG_SET_VIEW,FRAG_CHOOSE_SET);
            }
        });
        return v;
    }

    public static String getSetName(){
        return sSetName;
    }

    public void msg(String str){
        Log.i(MainActivity.TAG,str);
    }

    @Override
    public void onStart() {
        super.onStart();
        msg("ChooseSetFragment: onStart...");
    }

    @Override
    public void onStop() {
        super.onStop();
        msg("ChooseSetFragment: onStop...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        msg("ChooseSetFragment: onDestroy...");
    }

    @Override
    public void onPause() {
        super.onPause();
        msg("ChooseSetFragment: onPause...");
    }

    @Override
    public void onResume() {
        super.onResume();
        msg("ChooseSetFragment: onResume...");
    }

}
