package com.example.icode.prespotapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class InsertKarya extends AppCompatActivity implements View.OnClickListener{

    EditText txtNama, txtTelp, txtPass, txtAlamat;
    Spinner spinJabatan, spinKelamin;
    Button btnSimpan, btnKembali;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_karya);

        txtNama     = (EditText)findViewById(R.id.txtNama);
        spinJabatan = (Spinner)findViewById(R.id.spinJabatan);
        spinKelamin = (Spinner)findViewById(R.id.spinKelamin);
        txtTelp     = (EditText)findViewById(R.id.txtTlpn);
        txtPass     = (EditText)findViewById(R.id.txtPassword);
        txtAlamat   = (EditText)findViewById(R.id.txtAlamat);
        btnSimpan   = (Button)findViewById(R.id.btnSimpan);
        btnKembali  = (Button)findViewById(R.id.btnKembali);

        spinJabatan();
        spinKelamin();

        btnSimpan.setOnClickListener(this);
        btnKembali.setOnClickListener(this);

    }

    public void spinJabatan(){
        String[] menuJabatan = {"Pilih", "Atasan",  "Kantor", "Lapangan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, menuJabatan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinJabatan.setAdapter(adapter);
        spinJabatan.setPrompt("Jabatan");
    }

    public void spinKelamin(){
        String[] menuKelamin = {"Pilih", "Laki-laki", "Perempuan"};
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, menuKelamin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKelamin.setAdapter(adapter);
        spinKelamin.setPrompt("Kelamin");
    }

    public void addKaryawan(){
        final String nama, jabatan, kelamin, telp, pass, alamat;
        nama        = txtNama.getText().toString().trim();
        jabatan     = spinJabatan.getSelectedItem().toString().trim();
        kelamin     = spinKelamin.getSelectedItem().toString().trim();
        telp        = txtTelp.getText().toString().trim();
        pass        = txtPass.getText().toString().trim();
        alamat      = txtAlamat.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(InsertKarya.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(InsertKarya.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<String, String>();
                params.put(konfigurasi.KEY_KARYA_NAMA,nama);
                params.put(konfigurasi.KEY_KARYA_JABATAN,jabatan);
                params.put(konfigurasi.KEY_KARYA_KELAMIN,kelamin);
                params.put(konfigurasi.KEY_KARYA_TELPON,telp);
                params.put(konfigurasi.KEY_KARYA_PASSWORD,pass);
                params.put(konfigurasi.KEY_KARYA_ALAMAT,alamat);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD_KARYAWAN, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSimpan:
                addKaryawan();
                Intent intent2 = new Intent(InsertKarya.this, Karyawan.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.btnKembali:
                Intent intent = new Intent(InsertKarya.this, Karyawan.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
