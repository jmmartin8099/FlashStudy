package com.example.jmmar.flashstudy;

import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class StudyActivity extends AppCompatActivity {
    private static DBHelper db;
    private static FragmentManager mFragmentManager;

    private ArrayList<IndexCard> mCards;
    private IndexCard mCurrCard;
    private int mCurrPos;
    private int mSize;
    private boolean mShowsTerm;

    private Button mCardDisplay;
    private Button mNextCard;
    private Button mPrevCard;
    private TextView mSetNameDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        db = MainActivity.getDB();
        mFragmentManager = MainActivity.getFragManager();

        mCards = new ArrayList<>();

        Cursor cursor = db.getCards(ChooseSetFragment.getSetName());
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

        // Remove the first card from the set that holds the values,
        // "Setname","set","set"
        int firstCard = 0;
        mCards.remove(firstCard);

        mCurrPos = 0;
        mSize = mCards.size();

        mCardDisplay = (Button) findViewById(R.id.button_flash_card);
        mNextCard = (Button) findViewById(R.id.button_next);
        mPrevCard = (Button) findViewById(R.id.button_previous);
        mSetNameDisplay = (TextView) findViewById(R.id.display_set_name);

        // Display the first card
        mCurrCard = mCards.get(mCurrPos);
        mCardDisplay.setText(mCurrCard.getDef());
        mSetNameDisplay.setText(mCurrCard.getSetname());
        mShowsTerm = false;

        // Set onClickListener to flip the card
        mCardDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mShowsTerm){
                    mCardDisplay.setText(mCurrCard.getTerm());
                    mShowsTerm = true;
                }
                else{
                    mCardDisplay.setText(mCurrCard.getDef());
                    mShowsTerm = false;
                }
            }
        });

        // Set onClickListener to move to the next card
        mNextCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrPos == mSize - 1)
                    mCurrPos = 0;
                else
                    mCurrPos++;

                mCurrCard = mCards.get(mCurrPos);
                mCardDisplay.setText(mCurrCard.getDef());
                mShowsTerm = false;
            }
        });

        // Set onClickListener to move to the previous card
        mPrevCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrPos == 0)
                    mCurrPos = mSize - 1;
                else
                    mCurrPos--;

                mCurrCard = mCards.get(mCurrPos);
                mCardDisplay.setText(mCurrCard.getDef());
                mShowsTerm = false;
            }
        });
    }
}
