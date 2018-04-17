package com.example.icode.prespotapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatRoomKaryawan extends AppCompatActivity {

    private Button btn_send_msg, btnKembali;
    private EditText input_msg;
    private TextView chat_conversation;
    private String username ,roomname;
    private DatabaseReference root;
    private String temp_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_karyawan);

        btn_send_msg        = (Button)findViewById(R.id.button);
        btnKembali          = (Button)findViewById(R.id.btnKembali);
        input_msg           = (EditText)findViewById(R.id.editText);
        chat_conversation   = (TextView)findViewById(R.id.textView);
        username            = getIntent().getExtras().get("username").toString();
        roomname            = getIntent().getExtras().get("roomname").toString();
        setTitle("Room - "+roomname);

        root = FirebaseDatabase.getInstance().getReference().child(roomname);

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",username);
                map2.put("msg",input_msg.getText().toString());

                message_root.updateChildren(map2);

                input_msg.setText("");

            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatRoomKaryawan.this, BottomNavigator.class);
                startActivity(intent);
                finish();
            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append_chat_conversatin(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversatin(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    String chat_msg, chat_user_name;

    private void append_chat_conversatin(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext())
        {
            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();

            chat_conversation.append(chat_user_name + " : "+chat_msg +"\n");

        }

    }


}
