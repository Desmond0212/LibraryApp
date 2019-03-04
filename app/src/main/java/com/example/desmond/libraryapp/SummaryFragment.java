package com.example.desmond.libraryapp;


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
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment {


//    private DatabaseReference databaseCancelBooks;
//    FirebaseUser firebaseUser;
//
//    ListView listViewBooksRemove;
//
//    //a list to store all the artist from firebase databse
//    List<BooksUpdate> books;
//
//    ArrayList<String> arrayList = new ArrayList<>();
//    ArrayAdapter<String> arrayAdapter;

    public SummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        databaseCancelBooks = FirebaseDatabase.getInstance().getReference("Book_Reserved");
//
//        books = new ArrayList<>();
//
//        databaseCancelBooks.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                books.clear();
//
//                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
//                {
//                    BooksUpdate booksUpdate = postSnapshot.getValue(BooksUpdate.class);
//
//                    books.add(booksUpdate);
//                }
//
//                //creating adapter
//                BooksList adapter = new BooksList(getActivity(), books);
//                //attaching adapter to the listview
//                listViewBooksRemove.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        listViewBooksRemove.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
////parent (adapterView)
//                BooksUpdate booksUpdate = books.get(i);
//
//                showUpdateDialog(booksUpdate.getA_Book_Id(), booksUpdate.getB_Book_Title(),  booksUpdate.getC_Name_By(), booksUpdate.getD_Book_Contributor(), booksUpdate.getE_Book_MaterialType(), booksUpdate.getF_Book_Publisher(), booksUpdate.getG_Book_Edition(), booksUpdate.getH_Book_Description(), booksUpdate.getI_Book_Subject(), booksUpdate.getJ_Book_CallNumber(), booksUpdate.getK_Book_CopyNumber());
//
//                return true;
//
//            }
//        });
    }

//    private void showUpdateDialog(final String a_Books_Id, final String b_Book_Title, String c_Name_By, String d_Book_Contributor, String e_Book_MaterialType, String f_Book_Publisher, String g_Book_Edition, String h_Book_Description, String i_Book_Subject, String j_Book_CallNumber, String k_Book_CopyNumber)
//    {
//
//        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Book_Details_Database").child(b_Book_Title);
////        databaseBooks = FirebaseDatabase.getInstance().getReference("Book_Details_Database");
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
//
//        LayoutInflater inflater = getLayoutInflater();
//
//        final View dialogView = inflater.inflate(R.layout.cancelreservation_dialog, null);
//
//        dialogBuilder.setView(dialogView);
//
////        final EditText editTextTitleRemove = (EditText) dialogView.findViewById(R.id.editTextTitleRemove);
////        final EditText editTextNameRemove = (EditText) dialogView.findViewById(R.id.editTextNameRemove);
////        final EditText editTextContributorRemove = (EditText) dialogView.findViewById(R.id.editTextContributorRemove);
////        final EditText editTextMaterialRemove = (EditText) dialogView.findViewById(R.id.editTextMaterialRemove);
////        final EditText editTextPublisherRemove = (EditText) dialogView.findViewById(R.id.editTextPublisherRemove);
////        final EditText editTextEditionRemove = (EditText) dialogView.findViewById(R.id.editTextEditionRemove);
////        final EditText editTextDescriptionRemove = (EditText) dialogView.findViewById(R.id.editTextDescriptionRemove);
////        final EditText editTextSubjectRemove = (EditText) dialogView.findViewById(R.id.editTextSubjectRemove);
////        final EditText editTextCallNumberRemove = (EditText) dialogView.findViewById(R.id.editTextCallNumberRemove);
////        final EditText editTextCopyNumberRemove = (EditText) dialogView.findViewById(R.id.editTextCopyNumberRemove);
//        final Button btn_RemoveItemAdmin = (Button) dialogView.findViewById(R.id.btn_RemoveItemAdmin);
//
//        dialogBuilder.setTitle("Delete Book's information from APU library catalogue");
//        dialogBuilder.setMessage("Title: " + b_Book_Title + "\n" + "Name (By): " + c_Name_By + "\n" + "Contributor(s): "
//                + d_Book_Contributor + "\n" + "Material Type: " + e_Book_MaterialType + "\n" + "Publisher(s): " + f_Book_Publisher
//                + "\n" + "Edition: " + g_Book_Edition + "\n" + "Description: " + h_Book_Description + "\n" + "Subject(s): " +
//                i_Book_Subject + "\n" + "Call Number: " + j_Book_CallNumber + "\n" + "Copy Number: " + k_Book_CopyNumber);
//
//        final AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.show();
//
//
//        btn_RemoveItemAdmin.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                deleteBook(b_Book_Title);
//                alertDialog.dismiss();
//            }
//        });
//
//
////        dialogBuilder.setTitle("Updating Book: " + b_Book_Title);
////
////        final AlertDialog alertDialog = dialogBuilder.create();
////        alertDialog.show();
//    }
//
//    private boolean deleteBook(String b_Book_Title)
//    {
//        DatabaseReference revBook = FirebaseDatabase.getInstance().getReference("Book_Reserved").child(b_Book_Title);
//
//        revBook.removeValue();
//
//        Toast.makeText(getActivity(), "Book's information deleted!", Toast.LENGTH_LONG).show();
//
//        return true;
//    }
//
//    private boolean updateBook(String id,  String title, String name, String contributor, String material, String publisher, String edition, String description, String subject, String callnumber, String copynumber)
//    {
////        databaseBooks = FirebaseDatabase.getInstance().getReference("Book_Details_Database").child(title);
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Book_Details_Database").child(title);
//
////        BooksUpdate booksUpdate = new BooksUpdate(id, title, name, contributor, material, publisher, edition, description, subject, callnumber, copynumber);
////
////        databaseReference.setValue(booksUpdate);
//
//
//        Toast.makeText(getActivity(), "Book has been Updated Successfully", Toast.LENGTH_LONG).show();
//
//        return true;
//
//    }

}
