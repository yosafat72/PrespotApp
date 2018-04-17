package com.example.icode.prespotapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UDKaryawan extends Activity implements View.OnClickListener{

    EditText txtId, txtNama, txtJabatan, txtKelamin, txtTelpon, txtPassword, txtAlamat;
    Button btnUpdate, btnDelete, btnKembali;
    String id_karya;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udkaryawan);

        Intent intent = getIntent();
        id_karya = intent.getStringExtra(konfigurasi.TAG_KARYA_ID);

        txtId       = (EditText)findViewById(R.id.txtID);
        txtNama     = (EditText)findViewById(R.id.txtNama);
        txtJabatan  = (EditText)findViewById(R.id.txtJabatan);
        txtKelamin  = (EditText)findViewById(R.id.txtKelamin);
        txtTelpon   = (EditText)findViewById(R.id.txtTlpn);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtAlamat   = (EditText)findViewById(R.id.txtAlamat);
        btnUpdate   = (Button)findViewById(R.id.btnUpdate);
        btnKembali  = (Button)findViewById(R.id.btnKembali);
        btnDelete   = (Button)findViewById(R.id.btnDelete);

        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnKembali.setOnClickListener(this);

        txtId.setText(id_karya);

        getEmployee();

    }

    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String nama     = c.getString(konfigurasi.TAG_KARYA_NAMA);
            String jabatan  = c.getString(konfigurasi.TAG_KARYA_JABATAN);
            String kelamin  = c.getString(konfigurasi.TAG_KARYA_KELAMIN);
            String telpon   = c.getString(konfigurasi.TAG_KARYA_TELPON);
            String password = c.getString(konfigurasi.TAG_KARYA_PASSWORD);
            String alamat   = c.getString(konfigurasi.TAG_KARYA_ALAMAT);

            txtNama.setText(nama);
            txtJabatan.setText(jabatan);
            txtKelamin.setText(kelamin);
            txtTelpon.setText(telpon);
            txtPassword.setText(password);
            txtAlamat.setText(alamat);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UDKaryawan.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> hash = new HashMap<String, String>();
                hash.put(konfigurasi.KEY_KARYA_ID, id_karya);
                String s = rh.sendPostRequest(konfigurasi.URL_GET_KARYAWAN, hash);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    public void updateKaryawan() {
        final String nama, jabatan, kelamin, telp, pass, alamat;
        nama = txtNama.getText().toString().trim();
        jabatan = txtJabatan.getText().toString();
        kelamin = txtKelamin.getText().toString();
        telp = txtTelpon.getText().toString().trim();
        pass = txtPassword.getText().toString().trim();
        alamat = txtAlamat.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UDKaryawan.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UDKaryawan.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put(konfigurasi.KEY_KARYA_ID, id_karya);
                params.put(konfigurasi.KEY_KARYA_NAMA, nama);
                params.put(konfigurasi.KEY_KARYA_JABATAN, jabatan);
                params.put(konfigurasi.KEY_KARYA_KELAMIN, kelamin);
                params.put(konfigurasi.KEY_KARYA_TELPON, telp);
                params.put(konfigurasi.KEY_KARYA_PASSWORD, pass);
                params.put(konfigurasi.KEY_KARYA_ALAMAT, alamat);
                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_KARYAWAN, params);

                return s;
            }
        }
        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    private void deleteEmployee(){
        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UDKaryawan.this, "Deleting...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UDKaryawan.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hash = new HashMap<String, String>();
                hash.put(konfigurasi.KEY_KARYA_ID,id_karya);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(konfigurasi.URL_DELETE_KARYAWAN, hash);
                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteEmployee(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Pegawai ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEmployee();
                        startActivity(new Intent(UDKaryawan.this,Main_Menu.class));
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnUpdate){
            updateKaryawan();
        }else if(view.getId() == R.id.btnDelete){
            confirmDeleteEmployee();
        }else if(view.getId() == R.id.btnKembali){
            Intent intent = new Intent(UDKaryawan.this, Main_Menu.class);
            startActivity(intent);
            finish();
        }

    }
}
