package com.example.icode.prespotapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

public class Approval extends Fragment {

    public Approval(){}
    private ListView listView;
    private String JSON_STRING;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_approval, container, false);
        getActivity().setTitle("Data Approval");

        listView    = (ListView)rootView.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), UDApproval.class);
                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                String empId = map.get(konfigurasi.TAG_PRESENSI_ID).toString();
                intent.putExtra(konfigurasi.TAG_PRESENSI_ID,empId);
                startActivity(intent);
                getActivity().finish();
            }
        });

        getJSON();

        return rootView;
    }

    private void showEmployee() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo       = result.getJSONObject(i);
                String id_pre       = jo.getString(konfigurasi.TAG_PRESENSI_ID);
                String nama         = jo.getString(konfigurasi.TAG_PRESENSI_NAMA);
                String deskripsi    = jo.getString(konfigurasi.TAG_PRESENSI_DESKRIPSI);

                HashMap<String, String> employees = new HashMap<String, String>();
                employees.put(konfigurasi.TAG_PRESENSI_ID, id_pre);
                employees.put(konfigurasi.TAG_PRESENSI_NAMA, nama);
                employees.put(konfigurasi.TAG_PRESENSI_DESKRIPSI, deskripsi);

                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list, R.layout.listpresensi,
                new String[]{konfigurasi.TAG_PRESENSI_ID,konfigurasi.TAG_PRESENSI_NAMA, konfigurasi.TAG_PRESENSI_DESKRIPSI},
                new int[]{R.id.txtID, R.id.txtNama, R.id.txtDeskripsi});

        listView.setAdapter(adapter);

    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Mengambil Data","Mohon Tunggu...",false,false);
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL_PRESENSI);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

}
