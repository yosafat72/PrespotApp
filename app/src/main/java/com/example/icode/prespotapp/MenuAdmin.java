package com.example.icode.prespotapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuAdmin extends AppCompatActivity implements View.OnClickListener{

    Button btnKaryawan, btnPresensi, btnPesan, btnLogout;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        context = MenuAdmin.this;

        btnKaryawan     = (Button)findViewById(R.id.btnKaryawan);
        btnPresensi     = (Button)findViewById(R.id.btnPresensi);
        btnPesan        = (Button)findViewById(R.id.btnPesan);
        btnLogout       = (Button)findViewById(R.id.btnLogout);

        btnKaryawan.setOnClickListener(this);
        btnPresensi.setOnClickListener(this);
        btnPesan.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int view;
        view = v.getId();

        switch (view){
            case R.id.btnKaryawan:
                Intent intent = new Intent(context, Karyawan.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnPresensi:
                Intent intent4 = new Intent(context, PresensiDataAdmin.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.btnPesan:
                Intent intent2 = new Intent(context, PesanAdmin.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.btnLogout:
                Intent intent3 = new Intent(context, Login.class);
                startActivity(intent3);
                finish();
                break;
            default:
                break;
        }

    }
}
