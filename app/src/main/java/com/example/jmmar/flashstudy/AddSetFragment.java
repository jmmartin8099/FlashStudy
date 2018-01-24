package com.example.jmmar.flashstudy;


import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        mEnterSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndexCard temp = new IndexCard(mSetNameInput.getText().toString(),"set","set");
                db.insertCard(temp);
                Toast.makeText(v.getContext(),"Set Successfully Added!",Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}
