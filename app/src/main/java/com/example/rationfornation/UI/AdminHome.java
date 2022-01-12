package com.example.rationfornation.UI;

import android.content.Context;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rationfornation.Adapter.ViewAdapter;
import com.example.rationfornation.MainActivity;
import com.example.rationfornation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdminHome extends Fragment {
    public Context context;
    View mview;
    RecyclerView mRecyclerView;
    DatabaseReference databaseReference;
    String phoneNumber, adminname;
    String fpsID;
    private  AdminDetails details;

//    String name[] = {"Nayan", "Swaraj","Vaibhav"};
//    String cotact[] ={"123455", "8999999","234567"};

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> cotact = new ArrayList<>();

    public  AdminHome(String PhoneNumber)
    {
        context = getContext();
        this.phoneNumber = PhoneNumber;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       databaseReference = FirebaseDatabase.getInstance()
               .getReferenceFromUrl("https://rationfornation-b5a0e-default-rtdb.firebaseio.com/");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.admin_home, container, false);
        return mview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity())                  //To set the Hamburger button on this page
                .setHamburgerIcon();
       // ((MainActivity)getActivity()).clickMenu(view);
        mRecyclerView =  view.findViewById(R.id.recyclerView1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                fpsID = dataSnapshot.child("admin").child(phoneNumber).child("fps id").getValue(String.class);
                adminname = dataSnapshot.child("admin").child(phoneNumber).child("full name").getValue(String.class);
                details.send(phoneNumber,adminname);
                Log.d("fps id", fpsID+"");
                for(DataSnapshot data : dataSnapshot.child("customer").getChildren())
                {
                      if((data.child("fps id").getValue(String.class)).equals(fpsID))
                      {
                          cotact.add(data.getKey());
                          name.add(data.child("full name").getValue(String.class));
                      }
                }
                Log.d("contact",cotact+" "+ name+"");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

            }
        });


        ViewAdapter c = new ViewAdapter(name,cotact);
        mRecyclerView.setAdapter(c);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        if(context instanceof AdminDetails)
        {
            details = (AdminDetails)context;
        }
        else
        {
            Log.d("NOT ATTACHED","NOT ATTACHED");
        }
        this.context = context;
    }

    public interface  AdminDetails{
        void send(String contact,String name);
    }
}
