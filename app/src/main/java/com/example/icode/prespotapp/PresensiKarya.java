package com.example.icode.prespotapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PresensiKarya extends Fragment {

    private Button btnLapang, btnKantor;
    private View rootView;

    public static PresensiKarya newInstance(){
        PresensiKarya fragment = new PresensiKarya();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_presensi_karya, container, false);
        getActivity().setTitle("DATA ABSENSI");

        btnKantor     = (Button)rootView.findViewById(R.id.btnKantor);
        btnKantor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PresensiKaryaKantor.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnLapang     = (Button)rootView.findViewById(R.id.btnLapang);
        btnLapang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PresensiDataLapang.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return rootView;

    }

}
