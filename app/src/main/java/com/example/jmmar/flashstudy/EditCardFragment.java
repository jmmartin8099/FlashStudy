package com.example.jmmar.flashstudy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.jmmar.flashstudy.MainActivity.FRAG_EDIT_CARD;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_EDIT_SET;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_MAIN;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_SET_VIEW;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditCardFragment extends Fragment {
    public static final String TAG = "EditCardFragment";

    private DBHelper db;
    private FragmentManager mFragmentManager;
    private String mSetName;

    private TextView mCurrTerm;
    private TextView mCurrDef;

    private EditText mTermInput;
    private EditText mDefInput;

    private Button mCancel;
    private Button mSave;
    private Button mDeleteCard;

    private static IndexCard sCard;

    public EditCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_card, container, false);

        db = MainActivity.getDB();
        mFragmentManager = MainActivity.getFragManager();
        mSetName = ChooseSetFragment.getSetName();
        sCard = EditSetFragment.getChosenCard();

        mCurrTerm = (TextView) v.findViewById(R.id.text_display_old_term);
        mCurrDef = (TextView) v.findViewById(R.id.text_display_old_def);

        mTermInput = (EditText) v.findViewById(R.id.edit_text_new_term);
        mDefInput = (EditText) v.findViewById(R.id.edit_text_new_def);

        mCancel = (Button) v.findViewById(R.id.button_cancel_card_change);
        mSave = (Button) v.findViewById(R.id.button_save_card_change);
        mDeleteCard = (Button) v.findViewById(R.id.button_delete_card);

        // Display current card term and definition
        mCurrTerm.setText("Current Term: " + sCard.getTerm());
        mCurrDef.setText("Current Definition: " + sCard.getDef());

        // Set onClickListener for CANCEL
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.launchFragment(new SetViewFragment(),FRAG_SET_VIEW,FRAG_EDIT_CARD);
            }
        });

        // Set onClickListener for SAVE
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTerm = mTermInput.getText().toString(),
                        newDef = mDefInput.getText().toString();
                msg("Old Card = " + sCard.toString());
                msg("newTerm = " + newTerm + "\nnewDef = " + newDef);
                IndexCard newCard = new IndexCard();

                if (newTerm.equals("") && newDef.equals("")){
                    // No changes made to card, launch SetViewFragment
                    Toast.makeText(v.getContext(),"No Changes Made To Card.",Toast.LENGTH_SHORT)
                            .show();
                    MainActivity.launchFragment(new SetViewFragment(),FRAG_SET_VIEW,FRAG_EDIT_CARD);
                }
                else if(newTerm.equals("") && !newDef.equals("")){
                    // Term has not been changed, Definition has changed
                    msg("In first else if");
                    newCard.setTerm(sCard.getTerm());
                    newCard.setDef(newDef);
                    db.deleteCard(mSetName,sCard.getTerm());
                    db.addCard(mSetName,newCard);
                    Toast.makeText(v.getContext(),"Card Successfully Changed.",Toast.LENGTH_SHORT)
                            .show();
                }
                else if (!newTerm.equals("") && newDef.equals("")){
                    // Term has been changed, Definition has not been changed
                    msg("in second else if");
                    newCard.setTerm(newTerm);
                    newCard.setDef(sCard.getDef());
                    db.deleteCard(mSetName,sCard.getTerm());
                    db.addCard(mSetName,newCard);
                    Toast.makeText(v.getContext(),"Card Successfully Changed.",Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    // Both term and definition has been changed
                    msg("in else");
                    newCard.setTerm(newTerm);
                    newCard.setDef(newDef);
                    db.deleteCard(mSetName,sCard.getTerm());
                    db.addCard(mSetName,newCard);
                    Toast.makeText(v.getContext(),"Card Successfully Changed.",Toast.LENGTH_SHORT)
                            .show();
                }

                MainActivity.launchFragment(new SetViewFragment(),FRAG_SET_VIEW,FRAG_EDIT_CARD);
            }
        });

        mDeleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteCard(mSetName,sCard.getTerm());
                MainActivity.launchFragment(new SetViewFragment(),FRAG_SET_VIEW,FRAG_EDIT_SET);
            }
        });

        return v;
    }

    public void msg(String str){
        Log.i(MainActivity.TAG,str);
    }

    @Override
    public void onStart() {
        super.onStart();
        msg("EditCardFragment: onStart...");
    }

    @Override
    public void onStop() {
        super.onStop();
        msg("EditCardFragment: onStop...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        msg("EditCardFragment: onDestroy...");
    }

    @Override
    public void onPause() {
        super.onPause();
        msg("EditCardFragment: onPause...");
    }

    @Override
    public void onResume() {
        super.onResume();
        msg("EditCardFragment: onResume...");
    }


}
