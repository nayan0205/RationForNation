package com.example.rationfornation.UI.LeftNavigation;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rationfornation.R;

public class Logout extends AppCompatActivity {
    Button btnCancel,btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);  //setting layout for logout activity
        setTitle("Logout");
        btnLogout=(Button)findViewById(R.id.btn_logout);
        btnCancel=(Button)findViewById(R.id.btn_cancel);
    }
}