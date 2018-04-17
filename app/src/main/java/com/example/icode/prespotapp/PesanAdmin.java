package com.example.icode.prespotapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PesanAdmin extends AppCompatActivity {

    Button btnRoom, btnKembali;
    EditText txtRoom;
    ListView listView;
    String nama;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> list_of_rooms = new ArrayList<>();

    Context context;

    public static PesanKarya newInstance(){
        PesanKarya fragment = new PesanKarya();
        return fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_admin);
        context = PesanAdmin.this;

        btnRoom     = (Button)findViewById(R.id.btnRuangan);
        btnKembali  = (Button)findViewById(R.id.btnKembali);
        txtRoom     = (EditText)findViewById(R.id.txtRoom);
        listView    = (ListView)findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list_of_rooms);
        listView.setAdapter(arrayAdapter);

        request_user_name();

        btnRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put(txtRoom.getText().toString(),"");
                root.updateChildren(map);
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(context, MenuAdmin.class);
                startActivity(intent2);
                finish();
            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while ( i.hasNext())
                {
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                list_of_rooms.clear();
                list_of_rooms.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent I = new Intent(context,ChatRoomAdmin.class);
                I.putExtra("roomname",((TextView)view).getText().toString());
                I.putExtra("username",nama);
                startActivity(I);
            }
        });


    }

    private void request_user_name(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Masukan Nama Anda");
        final EditText txtInput = new EditText(context);
        builder.setView(txtInput);
        builder.setPositiveButton("OK ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nama = txtInput.getText().toString();
                if(nama.isEmpty()){
                    Intent intent2 = new Intent(context, PesanAdmin.class);
                    startActivity(intent2);
                    finish();
                    Toast.makeText(context, "Nama Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                request_user_name();
            }
        });
        builder.show();
    }

}
