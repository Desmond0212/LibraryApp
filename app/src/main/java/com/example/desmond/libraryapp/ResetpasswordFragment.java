package com.example.desmond.libraryapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.desmond.libraryapp.R.id.txtPasswordRP;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetpasswordFragment extends Fragment {

    private EditText inputNewPassowrd, inputConfirmPassword;
    private Button doneButton;
    FirebaseAuth auth;
    ProgressDialog dialog;

    private DatabaseReference userPasswordDB;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        inputNewPassowrd = (EditText) getView().findViewById(R.id.txtPasswordRP);
        inputConfirmPassword = (EditText) getView().findViewById(R.id.txtConfirmPasswordRP);
        doneButton = (Button) getView().findViewById(R.id.btn_reset_passwordFra);

        dialog = new ProgressDialog(getActivity());

        userPasswordDB = FirebaseDatabase.getInstance().getReference("Users");

        auth = FirebaseAuth.getInstance();

        doneButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userPasswordDB = FirebaseDatabase.getInstance().getReference("Users");

                String newPasswordUserMain, confirmPasswordUserMain;

                newPasswordUserMain = inputNewPassowrd.getText().toString().trim();
                confirmPasswordUserMain = inputConfirmPassword.getText().toString().trim();

                if (newPasswordUserMain.isEmpty())
                {
                    inputNewPassowrd.setError("New Password is required!");
                    inputNewPassowrd.requestFocus();
                    return;
                }


                if (confirmPasswordUserMain.isEmpty())
                {
                    inputConfirmPassword.setError("Confirm Password is required!");
                    inputConfirmPassword.requestFocus();
                    return;
                }

                if (inputNewPassowrd.getText().length() < 6)
                {
                    inputNewPassowrd.setError("Minimum 6 digits required!");
                    inputNewPassowrd.requestFocus();
                    return;
                }


                change();
//                newPasswordUserMain, confirmPasswordUserMain
            }
        });

        inputNewPassowrd.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(inputNewPassowrd.getText().length() < 6)
                {
                    inputNewPassowrd.setError("Minimum 6 digits re quired");
                }
            }
        });

        inputConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(inputConfirmPassword.getText() != inputNewPassowrd.getText())
                {
                    inputConfirmPassword.setError("Confirm Paswword doesn't macth with Password");
                }
            }
        });


    }

    private void change ()
    {
//        final String newPasswordUserMain, final String confirmPasswordUserMain
        userPasswordDB = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {
            dialog.setMessage("Updating password, please wait...");
            dialog.show();

            user.updatePassword(inputNewPassowrd.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                       @Override
                        public void onComplete(Task<Void> task)
                       {
                           if(task.isSuccessful())
                           {
//                              userPasswordDB.child("Password").setValue(newPasswordUserMain);

                               dialog.dismiss();
                               Toast.makeText(getActivity().getApplicationContext(), "Your password has been updated", Toast.LENGTH_LONG).show();

                               Intent backtoMain = new Intent(getActivity(), DrawerActivity.class);
                               backtoMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               startActivity(backtoMain);

                           }else
                           {
                               dialog.dismiss();
                               Toast.makeText(getActivity().getApplicationContext(), "Password could not be updated", Toast.LENGTH_LONG).show();
                           }
                       }
                    });
        }
    }


    public ResetpasswordFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resetpassword, container, false);

    }

}
