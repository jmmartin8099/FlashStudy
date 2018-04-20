package com.CCC.jmmartin.flashstudy;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private boolean mShowDefThenTerm;

    private Button mCardDisplay;
    private Button mHelp;
    private Button mCloseHelp;
    private ImageButton mShuffle;
    private ImageButton mChangeOrder;
    private ImageButton mEditCard;
    private ImageButton mHome;
    private TextView mSetNameDisplay;
    private TextView mDisplayTermOrDef;
    private FrameLayout mHelpLayout;

    private LinearLayout mEditCardDisplay;
    private EditText mEditTerm;
    private EditText mEditDef;
    private Button mEditCancel;
    private Button mEditUpdate;

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
        mSetNameDisplay = (TextView) findViewById(R.id.display_set_name);
        mDisplayTermOrDef = (TextView) findViewById(R.id.text_term_def);

        mHelpLayout = (FrameLayout) findViewById(R.id.layout_help);
        mHelp = (Button) findViewById(R.id.button_help);
        mCloseHelp = (Button) findViewById(R.id.button_close_help);

        mShuffle = (ImageButton) findViewById(R.id.text_shuffle);
        mChangeOrder = (ImageButton) findViewById(R.id.button_switch);

        mEditCard = (ImageButton) findViewById(R.id.button_edit);
        mEditCardDisplay = (LinearLayout) findViewById(R.id.study_edit_card);
        mEditTerm = (EditText) findViewById(R.id.study_edit_term);
        mEditDef = (EditText) findViewById(R.id.study_edit_def);
        mEditCancel = (Button) findViewById(R.id.study_edit_cancel);
        mEditUpdate = (Button) findViewById(R.id.study_edit_update);

        mHome = (ImageButton) findViewById(R.id.button_home);

        // Display the first card
        mCurrCard = mCards.get(mCurrPos);
        mCardDisplay.setText(mCurrCard.getDef());
        mSetNameDisplay.setText(mSetName);
        mShowsTerm = false;
        mShowDefThenTerm = true;

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

                if (mShowDefThenTerm) {
                    mCurrCard = mCards.get(mCurrPos);
                    mCardDisplay.setText(mCurrCard.getDef());
                    mDisplayTermOrDef.setText("Definition");
                    mShowsTerm = false;
                }
                else{
                    mCurrCard = mCards.get(mCurrPos);
                    mCardDisplay.setText(mCurrCard.getTerm());
                    mDisplayTermOrDef.setText("Term");
                    mShowsTerm = true;
                }
            }
            @Override
            public void onSwipeLeft() {
                if (mCurrPos == 0)
                    mCurrPos = mSize - 1;
                else
                    mCurrPos--;

                if (mShowDefThenTerm) {
                    mCurrCard = mCards.get(mCurrPos);
                    mCardDisplay.setText(mCurrCard.getDef());
                    mDisplayTermOrDef.setText("Definition");
                    mShowsTerm = false;
                }
                else {
                    mCurrCard = mCards.get(mCurrPos);
                    mCardDisplay.setText(mCurrCard.getTerm());
                    mDisplayTermOrDef.setText("Term");
                    mShowsTerm = true;
                }
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
                Toast.makeText(getApplicationContext(),"Deck Shuffled!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mChangeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShowDefThenTerm) {
                    mShowDefThenTerm = false;
                    Toast.makeText(getApplicationContext(), "Showing Term First",
                            Toast.LENGTH_SHORT).show();
                    mCardDisplay.setText(mCurrCard.getTerm());
                    mDisplayTermOrDef.setText("Term");
                    mShowsTerm = true;
                }
                else {
                    mShowDefThenTerm = true;
                    Toast.makeText(getApplicationContext(), "Showing Definition First",
                            Toast.LENGTH_SHORT).show();
                    mCardDisplay.setText(mCurrCard.getDef());
                    mDisplayTermOrDef.setText("Definition");
                    mShowsTerm = false;
                }
            }
        });

        mEditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditCardDisplay.setVisibility(View.VISIBLE);
                mEditTerm.setText(mCurrCard.getTerm());
                mEditDef.setText(mCurrCard.getDef());
            }
        });

        mEditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditCardDisplay.setVisibility(View.GONE);
            }
        });

        mEditUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String termInput = mEditTerm.getText().toString(),
                        defInput = mEditDef.getText().toString();

                if (termInput.equals(mCurrCard.getTerm()) && defInput.equals(mCurrCard.getDef())){
                    mEditCardDisplay.setVisibility(View.GONE);
                    createToast("No Changes Made");
                }
                else{
                    db.deleteCard(mSetName,mCurrCard.getTerm());
                    IndexCard newCard = new IndexCard(termInput,defInput);
                    db.addCard(mSetName,newCard);
                    mCurrCard = newCard;
                    mCards.remove(mCurrPos);
                    mCards.add(newCard);
                    if (mShowDefThenTerm) {
                        mCardDisplay.setText(mCurrCard.getDef());
                        mDisplayTermOrDef.setText("Definition");
                    }
                    else {
                        mCardDisplay.setText(mCurrCard.getTerm());
                        mDisplayTermOrDef.setText("Term");
                    }
                    mEditCardDisplay.setVisibility(View.GONE);
                    createToast("Card Updated");
                }
            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudyActivity.super.onBackPressed();
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

    private void createToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
