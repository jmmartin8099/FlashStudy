package com.CCC.jmmartin.flashstudy;

import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class StudyActivity extends AppCompatActivity {
    private DBHelper db;
    private FragmentManager mFragmentManager;

    private String mSetName;

    private ArrayList<IndexCard> mCards;
    private IndexCard mCurrCard;
    private int mCurrPos;
    private int mSize;
    private boolean mShowsTerm;

    private Button mCardDisplay;
    private Button mHelp;
    private Button mCloseHelp;
    private TextView mShuffle;
    private TextView mSetNameDisplay;
    private TextView mDisplayTermOrDef;
    private FrameLayout mHelpLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        db = MainActivity.getDB();

        if(getIntent().hasExtra("setName"))
            mSetName = (String) getIntent().getSerializableExtra("setName");

        mCards = new ArrayList<>();

        Cursor cursor = db.getCards(mSetName);
        int termIndex = cursor.getColumnIndex(DBHelper.CARDS_COLUMN_TERM),
                defIndex = cursor.getColumnIndex(DBHelper.CARDS_COLUMN_DEFINITION);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            IndexCard temp = new IndexCard(cursor.getString(termIndex),cursor.getString(defIndex));
            mCards.add(temp);
            cursor.moveToNext();
        }

        mCurrPos = 0;
        mSize = mCards.size();

        mCardDisplay = (Button) findViewById(R.id.button_flash_card);
        mHelp = (Button) findViewById(R.id.button_help);
        mCloseHelp = (Button) findViewById(R.id.button_close_help);
        mShuffle = (TextView) findViewById(R.id.text_shuffle);
        mSetNameDisplay = (TextView) findViewById(R.id.display_set_name);
        mDisplayTermOrDef = (TextView) findViewById(R.id.text_term_def);
        mHelpLayout = (FrameLayout) findViewById(R.id.layout_help);

        // Display the first card
        mCurrCard = mCards.get(mCurrPos);
        mCardDisplay.setText(mCurrCard.getDef());
        mSetNameDisplay.setText(mSetName);
        mShowsTerm = false;

        // Set onClickListener to flip the card
        mCardDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mShowsTerm){
                    mCardDisplay.setText(mCurrCard.getTerm());
                    mDisplayTermOrDef.setText("Term");
                    mShowsTerm = true;
                }
                else{
                    mCardDisplay.setText(mCurrCard.getDef());
                    mDisplayTermOrDef.setText("Definition");
                    mShowsTerm = false;
                }
            }
        });

        // Set onTouchListener to go to next or previous card by swiping
        mCardDisplay.setOnTouchListener(new OnSwipeTouchListener(this.getApplicationContext()){
            @Override
            public void onFlip(){
                if (!mShowsTerm){
                    mCardDisplay.setText(mCurrCard.getTerm());
                    mDisplayTermOrDef.setText("Term");
                    mShowsTerm = true;
                }
                else{
                    mCardDisplay.setText(mCurrCard.getDef());
                    mDisplayTermOrDef.setText("Definition");
                    mShowsTerm = false;
                }
            }
            @Override
            public void onSwipeRight() {
                if (mCurrPos == mSize - 1)
                    mCurrPos = 0;
                else
                    mCurrPos++;

                mCurrCard = mCards.get(mCurrPos);
                mCardDisplay.setText(mCurrCard.getDef());
                mDisplayTermOrDef.setText("Definition");
                mShowsTerm = false;
            }
            @Override
            public void onSwipeLeft() {
                if (mCurrPos == 0)
                    mCurrPos = mSize - 1;
                else
                    mCurrPos--;

                mCurrCard = mCards.get(mCurrPos);
                mCardDisplay.setText(mCurrCard.getDef());
                mDisplayTermOrDef.setText("Definition");
                mShowsTerm = false;
            }
        });

        mShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffleCards();
                mCurrPos = 0;
                mCurrCard = mCards.get(mCurrPos);
                mCardDisplay.setText(mCurrCard.getDef());
                mDisplayTermOrDef.setText("Definition");
                mShowsTerm = false;
            }
        });

        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelpLayout.setVisibility(View.VISIBLE);
            }
        });

        mCloseHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelpLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void shuffleCards(){
        ArrayList<IndexCard> newList = new ArrayList<>(mSize);
        int size = mSize,
                origSize = mSize,
                origPos;
        Random gen = new Random();

        for (origPos = 0;origPos<origSize;origPos++){
            int num = gen.nextInt(size);
            newList.add(mCards.get(num));
            mCards.remove(num);
            size--;
        }
        mCards = newList;
    }
}
