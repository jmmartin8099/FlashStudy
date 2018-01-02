package com.example.jmmar.flashstudy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudyFragment extends Fragment {
    private static DBHelper db;

    private ArrayList<IndexCard> mCards;
    private IndexCard mCurrCard;
    private int mCurrPos;
    private int mSize;
    private boolean mShowsTerm;

    private Button mCardDisplay;
    private Button mNextCard;
    private Button mPrevCard;
    private TextView mSetNameDisplay;

    public StudyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_study, container, false);

        db = MainActivity.getDB();
        mCards = ChooseSetFragment.getSetOfCards();
        mCurrPos = 0;
        mSize = mCards.size();

        mCardDisplay = (Button) v.findViewById(R.id.button_flash_card);
        mNextCard = (Button) v.findViewById(R.id.button_next);
        mPrevCard = (Button) v.findViewById(R.id.button_previous);
        mSetNameDisplay = (TextView) v.findViewById(R.id.display_set_name);

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
        return v;
    }



}
