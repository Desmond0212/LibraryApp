package com.example.desmond.libraryapp;

import android.annotation.SuppressLint;
//import android.app.FragmentManager;
import android.support.v4.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView, navigationView2, navigationView3, navigationView4, navigationView5, navigationView6, navigationView7;
    Toolbar toolbar = null;

    TextView userEmail;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText emailLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        userEmail = (TextView) findViewById(R.id.lbl_userLoggedIn);

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(DrawerActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

        userEmail.setText( user.getEmail());


    }

    // this listener will be called when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(DrawerActivity.this, LoginActivity.class));
                finish();
            } else {
                setDataToView(user);

            }
        }


    };





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.drawer, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent a = new Intent(DrawerActivity.this, ManageActivity.class);
//            startActivity(a);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onStart()
    {
        super.onStart();

        mAuth.addAuthStateListener(authListener);
        //check if user signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            sendToStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            mAuth.removeAuthStateListener(authListener);
        }
    }

    private void sendToStart()
    {
        Intent startIntent = new Intent (DrawerActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;

        int id = item.getItemId();
        if (id == R.id.mainMenu)
        {
            Intent H = new Intent (DrawerActivity.this, DrawerActivity.class);
            startActivity(H);
            finish();
        }else if (id == R.id.fragment_drawer)
        {
            fragment = new DrawerFragment();
        }else if (id == R.id.fragment_summary)
        {
            fragment = new SummaryFragment();
        }else if(id ==  R.id.fragment_reservation)
        {
            fragment = new ReservationFragment();
        }else if(id ==  R.id.fragment_renew)
        {
            fragment = new RenewFragment();
        }else if(id ==  R.id.fragment_message)
        {
            fragment = new MessageFragment();
        }else if(id ==  R.id.fragment_resetpassword)
        {
            fragment = new ResetpasswordFragment();
        }else if(id ==  R.id.fragment_help)
        {
            fragment = new HelpFragment();
        }else if(id ==  R.id.nav_logout)
        {
            Intent moveToMain = new Intent(DrawerActivity.this, LoginActivity.class);
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }

        if (fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.screen_area, fragment);
            ft.commit();
        }


//        switch (id)
//        {
//            case R.id.fragment_drawer:
////                Intent h = new Intent(DrawerActivity.this, DrawerActivity.class);
////                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
////                View headerView = navigationView.getHeaderView(0);
////                userEmail = (TextView) findViewById(R.id.txtuseremail);
//               // userEmail.setText(getIntent().getExtras().getString("EMAIL"));
////                startActivity(h);
////                break;
//
//            case R.id.nav_summary:
//                Intent s = new Intent(DrawerActivity.this, SummaryActivity.class);
////                NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view);
////                View headerView2 = navigationView2.getHeaderView(0);
////                userEmail = (TextView) findViewById(R.id.txtuseremail);
////                userEmail.setText(getIntent().getExtras().getString("EMAIL2"));
//                startActivity(s);
//                break;
//            case R.id.nav_renew:
//                Intent r = new Intent(DrawerActivity.this, RenewActivity.class);
////                NavigationView navigationView3 = (NavigationView) findViewById(R.id.nav_view);
////                View headerView3 = navigationView3.getHeaderView(0);
////                userEmail = (TextView) findViewById(R.id.txtuseremail);
////                userEmail.setText(getIntent().getExtras().getString("EMAIL3"));
//                startActivity(r);
//                break;
//            case R.id.nav_scanner:
//                Intent q = new Intent(DrawerActivity.this, QRCodeScannerActivity.class);
////                NavigationView navigationView4 = (NavigationView) findViewById(R.id.nav_view);
////                View headerView4 = navigationView4.getHeaderView(0);
////                userEmail = (TextView) findViewById(R.id.txtuseremail);
////                userEmail.setText(getIntent().getExtras().getString("EMAIL4"));
//                startActivity(q);
//                break;
//            case R.id.nav_message:
//                Intent m = new Intent(DrawerActivity.this, MessageActivity.class);
////                NavigationView navigationView5 = (NavigationView) findViewById(R.id.nav_view);
////                View headerView5 = navigationView5.getHeaderView(0);
////                userEmail = (TextView) findViewById(R.id.txtuseremail);
////                userEmail.setText(getIntent().getExtras().getString("EMAIL5"));
//                startActivity(m);
//                break;
//            case R.id.nav_resetpassword:
//                Intent p = new Intent(DrawerActivity.this, ResetPasswordActivity.class);
////                NavigationView navigationView6 = (NavigationView) findViewById(R.id.nav_view);
////                View headerView6 = navigationView6.getHeaderView(0);
////                userEmail = (TextView) findViewById(R.id.txtuseremail);
////                userEmail.setText(getIntent().getExtras().getString("EMAIL6"));
//                startActivity(p);
//                break;
//            case R.id.nav_help:
//                Intent H = new Intent(DrawerActivity.this, HelpActivity.class);
////                NavigationView navigationView6 = (NavigationView) findViewById(R.id.nav_view);
////                View headerView6 = navigationView6.getHeaderView(0);
////                userEmail = (TextView) findViewById(R.id.txtuseremail);
////                userEmail.setText(getIntent().getExtras().getString("EMAIL6"));
//                startActivity(H);
//                break;
//            case R.id.nav_logout:
//            //   mAuth.signOut();
////               Intent moveToMain = new Intent(DrawerActivity.this, LoginActivity.class);
////
////              //  moveToMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                moveToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                startActivity(moveToMain);
//
//                FirebaseAuth.getInstance().signOut();
//                sendToStart();
//                break;
//
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
