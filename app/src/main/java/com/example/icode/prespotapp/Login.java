package com.example.icode.prespotapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText txtEmail, txtPassUsr, txtJabatan;
    Button btnLogin, btnKeluar;
    private ProgressDialog pDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = Login.this;

        pDialog = new ProgressDialog(context);
        txtEmail    = (EditText)findViewById(R.id.txtEmail);
        txtPassUsr  = (EditText)findViewById(R.id.txtPassUsr);
        txtJabatan  = (EditText)findViewById(R.id.txtJabatan);
        btnLogin    = (Button)findViewById(R.id.btnLogin);
        btnKeluar   = (Button)findViewById(R.id.btnKeluar);

        btnLogin.setOnClickListener(this);
        btnKeluar.setOnClickListener(this);

    }

    private void login() {
        //Getting values from edit texts
        final String email = txtEmail.getText().toString().trim();
        final String password = txtPassUsr.getText().toString().trim();
        pDialog.setMessage("Login Process...");
        showDialog();
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success from server
                        if (response.contains(konfigurasi.LOGIN_SUCCESS)) {
                            hideDialog();
                            gotoCourseActivity();

                        } else {
                            hideDialog();
                            //Displaying an error message on toast
                            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        hideDialog();
                        Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(konfigurasi.KEY_EMAIL, email);
                params.put(konfigurasi.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void gotoCourseActivity() {
        String atasan = "Atasan", kantor = "Kantor", lapang = "Lapangan";
        String jabatan = txtJabatan.getText().toString().trim();
        if(jabatan.equals(atasan)){
            Intent intent = new Intent(context, MenuAdmin.class);
            startActivity(intent);
            finish();
        }else if(jabatan.equals(kantor)){
            Intent intent = new Intent(context, Main_Menu.class);
            startActivity(intent);
            finish();
        }else if(jabatan.equals(lapang)){
            Intent intent = new Intent(context, BottomNavigator.class);
            startActivity(intent);
            finish();
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnLogin){
            login();
        }else if (v.getId() == R.id.btnKeluar){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Keluar Aplikasi ?")
                    .setCancelable(false)
                    .setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Login.this.finish();
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }

    }
}
