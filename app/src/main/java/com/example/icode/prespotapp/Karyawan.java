package com.example.icode.prespotapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Karyawan extends Activity{

    private ListView listView;
    private String JSON_STRING;
    Button btnTambah, btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karyawan);

        listView    = (ListView)findViewById(R.id.listView);
        btnTambah   = (Button)findViewById(R.id.btnAdd);
        btnKembali  = (Button)findViewById(R.id.btnKembali);

        if(btnTambah != null){
            btnTambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(Karyawan.this, InsertKarya.class);
                    startActivity(intent);
                }
            });
        }

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Karyawan.this, MenuAdmin.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Karyawan.this, UDKaryawan.class);
                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                String empId = map.get(konfigurasi.TAG_KARYA_ID).toString();
                intent.putExtra(konfigurasi.TAG_KARYA_ID,empId);
                startActivity(intent);
                Karyawan.this.finish();
            }
        });

        getJSON();

    }

    private void showEmployee() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id_karya = jo.getString(konfigurasi.TAG_KARYA_ID);
                String nm_karya = jo.getString(konfigurasi.TAG_KARYA_NAMA);

                HashMap<String, String> employees = new HashMap<String, String>();
                employees.put(konfigurasi.TAG_KARYA_ID, id_karya);
                employees.put(konfigurasi.TAG_KARYA_NAMA, nm_karya);

                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Karyawan.this, list, R.layout.listkaryawan,
                new String[]{konfigurasi.TAG_KARYA_ID,konfigurasi.TAG_KARYA_NAMA},
                new int[]{R.id.txtIdKarya, R.id.txtNamaKarya});

        listView.setAdapter(adapter);

    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Karyawan.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL_KARYAWAN);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

}
