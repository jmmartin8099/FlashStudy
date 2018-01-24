package com.example.jmmar.flashstudy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.example.jmmar.flashstudy.MainActivity.FRAG_ADD_CARDS;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_DELETE_SET;
import static com.example.jmmar.flashstudy.MainActivity.FRAG_MAIN;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetViewFragment extends Fragment {
    public static final String TAG = "SetViewFragment";

    private static DBHelper db;
    private static FragmentManager mFragmentManager;

    private static String mSetName;

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
        mFragmentManager = MainActivity.getFragManager();

        mDisplaySetName = (TextView) v.findViewById(R.id.view_set_title);

        // Get chosen set name
        mSetName = ChooseSetFragment.getSetName();
        mDisplaySetName.setText(mSetName);

        // Get buttons
        mBeginStudy = (Button) v.findViewById(R.id.button_begin_studying);
        mAddCard = (Button) v.findViewById(R.id.button_add_cards);
        mEditSet = (Button) v.findViewById(R.id.button_edit_set);
        mDeleteSet = (Button) v.findViewById(R.id.button_delete_set);

        // Set onClickListener to BeginStudying
        mBeginStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(v.getContext(),StudyActivity.class);
                startActivity(startIntent);
            }
        });

        // Set onClickListener to AddCard
        mAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                        new AddCardsFragment(),FRAG_ADD_CARDS).addToBackStack(FRAG_MAIN).commit();
            }
        });

        // Set onClickListener to EditSet
        mEditSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Not Yet Added!",Toast.LENGTH_SHORT).show();
            }
        });

        // Set onClickListener to DeleteSet
        mDeleteSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager.beginTransaction().replace(R.id.big_fragment_container,
                        new DeleteSetFragment(),FRAG_DELETE_SET).addToBackStack(FRAG_MAIN).commit();
            }
        });

        return v;
    }
}
