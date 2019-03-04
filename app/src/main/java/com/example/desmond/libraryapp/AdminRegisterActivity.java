package com.example.desmond.libraryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AdminRegisterActivity extends AppCompatActivity {

    private EditText inputUsernameAdmin, inputPasswordAdmin, inputConfirmPasswordAdmin, inputFullNameAdmin;
    private Button signupButtonAdmin, linkButtonAdmin;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        inputUsernameAdmin = (EditText) findViewById(R.id.txtAdminEmail);
        inputPasswordAdmin = (EditText) findViewById(R.id.txtAdminPassword);
        inputConfirmPasswordAdmin = (EditText) findViewById(R.id.txtAdminConfirmPassword);
        signupButtonAdmin = (Button) findViewById(R.id.btnAdminRegister);
//        linkButtonAdmin = (Button) findViewById(R.id.btnAdminLinkToLogin);
        inputFullNameAdmin = (EditText) findViewById(R.id.txtAdminFullName);

        mProgressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

//        linkButtonAdmin.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View L)
//            {
//                startActivity(new Intent(AdminRegisterActivity.this, AdminMainActivity.class));
//            }
//        });

        signupButtonAdmin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String usernameUser, passwordUser, confirmpasswordUser, emailUser, fullnameUser;

                usernameUser = inputUsernameAdmin.getText().toString().trim();
                passwordUser = inputPasswordAdmin.getText().toString().trim();
                confirmpasswordUser = inputConfirmPasswordAdmin.getText().toString().trim();
                fullnameUser = inputFullNameAdmin.getText().toString().trim();

                if (usernameUser.isEmpty())
                {
                    inputUsernameAdmin.setError("Email is required!");
                    inputUsernameAdmin.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(usernameUser).matches())
                {
                    inputUsernameAdmin.setError("Please enter a valid email address!");
                    inputUsernameAdmin.requestFocus();
                    return;
                }

                if (passwordUser.isEmpty())
                {
                    inputPasswordAdmin.setError("Password is required!");
                    inputPasswordAdmin.requestFocus();
                    return;
                }

                if (inputPasswordAdmin.getText().length() < 6)
                {
                    inputPasswordAdmin.setError("Minimum 6 digits required!");
                    inputPasswordAdmin.requestFocus();
                    return;
                }

                if (fullnameUser.isEmpty())
                {
                    inputFullNameAdmin.setError("Full Name is required!");
                    inputFullNameAdmin.requestFocus();
                    return;
                }


                mProgressDialog.setTitle("Create Account");
                mProgressDialog.setMessage("Wait while the account is being created..");
                mProgressDialog.show();

                createUserAccountAdmin();
            }
        });

        inputPasswordAdmin.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(inputPasswordAdmin.getText().length() < 6)
                {
                    inputPasswordAdmin.setError("Minimum 6 digits required");
                }
            }
        });

        inputConfirmPasswordAdmin.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(inputConfirmPasswordAdmin.getText() != inputPasswordAdmin.getText())
                {
                    inputConfirmPasswordAdmin.setError("Confirm Paswword doesn't macth with Password");
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
            Intent AR = new Intent(AdminRegisterActivity.this, AdminMainActivity.class);
            startActivity(AR);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createUserAccountAdmin()
    {
        String usernameUser, passwordUser, confirmpasswordUser, emailUser, fullnameUser;

        usernameUser = inputUsernameAdmin.getText().toString().trim();
        passwordUser = inputPasswordAdmin.getText().toString().trim();
        confirmpasswordUser = inputConfirmPasswordAdmin.getText().toString().trim();
        fullnameUser = inputFullNameAdmin.getText().toString().trim();

        if (!TextUtils.isEmpty(usernameUser) && !TextUtils.isEmpty(passwordUser) && !TextUtils.isEmpty(confirmpasswordUser)  && !TextUtils.isEmpty(fullnameUser))
        {
            mAuth.createUserWithEmailAndPassword(usernameUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Admins").child(user_id);

                        String adminEmail = inputUsernameAdmin.getText().toString();
                        String adminPassword = inputPasswordAdmin.getText().toString();
                        String adminFullname = inputFullNameAdmin.getText().toString();

                        Map setValueAdmin = new HashMap();
                        setValueAdmin.put("Email", adminEmail);
                        setValueAdmin.put("Password", adminPassword);
                        setValueAdmin.put("Full Name", adminFullname);

                        Toast.makeText(AdminRegisterActivity.this, "Account Created Success", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();

                        Intent moveToHome = new Intent(AdminRegisterActivity.this, AdminMainActivity.class);
                        moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(moveToHome);

                        current_user_db.setValue(setValueAdmin);
                    }else
                    {
                        Toast.makeText(AdminRegisterActivity.this, "Account Creation Failed", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                }
            });
        }else
        {
            Toast.makeText(AdminRegisterActivity.this, "All the information are required to fill up", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();
        }

    }

}
