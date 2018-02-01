package com.example.jmmar.flashstudy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.jmmar.flashstudy.MainActivity.FRAG_MAIN;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_SET_VIEW;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditCardFragment extends Fragment {
    public static final String TAG = "EditCardFragment";

    private static DBHelper db;
    private static FragmentManager mFragmentManager;

    private TextView mCurrTerm;
    private TextView mCurrDef;

    private EditText mTermInput;
    private EditText mDefInput;

    private Button mCancel;
    private Button mSave;
    private Button mDeleteCard;

    private static IndexCard mCard;

    public EditCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_card, container, false);

        db = MainActivity.getDB();
        mFragmentManager = MainActivity.getFragManager();
        mCard = EditSetFragment.getChosenCard();

        mCurrTerm = (TextView) v.findViewById(R.id.text_display_old_term);
        mCurrDef = (TextView) v.findViewById(R.id.text_display_old_def);

        mTermInput = (EditText) v.findViewById(R.id.edit_text_new_term);
        mDefInput = (EditText) v.findViewById(R.id.edit_text_new_def);

        mCancel = (Button) v.findViewById(R.id.button_cancel_card_change);
        mSave = (Button) v.findViewById(R.id.button_save_card_change);
        mDeleteCard = (Button) v.findViewById(R.id.button_delete_card);

        // Display current card term and definition
        mCurrTerm.setText("Current Term: " + mCard.getTerm());
        mCurrDef.setText("Current Definition: " + mCard.getDef());

        // Set onClickListener for CANCEL
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                        new SetViewFragment(),FRAG_SET_VIEW).addToBackStack(FRAG_MAIN).commit();
            }
        });

        // Set onClickListener for SAVE
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTerm = mTermInput.getText().toString(),
                        newDef = mDefInput.getText().toString();

                IndexCard newCard = new IndexCard(mCard.getSetname(),newTerm,newDef);

                db.deleteCard(mCard.getTerm());
                db.insertCard(newCard);

                mFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                        new SetViewFragment(),FRAG_SET_VIEW).addToBackStack(FRAG_MAIN).commit();
            }
        });

        mDeleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteCard(mCard.getTerm());

                mFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                        new SetViewFragment(),FRAG_SET_VIEW).commit();
            }
        });

        return v;
    }

}
