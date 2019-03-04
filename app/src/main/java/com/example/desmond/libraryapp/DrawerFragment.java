package com.example.desmond.libraryapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText userInputSearch;

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

    public DrawerFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, null);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBookDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userInputSearch = (EditText) getView().findViewById(R.id.txtSearchMain);

        mResultList = (RecyclerView) getView().findViewById(R.id.result_listUser);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        mResultList.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));

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

        userInputSearch.addTextChangedListener(new TextWatcher() {
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

                searchAdapter = new SearchAdapter(getActivity().getApplicationContext(), bookTitleList, bookNameByList, bookContributorList, bookMaterialList, bookPublisherList, bookEditionList, bookDescriptionList, bookSubjectList, bookCallNumberList, bookCopyNumberList);
                mResultList.setAdapter(searchAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
