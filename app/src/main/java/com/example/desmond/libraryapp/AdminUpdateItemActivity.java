package com.example.desmond.libraryapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.UpdateAppearance;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUpdateItemActivity extends AppCompatActivity {

    private DatabaseReference databaseBooks;
    FirebaseUser firebaseUser;

    ListView listViewBooks;

    //a list to store all the artist from firebase databse
    List<BooksUpdate> books;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    private ImageView iconBackUpdate;
    private Button buttonBackUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_item);

       databaseBooks = FirebaseDatabase.getInstance().getReference("Book_Details_Database");

        listViewBooks = (ListView) findViewById(R.id.listViewBooksUpdate);
//        iconBackUpdate = (ImageView) findViewById(R.id.iconlinktomainupdate);
//        buttonBackUpdate = (Button) findViewById(R.id.btnBacktoMainUpdate);

        books = new ArrayList<>();
//
//        iconBackUpdate.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                startActivity(new Intent(AdminUpdateItemActivity.this, AdminMainActivity.class));
//            }
//        });
//
//        buttonBackUpdate.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                startActivity(new Intent(AdminUpdateItemActivity.this, AdminMainActivity.class));
//            }
//        });

        databaseBooks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                books.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    BooksUpdate booksUpdate = postSnapshot.getValue(BooksUpdate.class);

                    books.add(booksUpdate);
                }

                //creating adapter
                BooksList adapter = new BooksList(AdminUpdateItemActivity.this, books);
                //attaching adapter to the listview
                listViewBooks.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listViewBooks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
//parent (adapterView)
                BooksUpdate booksUpdate = books.get(i);

                showUpdateDialog(booksUpdate.getA_Book_Id(), booksUpdate.getB_Book_Title(),  booksUpdate.getC_Name_By(), booksUpdate.getD_Book_Contributor(), booksUpdate.getE_Book_MaterialType(), booksUpdate.getF_Book_Publisher(), booksUpdate.getG_Book_Edition(), booksUpdate.getH_Book_Description(), booksUpdate.getI_Book_Subject(), booksUpdate.getJ_Book_CallNumber(), booksUpdate.getK_Book_CopyNumber());

                return true;

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
            Intent AU = new Intent(AdminUpdateItemActivity.this, AdminMainActivity.class);
            startActivity(AU);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showUpdateDialog(final String a_Books_Id, final String b_Book_Title, String c_Name_By, String d_Book_Contributor, String e_Book_MaterialType, String f_Book_Publisher, String g_Book_Edition, String h_Book_Description, String i_Book_Subject, String j_Book_CallNumber, String k_Book_CopyNumber)
    {

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Book_Details_Database").child(b_Book_Title);
        databaseBooks = FirebaseDatabase.getInstance().getReference("Book_Details_Database");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.editTextTitle);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextContributor = (EditText) dialogView.findViewById(R.id.editTextContributor);
        final EditText editTextMaterial = (EditText) dialogView.findViewById(R.id.editTextMaterial);
        final EditText editTextPublisher = (EditText) dialogView.findViewById(R.id.editTextPublisher);
        final EditText editTextEdition = (EditText) dialogView.findViewById(R.id.editTextEdition);
        final EditText editTextDescription = (EditText) dialogView.findViewById(R.id.editTextDescription);
        final EditText editTextSubject = (EditText) dialogView.findViewById(R.id.editTextSubject);
        final EditText editTextCallNumber = (EditText) dialogView.findViewById(R.id.editTextCallNumber);
        final EditText editTextCopyNumber = (EditText) dialogView.findViewById(R.id.editTextCopyNumber);
        final Button btn_UpdateItemAdmin = (Button) dialogView.findViewById(R.id.btn_UpdateItemAdmin);

        dialogBuilder.setTitle("Updating Book: " + b_Book_Title);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btn_UpdateItemAdmin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String title = editTextTitle.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String contributor = editTextContributor.getText().toString().trim();
                String material = editTextMaterial.getText().toString().trim();
                String publisher = editTextPublisher.getText().toString().trim();
                String edition = editTextEdition.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String subject = editTextSubject.getText().toString().trim();
                String callnumber = editTextCallNumber.getText().toString().trim();
                String copynumber = editTextCopyNumber.getText().toString().trim();

                if (TextUtils.isEmpty(title))
                {
                    editTextTitle.setError("Title is required!");
                    return;
                }
                if (TextUtils.isEmpty(name))
                {
                    editTextName.setError("Name is required!");
                    return;
                }
                if (TextUtils.isEmpty(contributor))
                {
                    editTextContributor.setError("Contributor is required!");
                    return;
                }
                if (TextUtils.isEmpty(material))
                {
                    editTextMaterial.setError("Material Type is required!");
                    return;
                }
                if (TextUtils.isEmpty(publisher))
                {
                    editTextPublisher.setError("Publisher is required!");
                    return;
                }
                if (TextUtils.isEmpty(edition))
                {
                    editTextEdition.setError("Edition is required!");
                    return;
                }
                if (TextUtils.isEmpty(description))
                {
                    editTextDescription.setError("Description is required!");
                    return;
                }
                if (TextUtils.isEmpty(subject))
                {
                    editTextSubject.setError("Subject is required!");
                    return;
                }
                if (TextUtils.isEmpty(callnumber))
                {
                    editTextCallNumber.setError("Call Number is required!");
                    return;
                }
                if (TextUtils.isEmpty(copynumber))
                {
                    editTextCopyNumber.setError("Copy Number is required!");
                    return;
                }

                databaseReference.child("a_Book_Id").setValue(a_Books_Id);
                databaseReference.child("b_Book_Title").setValue(title);
                databaseReference.child("c_Name_By").setValue(name);
                databaseReference.child("d_Book_Contributor").setValue(contributor);
                databaseReference.child("e_Book_MaterialType").setValue(material);
                databaseReference.child("f_Book_Publisher").setValue(publisher);
                databaseReference.child("g_Book_Edition").setValue(edition);
                databaseReference.child("h_Book_Description").setValue(description);
                databaseReference.child("i_Book_Subject").setValue(subject);
                databaseReference.child("j_Book_CallNumber").setValue(callnumber);
                databaseReference.child("k_Book_CopyNumber").setValue(copynumber);

                    updateBook(a_Books_Id, title, name, contributor, material, publisher, edition, description, subject, callnumber, copynumber);
                    alertDialog.dismiss();

            }
        });



//        dialogBuilder.setTitle("Updating Book: " + b_Book_Title);
//
//        final AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.show();
    }

    private boolean updateBook(String id,  String title, String name, String contributor, String material, String publisher, String edition, String description, String subject, String callnumber, String copynumber)
    {
//        databaseBooks = FirebaseDatabase.getInstance().getReference("Book_Details_Database").child(title);
       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Book_Details_Database").child(title);


//        BooksUpdate booksUpdate = new BooksUpdate(id, title, name, contributor, material, publisher, edition, description, subject, callnumber, copynumber);
//
//        databaseReference.setValue(booksUpdate);


        Toast.makeText(this, "Book has been Updated Successfully", Toast.LENGTH_LONG).show();

        return true;

    }


}
