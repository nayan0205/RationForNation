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
import java.util.regex.*;

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
 * Use the {@link AdminSignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminSignUp extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context context;
    DatabaseReference databaseReference;
    Button SignUp;
    View view;
    EditText phoneNumber,fpsID,Password,ConfirPassword,name;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminSignUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminSignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminSignUp newInstance(String param1, String param2) {
        AdminSignUp fragment = new AdminSignUp();
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
        return view = inflater.inflate(R.layout.admin_signup, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      phoneNumber = view.findViewById(R.id.PhoneNumber1);
      fpsID = view.findViewById(R.id.shopnumber);
      name = view.findViewById(R.id.adminname);
      Password = view.findViewById(R.id.password1);
      ConfirPassword = view.findViewById(R.id.confirmpassword);
      SignUp = view.findViewById(R.id.signup2);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneNumberTXT = phoneNumber.getText().toString();
                final String fpsIDTXT = fpsID.getText().toString();
                final String nameTXT = name.getText().toString();
                final String PasswordTXT = Password.getText().toString();
                final String ConfirPasswordTXT = ConfirPassword.getText().toString();

                if(phoneNumberTXT.isEmpty() || fpsIDTXT.isEmpty() || nameTXT.isEmpty() || PasswordTXT.isEmpty()|| ConfirPasswordTXT.isEmpty())
                {
                    Toast.makeText(context,"please enter all details",Toast.LENGTH_SHORT).show();
                }
                else if(phoneNumberTXT.length()<10 || phoneNumberTXT.length()>10 || !isValidMobileNo(phoneNumberTXT))
                {
                    Toast.makeText(context,"please enter valid number",Toast.LENGTH_SHORT).show();
                }
                else if(!android.text.TextUtils.isDigitsOnly(fpsIDTXT) || fpsIDTXT.length()<12 || fpsIDTXT.length()>12)
                {
                    Toast.makeText(context,"please enter valid FPS ID",Toast.LENGTH_SHORT).show();
                }
                else if(!isAlpha(nameTXT))
                {
                    Toast.makeText(context,"please enter valid name",Toast.LENGTH_SHORT).show();
                }
                else if(!PasswordTXT.equals(ConfirPasswordTXT))
                {
                    Toast.makeText(context,"password not matching",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("customer").hasChild(phoneNumberTXT))
                            {
                                Toast.makeText(context,"phone number already exist",Toast.LENGTH_SHORT).show();
                            }
                            else if(dataSnapshot.child("admin").hasChild(phoneNumberTXT))
                            {
                                Toast.makeText(context,"phone number already exist",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                databaseReference.child("users").child("admin").child(phoneNumberTXT).child("full name").setValue(nameTXT);
                                databaseReference.child("users").child("admin").child(phoneNumberTXT).child("fps id").setValue(fpsIDTXT);
                                databaseReference.child("users").child("admin").child(phoneNumberTXT).child("password").setValue(PasswordTXT);
                                Toast.makeText(context,"User register sucessfully",Toast.LENGTH_SHORT).show();
//                                finish();
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
            }
        });

    }
    public static boolean isValidMobileNo(String str)
    {
//(0/91): number starts with (0/91)
//[7-9]: starting of the number may contain a digit between 0 to 9
//[0-9]: then contains digits 0 to 9
        Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
//the matcher() method creates a matcher that will match the given input against this pattern
        Matcher match = ptrn.matcher(str);
//returns a boolean value
        return (match.find() && match.group().equals(str));
    }
    public boolean isAlpha(String name) {
        return name.matches("^[ A-Za-z]+$");
    }
}

