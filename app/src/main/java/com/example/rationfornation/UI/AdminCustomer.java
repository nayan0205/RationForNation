package com.example.rationfornation.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rationfornation.MainActivity;
import com.example.rationfornation.R;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminCustomer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminCustomer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  View view;

    Context mcontext;

    private String mParam1;
    private String mParam2;

    public AdminCustomer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminCustomer.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminCustomer newInstance(String param1, String param2) {
        AdminCustomer fragment = new AdminCustomer();
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
      mcontext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.login_admincustomer, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button admin = view.findViewById(R.id.button1);
        final Button customer = view.findViewById(R.id.button2);

        admin.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
              FragmentManager fragmentManager = ((MainActivity)mcontext).getSupportFragmentManager();
              AdminSignUp fragment  = new AdminSignUp();
              fragmentManager.beginTransaction().replace(R.id.windowFrame,fragment).addToBackStack(null).commit();
            }
        });
        customer.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((MainActivity)mcontext).getSupportFragmentManager();
                CustomerSignUp fragment = new CustomerSignUp();
                fragmentManager.beginTransaction().replace(R.id.windowFrame,fragment).addToBackStack(null).commit();
            }
        });
    }
}