package com.example.rationfornation;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.rationfornation.UI.AdminHome;
import com.example.rationfornation.UI.AdminSignUp;
import com.example.rationfornation.UI.LeftNavigation.Logout;
import com.example.rationfornation.UI.LoginPage;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements AdminHome.AdminDetails {

//272005737771, 150400100002


    private DrawerLayout dl;
    private ActionBarDrawerToggle abdl;
    NavigationView nav_view;
    Context context;
    TextView txt_name, phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = findViewById(R.id.dl);
        abdl = new ActionBarDrawerToggle(this,dl, R.string.open, R.string.close);
        dl.addDrawerListener(abdl);
        abdl.syncState();
        context = getApplicationContext();
        Activity activity = new MainActivity();


        nav_view = findViewById(R.id.nav_view);

        View header = nav_view.getHeaderView(0);
        phoneNumber = header.findViewById(R.id.contact);
        txt_name = header.findViewById(R.id.name);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) { //for opening left navigation activities as per selection
                int id = menuItem.getItemId();
                if(id==R.id.logout){
//                    Intent intent = new Intent(MainActivity.this, Logout.class);
//                    startActivity(intent);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Logout");

                    //message
                    builder.setMessage("Are you sure you want to logout ?");

                    //setPositive yes
                    builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           activity.finishAffinity();
                           System.exit(0);
                        }
                    });
                    //setNegative button

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                             dialogInterface.dismiss();
                        }
                    });

                builder.show();
                }
                return true;

            }
        });
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        getSupportFragmentManager().beginTransaction().add(R.id.windowFrame, new LoginPage()).commit();
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



//    private void logout(MainActivity mainActivity) {
//        AlertDialog.Builder builder = new
//    }

        public void setHamburgerIcon()
       {
            abdl.setDrawerIndicatorEnabled(true);    // if true it is by default setting the hamburger icon
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                dl.openDrawer(GravityCompat.START);
//                return true;
//        }
        if (abdl.onOptionsItemSelected(item)) {
            return true;                                //For working of hamburger button
        }
        else{
            onBackPressed();      //For working of back button
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void send(String contact, String name) {
        txt_name.setText(name);
        phoneNumber.setText(contact);
        Log.d("mainActivity",""+contact+" "+name);
    }
}