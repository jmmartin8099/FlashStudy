package com.example.jmmar.flashstudy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.example.jmmar.flashstudy.MainActivity.FRAG_ADD_CARDS;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_ADD_CARDS_ID;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_CHOOSE_SET;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_CHOOSE_SET_ID;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_DELETE_SET;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_DELETE_SET_ID;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_EDIT_SET;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_EDIT_SET_ID;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_MAIN;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_SET_VIEW;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_SET_VIEW_ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetViewFragment extends Fragment {
    public static final String TAG = "SetViewFragment";

    private DBHelper db;

    private String mSetName;

    private TextView mDisplaySetName;
    private Button mBeginStudy;
    private Button mAddCard;
    private Button mEditSet;
    private Button mDeleteSet;

    public SetViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_set_view, container, false);

        db = MainActivity.getDB();

        mDisplaySetName = (TextView) v.findViewById(R.id.view_set_title);

        // Get chosen set name
        mSetName = ChooseSetFragment.getSetName();
        mDisplaySetName.setText(mSetName);

        // Get buttons
        mBeginStudy = (Button) v.findViewById(R.id.button_begin_studying);
        mAddCard = (Button) v.findViewById(R.id.button_add_cards);
        mEditSet = (Button) v.findViewById(R.id.button_edit_set);
        mDeleteSet = (Button) v.findViewById(R.id.button_delete_set);

        // Set onClickListener to change sets
        mDisplaySetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setCurrFragDisplayed(FRAG_CHOOSE_SET_ID);
                MainActivity.launchFragment(new ChooseSetFragment(),FRAG_CHOOSE_SET,FRAG_MAIN);
            }
        });

        // Set onClickListener to BeginStudying
        mBeginStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.numCardsInSet(mSetName) == 0)
                    Toast.makeText(v.getContext(),"No Cards in Set!\nAdd Cards to begin studying.",
                            Toast.LENGTH_LONG).show();
                else {
                    MainActivity.setCurrFragDisplayed(FRAG_SET_VIEW_ID);
                    Intent startIntent = new Intent(v.getContext(), StudyActivity.class);
                    startActivity(startIntent);
                }
            }
        });

        // Set onClickListener to AddCard
        mAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setCurrFragDisplayed(FRAG_ADD_CARDS_ID);
                MainActivity.launchFragment(new AddCardsFragment(),FRAG_ADD_CARDS,FRAG_SET_VIEW);
            }
        });

        // Set onClickListener to EditSet
        mEditSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.numCardsInSet(mSetName) == 0)
                    Toast.makeText(v.getContext(),"No Cards in Set!\nAdd Cards to edit the set.",
                            Toast.LENGTH_LONG).show();
                else {
                    MainActivity.setCurrFragDisplayed(FRAG_EDIT_SET_ID);
                    MainActivity.launchFragment(new EditSetFragment(), FRAG_EDIT_SET,FRAG_SET_VIEW);
                }
            }
        });

        // Set onClickListener to DeleteSet
        mDeleteSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setCurrFragDisplayed(FRAG_DELETE_SET_ID);
                MainActivity.launchFragment(new DeleteSetFragment(),FRAG_DELETE_SET,FRAG_SET_VIEW);
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
        msg("SetViewFragment: onStart...");
    }

    @Override
    public void onStop() {
        super.onStop();
        msg("SetViewFragment: onStop...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        msg("SetViewFragment: onDestroy...");
    }

    @Override
    public void onPause() {
        super.onPause();
        msg("SetViewFragment: onPause...");
    }

    @Override
    public void onResume() {
        super.onResume();
        msg("SetViewFragment: onResume...");
    }
}
