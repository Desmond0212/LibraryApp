package com.example.desmond.libraryapp;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {

    private DatabaseReference databaseReserveBooks;
    FirebaseUser firebaseUser;

    private FirebaseAuth firebaseAuth;
    ProgressDialog dialog;

    ListView listViewBooksReservation;

    //a list to store all the artist from firebase databse
    List<BooksUpdate> books;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(getActivity());

        databaseReserveBooks = FirebaseDatabase.getInstance().getReference("Book_Details_Database");

        listViewBooksReservation = (ListView) getView().findViewById(R.id.listViewBooksReservation);

        books = new ArrayList<>();

        databaseReserveBooks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                books.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    BooksUpdate booksUpdate = postSnapshot.getValue(BooksUpdate.class);

                    books.add(booksUpdate);
                }

                //creating adapter
                BooksList adapter = new BooksList(getActivity(), books);
                //attaching adapter to the listview
                listViewBooksReservation.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listViewBooksReservation.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
//parent (adapterView)
                BooksUpdate booksUpdate = books.get(i);

                showUpdateDialog(booksUpdate.getA_Book_Id(), booksUpdate.getB_Book_Title(),  booksUpdate.getC_Name_By(), booksUpdate.getD_Book_Contributor(), booksUpdate.getE_Book_MaterialType(), booksUpdate.getF_Book_Publisher(), booksUpdate.getG_Book_Edition(), booksUpdate.getH_Book_Description(), booksUpdate.getI_Book_Subject(), booksUpdate.getJ_Book_CallNumber(), booksUpdate.getK_Book_CopyNumber());

                return true;

            }
        });

    }

    public void dialogBox(String txt, final String a_Books_Id, final String b_Book_Title, final String c_Name_By, final String d_Book_Contributor, final String e_Book_MaterialType, final String f_Book_Publisher, final String g_Book_Edition, final String h_Book_Description, final String i_Book_Subject, final String j_Book_CallNumber, final String k_Book_CopyNumber)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(txt);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showUpdateDialog(final String a_Books_Id, final String b_Book_Title, final String c_Name_By, final String d_Book_Contributor, final String e_Book_MaterialType, final String f_Book_Publisher, final String g_Book_Edition, final String h_Book_Description, final String i_Book_Subject, final String j_Book_CallNumber, final String k_Book_CopyNumber)
    {

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Book_Details_Database").child(b_Book_Title);
//        databaseBooks = FirebaseDatabase.getInstance().getReference("Book_Details_Database");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.reservation_dialog, null);

        dialogBuilder.setView(dialogView);

//        final EditText txtUserEmail = (EditText) dialogView.findViewById(R.id.editTextUsernameRE);
        final Button btn_ReserveItemUser = (Button) dialogView.findViewById(R.id.btn_BookReservation);

        dialogBuilder.setTitle("Books reservation from APU library catalogue");
        dialogBuilder.setMessage("Title: " + b_Book_Title + "\n" + "Name (By): " + c_Name_By + "\n" + "Contributor(s): "
                + d_Book_Contributor + "\n" + "Material Type: " + e_Book_MaterialType + "\n" + "Publisher(s): " + f_Book_Publisher
                + "\n" + "Edition: " + g_Book_Edition + "\n" + "Description: " + h_Book_Description + "\n" + "Subject(s): " +
                i_Book_Subject + "\n" + "Call Number: " + j_Book_CallNumber + "\n" + "Copy Number: " + k_Book_CopyNumber);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();


        btn_ReserveItemUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

//                String user_id = firebaseAuth.getCurrentUser().getUid();
//                DatabaseReference resBook = FirebaseDatabase.getInstance().getReference("Book_Reserved").child(user_id);
//
//                String adminEmail = txtUserEmail.getText().toString();
//
//                Map setValueAdmin = new HashMap();
//                setValueAdmin.put("Book_Title", b_Book_Title);
//                setValueAdmin.put("Reserved_By", adminEmail);

                reserveBook(b_Book_Title, c_Name_By, d_Book_Contributor, e_Book_MaterialType, f_Book_Publisher, g_Book_Edition, h_Book_Description, i_Book_Subject, j_Book_CallNumber, k_Book_CopyNumber);
                alertDialog.dismiss();


            }
        });
    }

    private boolean reserveBook( String b_Book_Title, String c_Name_By, String d_Book_Contributor, String e_Book_MaterialType, String f_Book_Publisher, String g_Book_Edition, String h_Book_Description, String i_Book_Subject, String j_Book_CallNumber, String k_Book_CopyNumber)
    {

//        String user_id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference resBook = FirebaseDatabase.getInstance().getReference("Book_Reserved").child(b_Book_Title);

//        String adminEmail = txtUserEmail.getText().toString();
//
//        Map setValueAdmin = new HashMap();
//        setValueAdmin.put("Book_Title", b_Book_Title);
//        setValueAdmin.put("Reserved_By", adminEmail);


        FirebaseUser user = firebaseAuth.getCurrentUser();

          resBook.setValue(user.getEmail());

        Toast.makeText(getActivity(), "Book Reserved Successful, Thank You!", Toast.LENGTH_LONG).show();

        return true;
    }


    public ReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservation, container, false);
    }

}
