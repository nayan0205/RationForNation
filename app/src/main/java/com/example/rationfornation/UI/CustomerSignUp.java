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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerSignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerSignUp extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button SignUp;
    EditText phoneNumber, fullName,fpsID, rationNumber, password, confirmPassword;
    Context context;
    DatabaseReference databaseReference;

    public CustomerSignUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerSignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerSignUp newInstance(String param1, String param2) {
        CustomerSignUp fragment = new CustomerSignUp();
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
        return inflater.inflate(R.layout.customer_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       phoneNumber = view.findViewById(R.id.PhoneNumber2);
       fullName = view.findViewById(R.id.text_name);
       fpsID = view.findViewById(R.id.fpsID);
       rationNumber = view.findViewById(R.id.rationnumber);
       password = view.findViewById(R.id.password2);
       confirmPassword =  view.findViewById(R.id.confirmpassword2);
       SignUp = view.findViewById(R.id.signup2);

       SignUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               final String phoneTXT = phoneNumber.getText().toString();
               final  String nameTXT = fullName.getText().toString();
               final String fpsTXT = fpsID.getText().toString();
               final String rationTXT = rationNumber.getText().toString();
               final String passwordTXT = password.getText().toString();
               final String cfPasswordTXT = confirmPassword.getText().toString();
               if(phoneTXT.isEmpty() || nameTXT.isEmpty()|| fpsTXT.isEmpty()|| rationTXT.isEmpty() || passwordTXT.isEmpty() || cfPasswordTXT.isEmpty())
                   Toast.makeText(context,"please enter all details",Toast.LENGTH_SHORT).show();

               else if(phoneTXT.length()<10 || phoneTXT.length()>10 || !isValidMobileNo(phoneTXT))
               {
                   Toast.makeText(context,"please enter valid number",Toast.LENGTH_SHORT).show();
               }
               else if(!android.text.TextUtils.isDigitsOnly(rationTXT) || rationTXT.length()<12 || rationTXT.length()>12)
               {
                   Toast.makeText(context,"please enter valid FPS ID",Toast.LENGTH_SHORT).show();
               }
               else if(!android.text.TextUtils.isDigitsOnly(fpsTXT) || fpsTXT.length()<12 || fpsTXT.length()>12)
               {
                   Toast.makeText(context,"please enter valid FPS ID",Toast.LENGTH_SHORT).show();
               }
               else if(!isAlpha(nameTXT))
               {
                   Toast.makeText(context,"please enter valid name",Toast.LENGTH_SHORT).show();
               }
               else if(!passwordTXT.equals(cfPasswordTXT))
                   Toast.makeText(context,"password not matching",Toast.LENGTH_SHORT).show();
               else
               {
                   databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                           if(dataSnapshot.child("admin").hasChild(phoneTXT))
                               Toast.makeText(context,"phone number already exist",Toast.LENGTH_SHORT).show();
                           else if(dataSnapshot.child("customer").hasChild(phoneTXT))
                               Toast.makeText(context,"phone number already exist",Toast.LENGTH_SHORT).show();
                           else
                           {
                               databaseReference.child("users").child("customer").child(phoneTXT).child("full name").setValue(nameTXT);
                               databaseReference.child("users").child("customer").child(phoneTXT).child("fps id").setValue(fpsTXT);
                               databaseReference.child("users").child("customer").child(phoneTXT).child("ration number").setValue(rationTXT);
                               databaseReference.child("users").child("customer").child(phoneTXT).child("password").setValue(passwordTXT);
                               Toast.makeText(context,"User register sucessfully",Toast.LENGTH_SHORT).show();

                               FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                               LoginPage fragment = new LoginPage();
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