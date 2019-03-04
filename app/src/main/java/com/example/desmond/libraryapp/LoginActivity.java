package com.example.desmond.libraryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private ImageView backButton;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button forgotButton, registerButton;
    LinearLayout loginButton;
    private EditText emailLogin, passwordLogin;
    private DatabaseReference fire_db_user;
    private DatabaseReference fire_db_admin;
    private ProgressDialog progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null)
        {
            startActivity(new Intent(LoginActivity.this, DrawerActivity.class));
            finish();
        }

        forgotButton = (Button) findViewById(R.id.btnForgotPassword);
        registerButton = (Button) findViewById(R.id.btnlinkToRegister);
        loginButton = (LinearLayout) findViewById(R.id.btnLogin);
        emailLogin = (EditText) findViewById(R.id.emailLoginEditText);
        passwordLogin = (EditText) findViewById(R.id.passwordLoginEditText);

        progress_bar = new ProgressDialog(this);

        fire_db_user = FirebaseDatabase.getInstance().getReference().child("Users");
        fire_db_user.keepSynced(true);

        fire_db_admin = FirebaseDatabase.getInstance().getReference().child("Admins");
        fire_db_admin.keepSynced(true);

        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, RegisterUserActivity.class));
            }
        });

        forgotButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String email = emailLogin.getText().toString();
                final String password = passwordLogin.getText().toString();

                if (email.isEmpty())
                {
                    emailLogin.setError("Email is required!");
                    emailLogin.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    emailLogin.setError("Please enter a valid email address!");
                    emailLogin.requestFocus();
                    return;
                }

                if (password.isEmpty())
                {
                    passwordLogin.setError("Password is required!");
                    passwordLogin.requestFocus();
                    return;
                }

                checkLogin();

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
            Intent M = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(M);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void dialogBox(String txt)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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

    public void checkLogin(){

        String username = this.emailLogin.getText().toString().trim();
        String password = this.passwordLogin.getText().toString().trim();

        progress_bar.setMessage("We are checking your credential... please be patient...");

        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            progress_bar.show();
            auth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        check_user_exist();
                        progress_bar.dismiss();

                    }else{

                        dialogBox("Invalid Email or Password, please try again!");
                        progress_bar.dismiss();
                    }
                }
            });


        }else{

            dialogBox("Sorry! Please fill in both email and password!");
        }
    }

    public void check_user_exist(){

        if(auth.getCurrentUser()!=null) {

            final String uid = auth.getCurrentUser().getUid().toString();

            fire_db_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChild(uid)) {
                        Intent mainintent = new Intent(LoginActivity.this, DrawerActivity.class);
                        mainintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainintent);

                        Toast.makeText(getApplicationContext(),getString(R.string.auth_login), Toast.LENGTH_LONG).show();

//bring legit user to main activity

                    }else{

                        Intent setup_intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                        setup_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(setup_intent);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
