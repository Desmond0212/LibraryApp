package com.example.desmond.libraryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView email = (TextView) findViewById(R.id.txtViewEmail);
        email.setText(Html.fromHtml("<a href=\"mailto:wayhou10@gmail.com\">Email: wayhou10@gmail.com</a>"));
        email.setMovementMethod(LinkMovementMethod.getInstance());


    }
}
