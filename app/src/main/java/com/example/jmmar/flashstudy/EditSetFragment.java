package com.example.jmmar.flashstudy;


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

import java.util.ArrayList;

import static com.example.jmmar.flashstudy.MainActivity.FRAG_EDIT_CARD;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_EDIT_CARD_ID;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_EDIT_SET;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditSetFragment extends Fragment {
    public static final String TAG = "EditSetFragment";

    private DBHelper db;

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

        mCardsArr = new ArrayList<>();

        // Get cards from database
        Cursor cursor = db.getCards(ChooseSetFragment.getSetName());
        int termIndex = cursor.getColumnIndex(DBHelper.CARDS_COLUMN_TERM),
                defIndex = cursor.getColumnIndex(DBHelper.CARDS_COLUMN_DEFINITION);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            IndexCard temp = new IndexCard(cursor.getString(termIndex),cursor.getString(defIndex));
            mCardsArr.add(temp);
            cursor.moveToNext();
        }
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

                MainActivity.setCurrFragDisplayed(FRAG_EDIT_CARD_ID);
                MainActivity.launchFragment(new EditCardFragment(),FRAG_EDIT_CARD,FRAG_EDIT_SET);
            }
        });

        return v;
    }

    public static IndexCard getChosenCard(){
        return mCard;
    }

    public void msg(String str){
        Log.i(MainActivity.TAG,str);
    }

    @Override
    public void onStart() {
        super.onStart();
        msg("EditSetFragment: onStart...");
    }

    @Override
    public void onStop() {
        super.onStop();
        msg("EditSetFragment: onStop...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        msg("EditSetFragment: onDestroy...");
    }

    @Override
    public void onPause() {
        super.onPause();
        msg("EditSetFragment: onPause...");
    }

    @Override
    public void onResume() {
        super.onResume();
        msg("EditSetFragment: onResume...");
    }

}
