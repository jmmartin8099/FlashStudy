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

import com.example.jmmar.flashstudy.R;

import static com.example.jmmar.flashstudy.MainActivity.FRAG_MAIN;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_SET_VIEW;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteSetFragment extends Fragment {
    public static final String TAG = "DeleteSetFragment";

    private DBHelper db;
    private FragmentManager mFragmentManager;

    private TextView mDisplayMsg;
    private EditText mDeleteInput;
    private Button mCancel;
    private Button mDelete;

    public DeleteSetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_delete_set, container, false);

        db = MainActivity.getDB();
        mFragmentManager = MainActivity.getFragManager();

        mDisplayMsg = (TextView) v.findViewById(R.id.text_delete_set);
        mDisplayMsg.append(ChooseSetFragment.getSetName());

        mDeleteInput = (EditText) v.findViewById(R.id.edit_text_delete_set);
        mCancel = (Button) v.findViewById(R.id.button_cancel_delete_set);
        mDelete = (Button) v.findViewById(R.id.button_final_delete_set);

        // Listener to cancel deleting a set
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                        new SetViewFragment(),FRAG_SET_VIEW).addToBackStack(FRAG_MAIN).commit();
            }
        });

        // Listener to delete the set
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setName = mDeleteInput.getText().toString();
                db.deleteSetOfCards(setName);
                mFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                        new MainFragment(),FRAG_MAIN).addToBackStack(FRAG_MAIN).commit();
            }
        });

        return v;
    }

}
