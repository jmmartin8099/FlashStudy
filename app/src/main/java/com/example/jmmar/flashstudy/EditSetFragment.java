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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditSetFragment extends Fragment {
    public static final String TAG = "EditSetFragment";
    public static final String FRAG_EDIT_CARD = "EditCardFragment";

    private static DBHelper db;
    private static FragmentManager mFragmentManager;

    private ListView mCardsList;
    private ArrayList<IndexCard> mCardsArr;
    private String[] mCardsData;
    private int mSize;

    private static IndexCard mCard;

    public EditSetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_set, container, false);

        db = MainActivity.getDB();
        mFragmentManager = MainActivity.getFragManager();

        mCardsArr = new ArrayList<>(db.numCardsInSet(ChooseSetFragment.getSetName()));

        // Get cards from database
        Cursor cursor = db.getCards(ChooseSetFragment.getSetName());
        int setIndex = cursor.getColumnIndex(DBHelper.CARDS_COLUMN_SETNAME),
                termIndex = cursor.getColumnIndex(DBHelper.CARDS_COLUMN_TERM),
                defIndex = cursor.getColumnIndex(DBHelper.CARDS_COLUMN_DEFINITION);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            IndexCard temp = new IndexCard(cursor.getString(setIndex)
                    ,cursor.getString(termIndex),cursor.getString(defIndex));
            mCardsArr.add(temp);
            cursor.moveToNext();
        }
        mCardsArr.remove(0);
        mSize = mCardsArr.size();
        mCardsData = new String[mSize];

        // Put data from cards into String[] for ListView
        for (int i = 0;i<mSize;i++){
            mCardsData[i] = mCardsArr.get(i).toString();
        }

        mCardsList = (ListView) v.findViewById(R.id.list_view_cards);
        mCardsList.setAdapter(new ArrayAdapter<String>(this.getContext()
                ,android.R.layout.simple_list_item_1,mCardsData));
        mCardsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                //Get Card info
                mCard = mCardsArr.get(position);

                mFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                        new EditCardFragment(),FRAG_EDIT_CARD).commit();
            }
        });

        return v;
    }

    public static IndexCard getChosenCard(){
        return mCard;
    }
}
