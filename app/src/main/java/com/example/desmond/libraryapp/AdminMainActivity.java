package com.example.desmond.libraryapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminMainActivity extends AppCompatActivity {

    LinearLayout registerAdminButton, logoutAdminButton, addItemAdminButton, searchItemAdminButton, updateItemAdminButton, removeItemAdminButton, removeAdminButton;
    private FirebaseAuth auth;

    private ProgressDialog progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        registerAdminButton = (LinearLayout) findViewById(R.id.btnRegisterAdmin);
        logoutAdminButton = (LinearLayout) findViewById(R.id.btnLogoutAdmin);
        addItemAdminButton = (LinearLayout) findViewById(R.id.btnAddNewItemAdmin);
        searchItemAdminButton = (LinearLayout) findViewById(R.id.btnSearchAdmin);
        updateItemAdminButton = (LinearLayout) findViewById(R.id.btnUpdateItemAdmin);
        removeItemAdminButton = (LinearLayout) findViewById(R.id.btnRemoveItemAdmin);
        removeAdminButton = (LinearLayout) findViewById(R.id.btnremoveAdmin);

        progress_bar = new ProgressDialog(this);

        addItemAdminButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View r)
            {
                Intent I = new Intent(AdminMainActivity.this, AdminAddItemActivity.class);
                startActivity(I);
                finish();
            }
        });

        registerAdminButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View r)
            {
                Intent R = new Intent(AdminMainActivity.this, AdminRegisterActivity.class);
                startActivity(R);
                finish();
            }
        });

        searchItemAdminButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View r)
            {
                Intent S = new Intent(AdminMainActivity.this, AdminSearchActivity.class);
                startActivity(S);
                finish();
            }
        });

        updateItemAdminButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View r)
            {
                Intent U = new Intent(AdminMainActivity.this, AdminUpdateItemActivity.class);
                startActivity(U);
                finish();
            }
        });

        removeItemAdminButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View r)
            {
                Intent R = new Intent(AdminMainActivity.this, AdminRemoveItemActivity.class);
                startActivity(R);
                finish();
            }
        });


        logoutAdminButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent moveToLogin = new Intent(AdminMainActivity.this, LoginActivity.class);
                FirebaseAuth.getInstance().signOut();
                sendToStart();
            }
        });

        removeAdminButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                if (user != null)
//                {
//                    user.delete()
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful())
//                                    {
//                                        Toast.makeText(AdminMainActivity.this, "Library Admin has been Removed!", Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(AdminMainActivity.this, LoginActivity.class));
//                                        finish();
//                                    }
//                                    else
//                                    {
//                                        Toast.makeText(AdminMainActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }

                deleteAdmin();

            }
        });


    }

    public void dialogBox(String txt)
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(txt);
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        if (user != null)
                        {
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {

                                                Toast.makeText(AdminMainActivity.this, "Library Admin has been Removed!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(AdminMainActivity.this, LoginActivity.class));
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(AdminMainActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }

                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void deleteAdmin()
    {
        dialogBox("Are you sure you want to remove this account?");

        String user_id = auth.getCurrentUser().getUid();
        DatabaseReference revBook = FirebaseDatabase.getInstance().getReference("Admins").child(user_id);

        revBook.removeValue();

    }

//    public void deleteAdmin(){
//
//        progress_bar.setMessage("We are checking your credential... please be patient...");
//
//        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
//            progress_bar.show();
//            auth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(task.isSuccessful()){
//
//                        check_user_exist();
//                        progress_bar.dismiss();
//
//                    }else{
//
//                        dialogBox("Invalid Email or Password, please try again!");
//                        progress_bar.dismiss();
//                    }
//                }
//            });
//
//
//        }else{
//
//            dialogBox("Sorry! Please fill in both email and password!");
//        }
//    }

    @Override
    public void onStart()
    {
        super.onStart();
        //check if user signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser == null)
        {
            sendToStart();
        }
    }

    private void sendToStart()
    {
        Intent startIntent = new Intent (AdminMainActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

}
