package com.example.desmond.libraryapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {

//        TextView email = (TextView) getView().findViewById(R.id.txtEmailHelp);
//        email.setText(Html.fromHtml("<a href=\"mailto:wayhou10@gmail.com\"> wayhou10@gmail.com</a>"));
//        email.setMovementMethod(LinkMovementMethod.getInstance());

        TextView email2 = (TextView) getView().findViewById(R.id.txtEmailHelp2);
        email2.setText(Html.fromHtml("<a href=\"mailto:wayhou10@gmail.com\"> wayhou10@gmail.com</a>"));
        email2.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

}
