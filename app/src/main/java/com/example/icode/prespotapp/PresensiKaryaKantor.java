package com.example.icode.prespotapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class PresensiKaryaKantor extends AppCompatActivity implements View.OnClickListener{

    EditText txtID;
    ImageView imgView;
    Button tampil, kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presensi_karya_kantor);

        txtID   = (EditText)findViewById(R.id.txtID);
        imgView = (ImageView)findViewById(R.id.imgQr);
        tampil  = (Button)findViewById(R.id.btnGenerate);
        kembali = (Button)findViewById(R.id.btnKembali);

        tampil.setOnClickListener(this);
        kembali.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnGenerate){
            String text2Qr  = txtID.getText().toString();
            if(text2Qr.isEmpty()){
                txtID.setError("ID Karyawan Tidak Boleh Kosong");
            }else{
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imgView.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        }else if(v.getId() == R.id.btnKembali){
            Intent intent = new Intent(PresensiKaryaKantor.this, BottomNavigator.class);
            startActivity(intent);
            finish();
        }

    }

}
