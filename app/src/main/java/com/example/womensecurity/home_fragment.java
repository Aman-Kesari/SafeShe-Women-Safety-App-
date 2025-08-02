package com.example.womensecurity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
public class home_fragment extends Fragment {
    Context context;

    public home_fragment(Context context) {
      this.context=context;
    }
    // TODO: Rename and change types and number of parameters
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.home_fragment, container, false);
        CardView btnPolice=view.findViewById(R.id.btnPolice);
        CardView btnWomen=view.findViewById(R.id.btnWomen);
        CardView btnAmbulance=view.findViewById(R.id.btnAmbulance);
        CardView btnDial=view.findViewById(R.id.btnDial);
        EditText dialedNo=view.findViewById(R.id.dialedNumber);
        ImageView btnSOS=view.findViewById(R.id.btnSOS);
        Button btn_editSos=view.findViewById(R.id.btn_editSOS);
        //Dialing
        btnPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calling("100");
            }
        });
        btnWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calling("181");
            }
        });
        btnAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calling("108");
            }
        });
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(dialedNo.getText().toString().isEmpty()){
                Toast.makeText(context, "Enter the number firstly!", Toast.LENGTH_SHORT).show();
              }
              else if(dialedNo.getText().toString().length()!=10){
                 Toast.makeText(context,"Enter 10 digits number ",Toast.LENGTH_SHORT).show();
              }else{
                  String number=(dialedNo.getText().toString());
                  Calling(number);
              }
            }
        });
        //SOS
        btnSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberSOS=gettingSharedNumber();
                if(numberSOS==""){
                    Toast.makeText(context, "Edit SOS number firstly!", Toast.LENGTH_SHORT).show();
                }else{
                    Calling(numberSOS);
                    SendingSms(numberSOS,"Save Me! I am in trouble");
                }
            }
        });
        btn_editSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingDialog();
            }
        });
        return view;
    }

    public void Calling(String number){
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+number));
        startActivity(intent);
    }
    public void SendingSms(String number,String message){
        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:"+Uri.encode(number)));
        intent.putExtra("sms_body",message);
        startActivity(intent);
    }
    public void settingDialog(){
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.edit_layout);
        dialog.setCancelable(true);

        EditText editedNum=dialog.findViewById(R.id.num_edited);
        Button btn_saved=dialog.findViewById(R.id.btn_savedNumber);
        if(gettingSharedNumber()!=""){
            editedNum.setHint(gettingSharedNumber());
        }
        btn_saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(editedNum.getText().toString()!="") {
                        settingSharedNumber(editedNum.getText().toString());
                    }
                    dialog.hide();
                    Toast.makeText(context, "Number is saved", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
   public String gettingSharedNumber(){
       SharedPreferences sharedPreferences= context.getSharedPreferences("SHARED_NUMBER",Context.MODE_PRIVATE);
       String number=sharedPreferences.getString("NumberDial","");
       return number;
   }
   public void settingSharedNumber(String number){
        SharedPreferences sharedPreferences= context.getSharedPreferences("SHARED_NUMBER",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("NumberDial",number);
        editor.apply();
   }
}