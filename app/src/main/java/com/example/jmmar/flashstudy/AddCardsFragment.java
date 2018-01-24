package com.example.jmmar.flashstudy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private static DBHelper db;

    private String mSetname;
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
        mEnterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mSetname = ChooseSetFragment.getSetName();
                 mTerm = (EditText) v.findViewById(R.id.edit_text_term);
                 mDefinition = (EditText) v.findViewById(R.id.edit_text_definition);

                String termStr = mTerm.getText().toString(),
                        defStr = mDefinition.getText().toString();

                IndexCard card = new IndexCard(mSetname,termStr,defStr);

                db.insertCard(card);

                Toast.makeText(v.getContext(),"Card Successfully Added!",Toast.LENGTH_LONG).show();
            }
        });



        return v;
    }
}
