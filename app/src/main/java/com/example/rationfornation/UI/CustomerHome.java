package com.example.rationfornation.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rationfornation.MainActivity;
import com.example.rationfornation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */
public class CustomerHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context context;
    ImageView transaction,notice,availability,rDetails;
    private AdminHome.AdminDetails mdetails;
    String phoneNumber,name;
    DatabaseReference database;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CustomerHome(String PhoneNumber) {
        context = getContext();
        this.phoneNumber = PhoneNumber;
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        database =  FirebaseDatabase.getInstance().
                getReferenceFromUrl("https://rationfornation-b5a0e-default-rtdb.firebaseio.com/");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.customer_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((MainActivity) getActivity())                  //To set the Hamburger button on this page
                .setHamburgerIcon();

        database.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
              name = dataSnapshot.child("customer").child(phoneNumber).child("full name").getValue(String.class);
                mdetails.send(phoneNumber,name);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

            }
        });

        Log.d("customer name",""+name);
        transaction = view.findViewById(R.id.transation);
        notice = view.findViewById(R.id.notice);
        availability = view.findViewById(R.id.availability);
        rDetails = view.findViewById(R.id.details);


        rDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                RationDetails fragment = new RationDetails();
                fragmentManager.beginTransaction().replace(R.id.windowFrame,fragment).addToBackStack(null).commit();
            }
        });

        availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                TotalAvailability fragment = new TotalAvailability();
                fragmentManager.beginTransaction().replace(R.id.windowFrame,fragment).addToBackStack(null).commit();
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                Notice fragment = new Notice();
                fragmentManager.beginTransaction().replace(R.id.windowFrame,fragment).addToBackStack(null).commit();
            }
        });

        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager =  ((MainActivity)context).getSupportFragmentManager();
                NewTransaction fragment = new NewTransaction();
                fragmentManager.beginTransaction().replace(R.id.windowFrame,fragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        if(context instanceof AdminHome.AdminDetails)
        {
            mdetails = (AdminHome.AdminDetails)context;
        }
        else
        {
            Log.d("NOT ATTACHED","NOT ATTACHED");
        }
        this.context = context;
    }
}