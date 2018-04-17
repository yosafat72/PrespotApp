package com.example.icode.prespotapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SeePresensi extends AppCompatActivity implements View.OnClickListener {

    EditText txtID, txtLokasi, txtNama, txtDeskripsi, txtTanggal, txtJam, txtStatus;
    Button btnKembali;
    String id_presensi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_presensi);

        Intent intent = getIntent();
        id_presensi = intent.getStringExtra(konfigurasi.TAG_PRESENSI_ID);

        txtID           = (EditText)findViewById(R.id.txtID);
        txtLokasi       = (EditText)findViewById(R.id.txtLokasi);
        txtNama         = (EditText)findViewById(R.id.txtNama);
        txtDeskripsi    = (EditText)findViewById(R.id.txtDeskripsi);
        txtTanggal      = (EditText)findViewById(R.id.txtTanggal);
        txtJam          = (EditText)findViewById(R.id.txtJam);
        txtStatus       = (EditText)findViewById(R.id.txtStatus);
        btnKembali      = (Button)findViewById(R.id.btnKembali);

        btnKembali.setOnClickListener(this);

        txtID.setText(id_presensi);

        getEmployee();

    }

    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String lokasi     = c.getString(konfigurasi.TAG_PRESENSI_LOKASI);
            String nama       = c.getString(konfigurasi.TAG_PRESENSI_NAMA);
            String deskripsi  = c.getString(konfigurasi.TAG_PRESENSI_DESKRIPSI);
            String tanggal    = c.getString(konfigurasi.TAG_PRESENSI_TANGGAL);
            String jam        = c.getString(konfigurasi.TAG_PRESENSI_JAM);
            String status     = c.getString(konfigurasi.TAG_PRESENSI_STATUS);

            txtLokasi.setText(lokasi);
            txtNama.setText(nama);
            txtDeskripsi.setText(deskripsi);
            txtTanggal.setText(tanggal);
            txtJam.setText(jam);
            txtStatus.setText(status);

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
                loading = ProgressDialog.show(SeePresensi.this,"Fetching...","Wait...",false,false);
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
                hash.put(konfigurasi.KEY_PRESENSI_ID, id_presensi);
                String s = rh.sendPostRequest(konfigurasi.URL_GET_PRESENSI, hash);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnKembali){
            Intent intent = new Intent(SeePresensi.this, PresensiDataAdmin.class);
            startActivity(intent);
            finish();
        }
    }

}
