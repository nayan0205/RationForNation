package com.example.rationfornation.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
 * Use the {@link PasswordRecovery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordRecovery extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText phoneNumber, password;
    Button submit;
    DatabaseReference databaseReference;
    Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PasswordRecovery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PasswordRecovery.
     */
    // TODO: Rename and change types and number of parameters
    public static PasswordRecovery newInstance(String param1, String param2) {
        PasswordRecovery fragment = new PasswordRecovery();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context = getContext();
        databaseReference = FirebaseDatabase.getInstance().
                getReferenceFromUrl("https://rationfornation-b5a0e-default-rtdb.firebaseio.com/");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password_recovery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phoneNumber = view.findViewById(R.id.PhoneNumber);
        password = view.findViewById(R.id.password);
        submit = view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String txt_phone = phoneNumber.getText().toString();
                final String txt_password = password.getText().toString();

                if(txt_phone.isEmpty() || txt_password.isEmpty())
                {
                    Toast.makeText(view.getContext(),"please fill all details",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("admin").hasChild(txt_phone))
                            {
                                databaseReference.child("users").child("admin").child(txt_phone).child("password")
                                        .setValue(txt_password);
                                Toast.makeText(view.getContext(),"password reset sucessfully",Toast.LENGTH_SHORT).show();

                                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                                LoginPage fragment = new LoginPage();


                                ///pass data
                                fragmentManager.beginTransaction().replace(R.id.windowFrame,fragment).commit();
                            }
                            else if(dataSnapshot.child("customer").hasChild(txt_phone))
                            {
                                databaseReference.child("users").child("customer").child(txt_phone).child("password")
                                        .setValue(txt_password);
                                Toast.makeText(view.getContext(),"password reset sucessfully",Toast.LENGTH_SHORT).show();

                                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                                LoginPage fragment = new LoginPage();


                                ///pass data
                                fragmentManager.beginTransaction().replace(R.id.windowFrame,fragment).commit();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

                        }
                    });
                }

                //else end
            }
        });

    }
}