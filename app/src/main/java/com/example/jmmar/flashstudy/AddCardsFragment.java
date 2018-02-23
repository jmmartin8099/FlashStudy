package com.example.jmmar.flashstudy;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCardsFragment extends Fragment {
    public static final String TAG = "AddCardsFragment";

    private static DBHelper sDb;

    private static String sSetname;
    private EditText mTerm;
    private EditText mDefinition;
    private Button mEnterCard;
    private Button mCancelCard;

    public AddCardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_cards, container, false);

        sDb = MainActivity.getDB();

        mEnterCard = (Button) v.findViewById(R.id.button_enter_card);
        mCancelCard = (Button) v.findViewById(R.id.button_cancel_add_card);
        mTerm = (EditText) v.findViewById(R.id.edit_text_term);
        mDefinition = (EditText) v.findViewById(R.id.edit_text_definition);

        mEnterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sSetname = ChooseSetFragment.getSetName();
                if (mTerm.getText().toString().equals("") ||
                        mDefinition.getText().toString().equals(""))
                    Toast.makeText(v.getContext(),"Please Enter Both a Term and Definition.",
                            Toast.LENGTH_SHORT).show();
                else {
                    IndexCard card = new IndexCard(mTerm.getText().toString(),
                            mDefinition.getText().toString());
                    sDb.addCard(sSetname, card);
                    hideKeyboard();
                    Toast.makeText(v.getContext(), "Card Successfully Added!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        mCancelCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hideKeyboard();
                MainActivity.setCurrFragDisplayed(MainActivity.FRAG_SET_VIEW_ID);
                MainActivity.launchFragment(new SetViewFragment(), MainActivity.FRAG_SET_VIEW
                        , MainActivity.FRAG_CHOOSE_SET);
            }
        });

        return v;
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
    }

    public void msg(String str){
        Log.i(MainActivity.TAG,str);
    }

    @Override
    public void onStart() {
        super.onStart();
        msg("AddCardsFragment: onStart...");
    }

    @Override
    public void onStop() {
        super.onStop();
        msg("AddCardsFragment: onStop...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        msg("AddCardsFragment: onDestroy...");
    }

    @Override
    public void onPause() {
        super.onPause();
        msg("AddCardsFragment: onPause...");
    }

    @Override
    public void onResume() {
        super.onResume();
        msg("AddCardsFragment: onResume...");
    }
}
