package com.example.jmmar.flashstudy;


import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jmmar.flashstudy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSetFragment extends Fragment {
    public static final String TAG = "AddSetFragment";

    private DBHelper db;

    private EditText mSetNameInput;
    private Button mEnterSet;
    private Button mCancelSet;

    public AddSetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_set, container, false);

        db = MainActivity.getDB();

        mSetNameInput = (EditText) v.findViewById(R.id.edit_text_set_name);
        mEnterSet = (Button) v.findViewById(R.id.button_enter_set_name);
        mCancelSet = (Button) v.findViewById(R.id.button_cancel_set_name);

        mEnterSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSetNameInput.getText().toString().equals(""))
                    Toast.makeText(v.getContext(), "Please Enter a Set Name.", Toast.LENGTH_SHORT).show();
                else if (db.addSet(mSetNameInput.getText().toString()))
                    Toast.makeText(v.getContext(),"Set Successfully Added!",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(v.getContext(),"Set Already Exists!",Toast.LENGTH_SHORT).show();
            }
        });

        mCancelSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setCurrFragDisplayed(MainActivity.FRAG_MAIN_ID);
                MainActivity.launchFragment(new MainFragment(),MainActivity.FRAG_MAIN,
                        MainActivity.FRAG_ADD_SET);
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
        msg("AddSetFragment: onStart...");
    }

    @Override
    public void onStop() {
        super.onStop();
        msg("AddSetFragment: onStop...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        msg("AddSetFragment: onDestroy...");
    }

    @Override
    public void onPause() {
        super.onPause();
        msg("AddSetFragment: onPause...");
    }

    @Override
    public void onResume() {
        super.onResume();
        msg("AddSetFragment: onResume...");
    }
}
