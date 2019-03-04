package com.example.desmond.libraryapp;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText inputUsername, inputPassword, inputConfirmPassword;
    private Button usernameButton, signupButton, loginButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        inputUsername = (EditText) findViewById(R.id.txtUsername);
        inputPassword = (EditText) findViewById(R.id.txtPassword);
        inputConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        usernameButton = (Button) findViewById(R.id.btnUsername);
        signupButton = (Button) findViewById(R.id.btnRegister);
        loginButton = (Button) findViewById(R.id.btnlinkToLogin);


        mProgressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null)
                {
                    Intent moveToHome = new Intent(RegisterUserActivity.this, DrawerActivity.class);
                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(moveToHome);
                }
            }
        };

        mAuth.addAuthStateListener(mAuthListener);

        usernameButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(RegisterUserActivity.this, UsernameInstructionActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(RegisterUserActivity.this, LoginActivity.class));
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String usernameUser, passwordUser, confirmpasswordUser, emailUser;

                usernameUser = inputUsername.getText().toString().trim();
                passwordUser = inputPassword.getText().toString().trim();
                confirmpasswordUser = inputConfirmPassword.getText().toString().trim();

                if (usernameUser.isEmpty())
                {
                    inputUsername.setError("Email is required!");
                    inputUsername.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(usernameUser).matches())
                {
                    inputUsername.setError("Please enter a valid email address!");
                    inputUsername.requestFocus();
                    return;
                }

                if (passwordUser.isEmpty())
                {
                    inputPassword.setError("Password is required!");
                    inputPassword.requestFocus();
                    return;
                }

                if (inputPassword.getText().length() < 6)
                {
                    inputPassword.setError("Minimum 6 digits required!");
                    inputPassword.requestFocus();
                    return;
                }

//                if (inputConfirmPassword.getText() != inputPassword)
//                {
//                    inputConfirmPassword.setError("Confirm Paswword doesn't macth with Password!");
//                    inputConfirmPassword.requestFocus();
//                    return;
//                }

//                inputUsername.setOnFocusChangeListener(new View.OnFocusChangeListener()
//                {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus)
//                    {
//                        if(inputUsername.getText().toString().isEmpty())
//                        {
//                            inputUsername.setError("e.g TPxxxxxx@mail.apu.edu.my");
//                        }
//                    }
//                });
//
//                inputPassword.setOnFocusChangeListener(new View.OnFocusChangeListener()
//                {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus)
//                    {
//                        if(inputPassword.getText().length() < 6)
//                        {
//                            inputPassword.setError("Minimum 6 digits required");
//                        }
//                    }
//                });

                mProgressDialog.setTitle("Create Account");
                mProgressDialog.setMessage("Wait while the account is being created..");
                mProgressDialog.show();

                createUserAccount();
            }
        });

//        inputUsername.setOnFocusChangeListener(new View.OnFocusChangeListener()
//        {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus)
//            {
//                if(inputUsername.getText().toString().isEmpty())
//                {
//                    inputUsername.setError("e.g TPxxxxxx@mail.apu.edu.my");
//                }
//            }
//        });

        inputPassword.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(inputPassword.getText().length() < 6)
                {
                    inputPassword.setError("Minimum 6 digits required");
                }
            }
        });

        inputConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(inputConfirmPassword.getText() != inputPassword.getText())
                {
                    inputConfirmPassword.setError("Confirm Paswword doesn't macth with Password");
                }
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        mAuth.removeAuthStateListener(mAuthListener);
    }

    private void createUserAccount()
    {
        String usernameUser, passwordUser, confirmpasswordUser, emailUser;

        usernameUser = inputUsername.getText().toString().trim();
        passwordUser = inputPassword.getText().toString().trim();
        confirmpasswordUser = inputConfirmPassword.getText().toString().trim();


        if (!TextUtils.isEmpty(usernameUser) && !TextUtils.isEmpty(passwordUser) && !TextUtils.isEmpty(confirmpasswordUser))
        {
            mAuth.createUserWithEmailAndPassword(usernameUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                        String adminEmail = inputUsername.getText().toString();
                        String adminPassword = inputPassword.getText().toString();

                        Map setValueAdmin = new HashMap();
                        setValueAdmin.put("Email", adminEmail);
                        setValueAdmin.put("Password", adminPassword);

                        Toast.makeText(RegisterUserActivity.this, "Account Created Success", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();

                        Intent moveToHome = new Intent(RegisterUserActivity.this, DrawerActivity.class);
                        moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(moveToHome);

                        Toast.makeText(getApplicationContext(),getString(R.string.auth_login), Toast.LENGTH_LONG).show();

                        current_user_db.setValue(setValueAdmin);
                    }else
                    {
                        Toast.makeText(RegisterUserActivity.this, "Account Creation Failed", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                }
            });
        }else
        {
            Toast.makeText(RegisterUserActivity.this, "All the information are required to fill up", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();
        }

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
            Intent L = new Intent(RegisterUserActivity.this, LoginActivity.class);
            startActivity(L);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



//    private void createUserAccount()
//    {
//        final String usernameUser, passwordUser, confirmpasswordUser, emailUser;
//
//        usernameUser = inputUsername.getText().toString().trim();
//        passwordUser = inputPassword.getText().toString().trim();
//        confirmpasswordUser = inputConfirmPassword.getText().toString().trim();
//        emailUser = inputEmail.getText().toString().trim();
//
//        Query usernameQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").equalTo(usernameUser);
//        usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getChildrenCount() > 0)
//                {
//                    Toast.makeText(RegisterUserActivity.this, "Choose a different username", Toast.LENGTH_SHORT).show();
//                }else
//                {
//                    mAuth.createUserWithEmailAndPassword(usernameUser, passwordUser).addOnCompleteListener(RegisterUserActivity.this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (!task.isSuccessful())
//                            {
//                                Toast.makeText(RegisterUserActivity.this, "Sign Up Error", Toast.LENGTH_SHORT).show();
//                            }else
//                            {
//                                String user_id = mAuth.getCurrentUser().getUid();
//                                final DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
//
//                                final String username = inputUsername.getText().toString();
//                                final String password = inputPassword.getText().toString();
//                                final String email = inputEmail.getText().toString();
//
//                                Map newPost = new HashMap();
//                                newPost.put("Username", username);
//                                newPost.put("Password", password);
//                                newPost.put("Email", email);
//
//                                current_user_db.setValue(newPost);
//                            }
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }

}
