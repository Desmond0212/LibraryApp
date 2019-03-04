package com.example.desmond.libraryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class QRCodeScannerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;

    TextView userEmail;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        View headerView = navigationView.getHeaderView(0);
//        userEmail = (TextView) headerView.findViewById(R.id.txtuseremail);
//        userEmail.setText(getIntent().getExtras().getString("EMAIL4"));

//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
////        setDataToView(user);
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user == null) {
//                    // user auth state is changed - user is null
//                    // launch login activity
//                    startActivity(new Intent(QRCodeScannerActivity.this, LoginActivity.class));
//                    finish();
//                }
//            }
//        };
    //    mAuth.addAuthStateListener(mAuthListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

//    @SuppressLint("SetTextI18n")
//    private void setDataToView(FirebaseUser user) {
//
//        userEmail.setText("User Email: " + user.getEmail());
//    }

//    // this listener will be called when there is change in firebase user session
//    FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
////        @SuppressLint("SetTextI18n")
//        @Override
//        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//            FirebaseUser user = firebaseAuth.getCurrentUser();
//            if (user == null) {
//                // user auth state is changed - user is null
//                // launch login activity
//                startActivity(new Intent(QRCodeScannerActivity.this, LoginActivity.class));
//                finish();
////            } else {
////                setDataToView(user);
//
//            }
//        }
//
//
//    };
//
//    // this listener will be called when there is change in firebase user session
//    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
//        @Override
//        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//            FirebaseUser user = firebaseAuth.getCurrentUser();
//            if (user == null) {
//                // user auth state is changed - user is null
//                // launch login activity
//                startActivity(new Intent(QRCodeScannerActivity.this, LoginActivity.class));
//                finish();
//            }
//        }
//    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent a = new Intent(QRCodeScannerActivity.this, ManageActivity.class);
            startActivity(a);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        //check if user signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            sendToStart();
        }
    }

    private void sendToStart()
    {
        Intent startIntent = new Intent (QRCodeScannerActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//    }
//
//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    protected void onStop()
//    {
//        super.onStop();
//
//        mAuth.removeAuthStateListener(mAuthListener);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        switch (id)
//        {
//            case R.id.fragment_drawer:
//                Intent h = new Intent(QRCodeScannerActivity.this, DrawerActivity.class);
////                NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_search);
////                View headerView1 = navigationView1.getHeaderView(0);
////                userEmail = (TextView) headerView1.findViewById(R.id.txtuseremail);
////                userEmail.setText(getIntent().getExtras().getString("EMAIL"));
//                startActivity(h);
//                break;
//            case R.id.nav_summary:
//                Intent s = new Intent(QRCodeScannerActivity.this, SummaryActivity.class);
////                NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_summary);
////                View headerView2 = navigationView2.getHeaderView(0);
////                userEmail = (TextView) headerView2.findViewById(R.id.txtuseremail);
////                userEmail.setText(getIntent().getExtras().getString("EMAIL"));
//                startActivity(s);
//                break;
//            case R.id.nav_renew:
//                Intent r = new Intent(QRCodeScannerActivity.this, RenewActivity.class);
////                NavigationView navigationView3 = (NavigationView) findViewById(R.id.nav_renew);
////                View headerView3 = navigationView3.getHeaderView(0);
////                userEmail = (TextView) headerView3.findViewById(R.id.txtuseremail);
////                userEmail.setText(getIntent().getExtras().getString("EMAIL"));
//                startActivity(r);
//                break;
//            case R.id.nav_scanner:
//                Intent q = new Intent(QRCodeScannerActivity.this, QRCodeScannerActivity.class);
////                NavigationView navigationView4 = (NavigationView) findViewById(R.id.nav_scanner);
////                View headerView4 = navigationView4.getHeaderView(0);
////                userEmail = (TextView) headerView4.findViewById(R.id.txtuseremail);
////                userEmail.setText(getIntent().getExtras().getString("EMAIL"));
//                startActivity(q);
//                break;
//            case R.id.nav_message:
//                Intent m = new Intent(QRCodeScannerActivity.this, MessageActivity.class);
////                NavigationView navigationView5 = (NavigationView) findViewById(R.id.nav_message);
////                View headerView5 = navigationView5.getHeaderView(0);
////                userEmail = (TextView) headerView5.findViewById(R.id.txtuseremail);
////                userEmail.setText(getIntent().getExtras().getString("EMAIL"));
//                startActivity(m);
//                break;
//            case R.id.nav_resetpassword:
//                Intent p = new Intent(QRCodeScannerActivity.this, ResetPasswordActivity.class);
////                NavigationView navigationView6 = (NavigationView) findViewById(R.id.nav_resetpassword);
////                View headerView6 = navigationView6.getHeaderView(0);
////                userEmail = (TextView) headerView6.findViewById(R.id.txtuseremail);
////                userEmail.setText(getIntent().getExtras().getString("EMAIL"));
//                startActivity(p);
//                break;
//            case R.id.nav_help:
//                Intent H = new Intent(QRCodeScannerActivity.this, HelpActivity.class);
////                NavigationView navigationView6 = (NavigationView) findViewById(R.id.nav_view);
////                View headerView6 = navigationView6.getHeaderView(0);
////                userEmail = (TextView) findViewById(R.id.txtuseremail);
////                userEmail.setText(getIntent().getExtras().getString("EMAIL6"));
//                startActivity(H);
//                break;
//            case R.id.nav_logout:
//      //          mAuth.signOut();
////                Intent moveToMain = new Intent(QRCodeScannerActivity.this, LoginActivity.class);
////                //moveToMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                moveToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                startActivity(moveToMain);
//                FirebaseAuth.getInstance().signOut();
//                sendToStart();
//                break;
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
