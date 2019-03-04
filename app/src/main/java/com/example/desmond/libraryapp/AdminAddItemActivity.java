package com.example.desmond.libraryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAddItemActivity extends AppCompatActivity {

    private Button linktomainButton, submitButton;
    private ImageView linktomainIcon;
    private EditText inputTitle, inputName, inputContributor, inputMaterial, inputPublisher, inputEdition, inputDescription, inputSubject, inputCallNumber, inputCopyNumber;

    private FirebaseAuth mAuth;

    ProgressDialog mProgressDialogAdd;

//    Firebase firebase;
   DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_item);

        dataRef = FirebaseDatabase.getInstance().getReference("Book_Details_Database");

//        linktomainIcon = (ImageView) findViewById(R.id.iconlinktomainadd);
//        linktomainButton = (Button) findViewById(R.id.btnBacktoMainAdd);
        submitButton = (Button) findViewById(R.id.btnSubmitAddItem);
        inputTitle = (EditText) findViewById(R.id.txtTitleAdd);
        inputName = (EditText) findViewById(R.id.txtNameAdd);
        inputContributor = (EditText) findViewById(R.id.txtContributorAdd);
        inputMaterial = (EditText) findViewById(R.id.txtmMaterialAdd);
        inputPublisher = (EditText) findViewById(R.id.txtPublisherAdd);
        inputEdition = (EditText) findViewById(R.id.txtEditionAdd);
        inputDescription = (EditText) findViewById(R.id.txtDescriptionAdd);
        inputSubject = (EditText) findViewById(R.id.txtSubjectAdd);
        inputCallNumber = (EditText) findViewById(R.id.txtCallNumberAdd);
        inputCopyNumber = (EditText) findViewById(R.id.txtCopyNumberAdd);


        mProgressDialogAdd = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

//        linktomainIcon.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View r)
//            {
//                Intent Ic = new Intent(AdminAddItemActivity.this, AdminMainActivity.class);
//                startActivity(Ic);
//                finish();
//            }
//        });
//
//        linktomainButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View r)
//            {
//                Intent B = new Intent(AdminAddItemActivity.this, AdminMainActivity.class);
//                startActivity(B);
//                finish();
//            }
//        });

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addBooks();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.backbutton, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Main) {
            Intent AA = new Intent(AdminAddItemActivity.this, AdminMainActivity.class);
            startActivity(AA);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addBooks()
    {
        String titleB = inputTitle.getText().toString().trim();
        String nameB = inputName.getText().toString().trim();
        String contributorB = inputContributor.getText().toString().trim();
        String materialB = inputMaterial.getText().toString().trim();
        String publisherB = inputPublisher.getText().toString().trim();
        String editionB = inputEdition.getText().toString().trim();
        String descriptionB = inputDescription.getText().toString().trim();
        String subjectB = inputSubject.getText().toString().trim();
        String callnumberB = inputCallNumber.getText().toString().trim();
        String copynumberB = inputCopyNumber.getText().toString().trim();

        mProgressDialogAdd.setTitle("Adding Book's Details");
        mProgressDialogAdd.setMessage("Wait while the book's details are being added..");
        mProgressDialogAdd.show();

        if(!TextUtils.isEmpty(titleB) && !TextUtils.isEmpty(nameB) && !TextUtils.isEmpty(contributorB) && !TextUtils.isEmpty(materialB) && !TextUtils.isEmpty(publisherB) && !TextUtils.isEmpty(editionB) && !TextUtils.isEmpty(descriptionB) && !TextUtils.isEmpty(subjectB) && !TextUtils.isEmpty(callnumberB) && !TextUtils.isEmpty(copynumberB))
        {

            String book_Id = dataRef.push().getKey();

            BooksInformation booksInformation = new BooksInformation(book_Id, titleB, nameB, contributorB, materialB, publisherB, editionB, descriptionB, subjectB, callnumberB, copynumberB);

            dataRef.child(titleB).setValue(booksInformation);

            Toast.makeText(AdminAddItemActivity.this, "Book's Information are added successful", Toast.LENGTH_LONG).show();
            mProgressDialogAdd.dismiss();

            Intent backToMain = new Intent(AdminAddItemActivity.this, AdminMainActivity.class);
            backToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(backToMain);


        }else
        {
            Toast.makeText(this, "Please fill up all the information", Toast.LENGTH_LONG).show();
            mProgressDialogAdd.dismiss();
        }
    }



}
