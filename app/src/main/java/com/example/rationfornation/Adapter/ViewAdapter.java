package com.example.rationfornation.Adapter;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rationfornation.R;
import java.util.ArrayList;


public class  ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private ArrayList<String> Name, Number;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView textView, textnumber;
        private Button btn1,btn2;
        Context context;



        //        private final Button button;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView =   view.findViewById(R.id.text_name);
            textnumber =   view.findViewById(R.id.text_number);
            context = view.getContext();
          btn1 =  view.findViewById(R.id.button_block);
            btn2 =  view.findViewById(R.id.button_block2);
        }

        public TextView getTextView() {
            return textView;
        }
        public TextView getTextnumber() {
            return textnumber;
        }
        public Button getButton1() {
            return btn1;
        }
        public Button getButton2() {
            return btn2;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param name String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public  ViewAdapter(ArrayList<String> name,ArrayList<String>  number) {
        this.Name = name;
        this.Number = number;
        Log.d("name number:", name+""+number);
    }



    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_admin, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(Name.get(position));
        viewHolder.getTextnumber().setText(Number.get(position));

        viewHolder.getButton1().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(Number.get(position), null, "Hello", null, null);
                    Toast.makeText(v.getContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(v.getContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                finally {
                    viewHolder.getButton2().setVisibility(View.VISIBLE);
                    viewHolder.getButton1().setVisibility(View.INVISIBLE);
                }
            }
        });

        viewHolder.getButton2().setVisibility(View.INVISIBLE);
//        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(threadPolicy);

        viewHolder.getButton2().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(Number.get(position), null, "Hello", null, null);
                    Toast.makeText(v.getContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(v.getContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                finally {
                    viewHolder.getButton1().setVisibility(View.VISIBLE);
                    viewHolder.getButton2().setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return Name.size();
    }

}