package com.example.desmond.libraryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class UpdateDialogActivity extends AppCompatActivity {

    private EditText title, name, contributor, material, publisher, edition, description, subject, callnumber, copynumber;
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dialog);

        title = (EditText) findViewById(R.id.editTextTitle);
        name = (EditText) findViewById(R.id.editTextName);
        contributor = (EditText) findViewById(R.id.editTextContributor);
        material = (EditText) findViewById(R.id.editTextMaterial);
        publisher = (EditText) findViewById(R.id.editTextPublisher);
        edition = (EditText) findViewById(R.id.editTextEdition);
        description = (EditText) findViewById(R.id.editTextDescription);
        subject = (EditText) findViewById(R.id.editTextSubject);
        callnumber = (EditText) findViewById(R.id.editTextCallNumber);
        copynumber = (EditText) findViewById(R.id.editTextCopyNumber);

        mRef = new Firebase("https://libraryapp-62276.firebaseio.com/Book_Details_Database");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
               {
                   BooksUpdate booksUpdate = postSnapshot.getValue(BooksUpdate.class);

                   String titleB = booksUpdate.getB_Book_Title();
                   String nameB = booksUpdate.getC_Name_By();
                   String contributorB = booksUpdate.getD_Book_Contributor();
                   String materialB = booksUpdate.getE_Book_MaterialType();
                   String publisherB = booksUpdate.getF_Book_Publisher();
                   String editionB = booksUpdate.getG_Book_Edition();
                   String descriptionB = booksUpdate.getH_Book_Description();
                   String subjectB = booksUpdate.getI_Book_Subject();
                   String callnumberB = booksUpdate.getJ_Book_CallNumber();
                   String copynumberB = booksUpdate.getK_Book_CopyNumber();

                   title.setText(titleB);
                   name.setText(nameB);
                   contributor.setText(contributorB);
                   material.setText(materialB);
                   publisher.setText(publisherB);
                   edition.setText(editionB);
                   description.setText(descriptionB);
                   subject.setText(subjectB);
                   callnumber.setText(callnumberB);
                   copynumber.setText(copynumberB);

               }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
