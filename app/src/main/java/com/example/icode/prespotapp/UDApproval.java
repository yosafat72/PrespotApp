package com.example.icode.prespotapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UDApproval extends AppCompatActivity implements View.OnClickListener{

    EditText txtID, txtLokasi, txtNama, txtDeskripsi, txtTanggal, txtJam, txtStatus;
    Button btnIzin, btnTidak, btnKembali;
    String id_presensi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udapproval);

        Intent intent = getIntent();
        id_presensi = intent.getStringExtra(konfigurasi.TAG_PRESENSI_ID);

        txtID           = (EditText)findViewById(R.id.txtID);
        txtLokasi       = (EditText)findViewById(R.id.txtLokasi);
        txtNama         = (EditText)findViewById(R.id.txtNama);
        txtDeskripsi    = (EditText)findViewById(R.id.txtDeskripsi);
        txtTanggal      = (EditText)findViewById(R.id.txtTanggal);
        txtJam          = (EditText)findViewById(R.id.txtJam);
        txtStatus       = (EditText)findViewById(R.id.txtStatus);
        btnIzin         = (Button)findViewById(R.id.btnIzin);
        btnTidak        = (Button)findViewById(R.id.btnTidak);
        btnKembali      = (Button)findViewById(R.id.btnKembali);

            btnKembali.setOnClickListener(this);
            btnTidak.setOnClickListener(this);
            btnIzin.setOnClickListener(this);

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
                loading = ProgressDialog.show(UDApproval.this,"Fetching...","Wait...",false,false);
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

    public void updateKaryawan() {
        final String lokasi, nama, deskripsi, tanggal, jam, status;
        lokasi      = txtLokasi.getText().toString().trim();
        nama        = txtNama.getText().toString();
        deskripsi   = txtDeskripsi.getText().toString();
        tanggal     = txtTanggal.getText().toString().trim();
        jam         = txtJam.getText().toString().trim();
        status      = txtStatus.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UDApproval.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UDApproval.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put(konfigurasi.KEY_PRESENSI_ID, id_presensi);
                params.put(konfigurasi.KEY_PRESENSI_LOKASI, lokasi);
                params.put(konfigurasi.KEY_PRESENSI_NAMA, nama);
                params.put(konfigurasi.KEY_PRESENSI_DESKRIPSI, deskripsi);
                params.put(konfigurasi.KEY_PRESENSI_TANGGAL, tanggal);
                params.put(konfigurasi.KEY_PRESENSI_JAM, jam);
                params.put(konfigurasi.KEY_PRESENSI_STATUS, status);
                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_PRESENSI, params);

                return s;
            }
        }
        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnIzin:
                    txtStatus.setText("Approv");
                    updateKaryawan();
                break;

            case R.id.btnTidak:
                    txtStatus.setText("Reject");
                    updateKaryawan();
                break;

            case R.id.btnKembali:
                Intent intent = new Intent(UDApproval.this, Main_Menu.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
