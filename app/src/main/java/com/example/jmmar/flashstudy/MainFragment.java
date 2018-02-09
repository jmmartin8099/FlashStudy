package com.example.jmmar.flashstudy;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.example.jmmar.flashstudy.MainActivity.FRAG_ADD_SET;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_ADD_SET_ID;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_CHOOSE_SET;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_CHOOSE_SET_ID;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_MAIN;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment";

    private DBHelper db;

    private Button mChooseSet;
    private Button mAddSet;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        db = MainActivity.getDB();

        mChooseSet = (Button) v.findViewById(R.id.button_choose_set);
        mAddSet = (Button) v.findViewById(R.id.button_add_set);

        mChooseSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setCurrFragDisplayed(FRAG_CHOOSE_SET_ID);
                MainActivity.launchFragment(new ChooseSetFragment(),FRAG_CHOOSE_SET,FRAG_MAIN);
            }
        });

        mAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setCurrFragDisplayed(FRAG_ADD_SET_ID);
                MainActivity.launchFragment(new AddSetFragment(),FRAG_ADD_SET,FRAG_MAIN);
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
        msg("MainFragment: onStart...");
    }

    @Override
    public void onStop() {
        super.onStop();
        msg("MainFragment: onStop...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        msg("MainFragment: onDestroy...");
    }

    @Override
    public void onPause() {
        super.onPause();
        msg("MainFragment: onPause...");
    }

    @Override
    public void onResume() {
        super.onResume();
        msg("MainFragment: onResume...");
    }
}
