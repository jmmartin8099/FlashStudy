package com.example.jmmar.flashstudy;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseSetFragment extends Fragment {
    private ListView mSetsListView;
    private ArrayList<String> mSetNames;
    private String []mSets;

    private static ArrayList<IndexCard> mCards;

    private DBHelper db;
    private static FragmentManager mFragmentManager;

    public ChooseSetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose_set, container, false);

        // Retrieve set names from database
        db = MainActivity.getDB();
        mFragmentManager = MainActivity.getFragManager();

        Cursor cursor = db.getSetnames();
        mSetNames = new ArrayList<>();

        // Check if database is empty
        if (cursor != null){
            int setnameIndex = cursor.getColumnIndex(DBHelper.CARDS_COLUMN_SETNAME);
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

        // Set the ListView to display the setnames
        // TODO: Create a layout to adjust the text inside the ListView
        mSetsListView = (ListView) v.findViewById(R.id.list_view_setnames);
        mSetsListView.setAdapter(new ArrayAdapter<String>(this.getContext()
                ,android.R.layout.simple_list_item_1,mSets));

        mSetsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                // Get cards with chosen set name
                mCards = new ArrayList<IndexCard>();

                Cursor cursor = db.getCards(mSets[position]);
                int setIndex = cursor.getColumnIndex(DBHelper.CARDS_COLUMN_SETNAME),
                        termIndex = cursor.getColumnIndex(DBHelper.CARDS_COLUMN_TERM),
                        defIndex = cursor.getColumnIndex(DBHelper.CARDS_COLUMN_DEFINITION);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    IndexCard temp = new IndexCard(cursor.getString(setIndex)
                            ,cursor.getString(termIndex),cursor.getString(defIndex));
                    mCards.add(temp);
                    cursor.moveToNext();
                }

                // Launch StudyFragment
                mFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                        new StudyFragment(),MainActivity.FRAG_STUDY)
                        .addToBackStack(MainActivity.FRAG_MAIN).commit();
            }
        });
        return v;
    }

    public static ArrayList<IndexCard> getSetOfCards(){
        return mCards;
    }
}
