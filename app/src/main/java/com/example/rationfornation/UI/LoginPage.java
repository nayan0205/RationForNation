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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
 * Use the {@link LoginPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    private Context context;
    Button Login;
    EditText phone, password;
    DatabaseReference databaseReference;


    private TextView SignUp, passwordRecovery;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginPage() {
        // Required empty public constructor


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginPage.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginPage newInstance(String param1, String param2) {
        LoginPage fragment = new LoginPage();
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
//        clearBackstack();
        databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://rationfornation-b5a0e-default-rtdb.firebaseio.com/");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount()>0)
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.login_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         TextView SignUp = view.findViewById(R.id.signup1);
         phone = view.findViewById(R.id.PhoneNumber);
         password =  view.findViewById(R.id.password);

         Login = view.findViewById(R.id.login);
         passwordRecovery = view.findViewById(R.id.forgetPassword);


        passwordRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager  = ((MainActivity)context).getSupportFragmentManager();
                 PasswordRecovery fragment = new PasswordRecovery();
                fragmentManager.beginTransaction().replace(R.id.windowFrame,fragment).addToBackStack(null).commit();
            }
        });

         SignUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FragmentManager fragmentManager  = ((MainActivity)context).getSupportFragmentManager();
                 AdminCustomer fragment = new AdminCustomer();
                 fragmentManager.beginTransaction().replace(R.id.windowFrame,fragment).addToBackStack(null).commit();
             }
         });

         Login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 final String PhoneTxt = phone.getText().toString();
                 final String PasswordTxt = password.getText().toString();

                 if(PhoneTxt.isEmpty() || PasswordTxt.isEmpty())
                 {
                     Toast.makeText( context,"please enter your phone number or password",Toast.LENGTH_SHORT).show();
                 }
                 else
                 {
                        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child("admin").hasChild(PhoneTxt)) {
                                    final String getPassword = dataSnapshot.child("admin").child(PhoneTxt).child("password").getValue(String.class);
                                        if (getPassword.equals(PasswordTxt)) {
                                            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                                            AdminHome fragment = new AdminHome(PhoneTxt);
                                            fragmentManager.beginTransaction().replace(R.id.windowFrame, fragment).commit();


                                           Log.d("admin phonetxt",PhoneTxt+"");
                                        } else {
                                            Toast.makeText(context, "incorrect password", Toast.LENGTH_SHORT).show();
                                        }
                                }
                                else if(dataSnapshot.child("customer").hasChild(PhoneTxt))
                                {
                                    final String getPassword = dataSnapshot.child("customer").child(PhoneTxt).child("password").getValue(String.class);
                                    if(getPassword.equals(PasswordTxt))
                                    {
                                        FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                                        CustomerHome fragment = new CustomerHome(PhoneTxt);
                                        fragmentManager.beginTransaction().replace(R.id.windowFrame, fragment).commit();
                                    }
                                    else {
                                        Toast.makeText(context, "incorrect password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(context,"incorrect phone number or password",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

                            }
                        });
                 }
             }
         });
    }
//    public void clearBackstack() {
//
//        FragmentManager.BackStackEntry entry = ((MainActivity)context).getSupportFragmentManager().getBackStackEntryAt(
//                0);
//        ((MainActivity)context).getSupportFragmentManager().popBackStack(entry.getId(),
//                FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        ((MainActivity)context).getSupportFragmentManager().executePendingTransactions();
//
//    }
}