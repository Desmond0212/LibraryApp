package com.example.desmond.libraryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdminSearchActivity extends AppCompatActivity {

    private EditText inputSearchAdmin;
    private ImageView backSearchAdmin;
    private Button backSearchButton;

    private RecyclerView mResultList;
    private DatabaseReference mBookDatabase;
    FirebaseUser firebaseUser;

    ArrayList<String> bookTitleList;
    ArrayList<String> bookNameByList;
    ArrayList<String> bookContributorList;
    ArrayList<String> bookMaterialList;
    ArrayList<String> bookPublisherList;
    ArrayList<String> bookEditionList;
    ArrayList<String> bookDescriptionList;
    ArrayList<String> bookSubjectList;
    ArrayList<String> bookCallNumberList;
    ArrayList<String> bookCopyNumberList;

    SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search);

        mBookDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        inputSearchAdmin = (EditText) findViewById(R.id.txtSearchMainAdmin);
//        backSearchAdmin = (ImageView) findViewById(R.id.icon_backSearch);
//        backSearchButton = (Button) findViewById(R.id.btnBacktoMainSearch);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mResultList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        bookTitleList = new ArrayList<>();
        bookNameByList = new ArrayList<>();
        bookContributorList = new ArrayList<>();
        bookMaterialList = new ArrayList<>();
        bookPublisherList = new ArrayList<>();
        bookEditionList = new ArrayList<>();
        bookDescriptionList = new ArrayList<>();
        bookSubjectList = new ArrayList<>();
        bookCallNumberList = new ArrayList<>();
        bookCopyNumberList = new ArrayList<>();


//        backSearchAdmin.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                startActivity(new Intent(AdminSearchActivity.this, AdminMainActivity.class));
//            }
//        });
//
//        backSearchButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                startActivity(new Intent(AdminSearchActivity.this, AdminMainActivity.class));
//            }
//        });


        inputSearchAdmin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty())
                {
                    setAdapter(s.toString());
                }else
                {
                    //Clear the list for every new search
                    bookTitleList.clear();
                    bookNameByList.clear();
                    bookContributorList.clear();
                    bookMaterialList.clear();
                    bookPublisherList.clear();
                    bookEditionList.clear();
                    bookDescriptionList.clear();
                    bookSubjectList.clear();
                    bookCallNumberList.clear();
                    bookCopyNumberList.clear();
                    mResultList.removeAllViews();
                }

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
            Intent SA = new Intent(AdminSearchActivity.this, AdminMainActivity.class);
            startActivity(SA);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setAdapter(final String searchedString)
    {

        mBookDatabase.child("Book_Details_Database").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Clear the list for every new search
                bookTitleList.clear();
                bookNameByList.clear();
                bookContributorList.clear();
                bookMaterialList.clear();
                bookPublisherList.clear();
                bookEditionList.clear();
                bookDescriptionList.clear();
                bookSubjectList.clear();
                bookCallNumberList.clear();
                bookCopyNumberList.clear();
                mResultList.removeAllViews();

                int counter = 0;

                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    String book_id = snapshot.getKey();
                    String b_Book_Title = snapshot.child("b_Book_Title").getValue(String.class);
                    String c_Name_By = snapshot.child("c_Name_By").getValue(String.class);
                    String d_Book_Contributor = snapshot.child("d_Book_Contributor").getValue(String.class);
                    String e_Book_MaterialType = snapshot.child("e_Book_MaterialType").getValue(String.class);
                    String f_Book_Publisher = snapshot.child("f_Book_Publisher").getValue(String.class);
                    String g_Book_Edition = snapshot.child("g_Book_Edition").getValue(String.class);
                    String h_Book_Description = snapshot.child("h_Book_Description").getValue(String.class);
                    String i_Book_Subject = snapshot.child("i_Book_Subject").getValue(String.class);
                    String j_Book_CallNumber = snapshot.child("j_Book_CallNumber").getValue(String.class);
                    String k_Book_CopyNumber = snapshot.child("k_Book_CopyNumber").getValue(String.class);

                    if (b_Book_Title.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        bookTitleList.add(b_Book_Title);
                        bookNameByList.add(c_Name_By);
                        bookContributorList.add(d_Book_Contributor);
                        bookMaterialList.add(e_Book_MaterialType);
                        bookPublisherList.add(f_Book_Publisher);
                        bookEditionList.add(g_Book_Edition);
                        bookDescriptionList.add(h_Book_Description);
                        bookSubjectList.add(i_Book_Subject);
                        bookCallNumberList.add(j_Book_CallNumber);
                        bookCopyNumberList.add(k_Book_CopyNumber);
                        counter++;
                    }else if (c_Name_By.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        bookTitleList.add(b_Book_Title);
                        bookNameByList.add(c_Name_By);
                        bookContributorList.add(d_Book_Contributor);
                        bookMaterialList.add(e_Book_MaterialType);
                        bookPublisherList.add(f_Book_Publisher);
                        bookEditionList.add(g_Book_Edition);
                        bookDescriptionList.add(h_Book_Description);
                        bookSubjectList.add(i_Book_Subject);
                        bookCallNumberList.add(j_Book_CallNumber);
                        bookCopyNumberList.add(k_Book_CopyNumber);
                        counter++;
                    }else if (d_Book_Contributor.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        bookTitleList.add(b_Book_Title);
                        bookNameByList.add(c_Name_By);
                        bookContributorList.add(d_Book_Contributor);
                        bookMaterialList.add(e_Book_MaterialType);
                        bookPublisherList.add(f_Book_Publisher);
                        bookEditionList.add(g_Book_Edition);
                        bookDescriptionList.add(h_Book_Description);
                        bookSubjectList.add(i_Book_Subject);
                        bookCallNumberList.add(j_Book_CallNumber);
                        bookCopyNumberList.add(k_Book_CopyNumber);
                        counter++;
                    }else if (e_Book_MaterialType.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        bookTitleList.add(b_Book_Title);
                        bookNameByList.add(c_Name_By);
                        bookContributorList.add(d_Book_Contributor);
                        bookMaterialList.add(e_Book_MaterialType);
                        bookPublisherList.add(f_Book_Publisher);
                        bookEditionList.add(g_Book_Edition);
                        bookDescriptionList.add(h_Book_Description);
                        bookSubjectList.add(i_Book_Subject);
                        bookCallNumberList.add(j_Book_CallNumber);
                        bookCopyNumberList.add(k_Book_CopyNumber);
                        counter++;
                    }else if (f_Book_Publisher.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        bookTitleList.add(b_Book_Title);
                        bookNameByList.add(c_Name_By);
                        bookContributorList.add(d_Book_Contributor);
                        bookMaterialList.add(e_Book_MaterialType);
                        bookPublisherList.add(f_Book_Publisher);
                        bookEditionList.add(g_Book_Edition);
                        bookDescriptionList.add(h_Book_Description);
                        bookSubjectList.add(i_Book_Subject);
                        bookCallNumberList.add(j_Book_CallNumber);
                        bookCopyNumberList.add(k_Book_CopyNumber);
                        counter++;
                    }else if (g_Book_Edition.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        bookTitleList.add(b_Book_Title);
                        bookNameByList.add(c_Name_By);
                        bookContributorList.add(d_Book_Contributor);
                        bookMaterialList.add(e_Book_MaterialType);
                        bookPublisherList.add(f_Book_Publisher);
                        bookEditionList.add(g_Book_Edition);
                        bookDescriptionList.add(h_Book_Description);
                        bookSubjectList.add(i_Book_Subject);
                        bookCallNumberList.add(j_Book_CallNumber);
                        bookCopyNumberList.add(k_Book_CopyNumber);
                        counter++;
                    }else if (h_Book_Description.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        bookTitleList.add(b_Book_Title);
                        bookNameByList.add(c_Name_By);
                        bookContributorList.add(d_Book_Contributor);
                        bookMaterialList.add(e_Book_MaterialType);
                        bookPublisherList.add(f_Book_Publisher);
                        bookEditionList.add(g_Book_Edition);
                        bookDescriptionList.add(h_Book_Description);
                        bookSubjectList.add(i_Book_Subject);
                        bookCallNumberList.add(j_Book_CallNumber);
                        bookCopyNumberList.add(k_Book_CopyNumber);
                        counter++;
                    }else if (i_Book_Subject.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        bookTitleList.add(b_Book_Title);
                        bookNameByList.add(c_Name_By);
                        bookContributorList.add(d_Book_Contributor);
                        bookMaterialList.add(e_Book_MaterialType);
                        bookPublisherList.add(f_Book_Publisher);
                        bookEditionList.add(g_Book_Edition);
                        bookDescriptionList.add(h_Book_Description);
                        bookSubjectList.add(i_Book_Subject);
                        bookCallNumberList.add(j_Book_CallNumber);
                        bookCopyNumberList.add(k_Book_CopyNumber);
                        counter++;
                    }else if (j_Book_CallNumber.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        bookTitleList.add(b_Book_Title);
                        bookNameByList.add(c_Name_By);
                        bookContributorList.add(d_Book_Contributor);
                        bookMaterialList.add(e_Book_MaterialType);
                        bookPublisherList.add(f_Book_Publisher);
                        bookEditionList.add(g_Book_Edition);
                        bookDescriptionList.add(h_Book_Description);
                        bookSubjectList.add(i_Book_Subject);
                        bookCallNumberList.add(j_Book_CallNumber);
                        bookCopyNumberList.add(k_Book_CopyNumber);
                        counter++;
                    }else if (k_Book_CopyNumber.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        bookTitleList.add(b_Book_Title);
                        bookNameByList.add(c_Name_By);
                        bookContributorList.add(d_Book_Contributor);
                        bookMaterialList.add(e_Book_MaterialType);
                        bookPublisherList.add(f_Book_Publisher);
                        bookEditionList.add(g_Book_Edition);
                        bookDescriptionList.add(h_Book_Description);
                        bookSubjectList.add(i_Book_Subject);
                        bookCallNumberList.add(j_Book_CallNumber);
                        bookCopyNumberList.add(k_Book_CopyNumber);
                        counter++;
                    }

                    if (counter == 15)
                        break;
                }

                searchAdapter = new SearchAdapter(AdminSearchActivity.this, bookTitleList, bookNameByList, bookContributorList, bookMaterialList, bookPublisherList, bookEditionList, bookDescriptionList, bookSubjectList, bookCallNumberList, bookCopyNumberList);
                mResultList.setAdapter(searchAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
