package com.example.icode.prespotapp;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DataPresensi extends Fragment {

    public DataPresensi(){}
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_data_presensi, container, false);
        getActivity().setTitle("Data Presensi");

        Button btnPindah = (Button)rootView.findViewById(R.id.btnAbsen);

        if(btnPindah != null){
            btnPindah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(getActivity(), Presensi.class);
                    startActivity(intent);
                }
            });
        }

        return rootView;
    }
}
