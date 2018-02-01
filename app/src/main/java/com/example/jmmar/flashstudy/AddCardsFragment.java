package com.example.jmmar.flashstudy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCardsFragment extends Fragment {
    public static final String TAG = "AddCardsFragment";

    private static DBHelper db;

    private static String mSetname;
    private EditText mTerm;
    private EditText mDefinition;
    private Button mEnterCard;

    public AddCardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_cards, container, false);

        db = MainActivity.getDB();

        mEnterCard = (Button) v.findViewById(R.id.button_enter_card);
        mTerm = (EditText) v.findViewById(R.id.edit_text_term);
        mDefinition = (EditText) v.findViewById(R.id.edit_text_definition);

        mEnterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetname = ChooseSetFragment.getSetName();

                IndexCard card = new IndexCard(mSetname,mTerm.getText().toString(),
                        mDefinition.getText().toString());
                db.insertCard(card);

                Toast.makeText(v.getContext(),"Card Successfully Added!",Toast.LENGTH_LONG).show();
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
